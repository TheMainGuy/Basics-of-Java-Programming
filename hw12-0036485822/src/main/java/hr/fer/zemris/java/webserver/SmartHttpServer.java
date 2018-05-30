package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

public class SmartHttpServer {
  private String address;
  private String domainName;
  private int port;
  private int workerThreads;
  private int sessionTimeout;
  private Map<String, String> mimeTypes = new HashMap<String, String>();
  private ServerThread serverThread;
  private ExecutorService threadPool;
  private Path documentRoot;
  private final AtomicBoolean running = new AtomicBoolean(false);

  /**
   * Method which is called when program starts.
   * 
   * @param args command line arguments
   */
  public static void main(String[] args) {
    if(args.length != 1) {
      System.out.println("Only 1 argument expected.");
    }

    new SmartHttpServer(args[0]);

  }

  /**
   * Constructor.
   * 
   * @param configFileName path to server.properties
   */
  public SmartHttpServer(String configFileName) {
    Properties serverProperties = new Properties();
    try {
      serverProperties.load(Files.newInputStream(Paths.get(configFileName)));
    } catch (IOException e) {
      System.out.println("Could not load server.properties file.");
      return;
    }

    Properties mimeProperties = new Properties();
    try {
      mimeProperties.load(Files.newInputStream(Paths.get(serverProperties.getProperty("server.mimeConfig"))));
    } catch (IOException e) {
      System.out.println("Could not load mime.properties file.");
      return;
    }

    Properties workersProperties = new Properties();
    try {
      workersProperties.load(Files.newInputStream(Paths.get(serverProperties.getProperty("server.workers"))));
    } catch (IOException e) {
      System.out.println("Could not load workers.properties file.");
      return;
    }

    address = serverProperties.getProperty("server.address").toString();
    domainName = serverProperties.getProperty("server.domainName");
    port = Integer.parseInt(serverProperties.getProperty("server.port").toString());
    workerThreads = Integer.parseInt(serverProperties.getProperty("server.workerThreads").toString());
    documentRoot = Paths.get(serverProperties.getProperty("server.documentRoot").toString());
    sessionTimeout = Integer.parseInt(serverProperties.getProperty("session.timeout").toString());

    mimeProperties.forEach((k, v) -> mimeTypes.put(k.toString(), v.toString()));

    serverThread = new ServerThread();

    this.start();
  }

  protected synchronized void start() {
    threadPool = Executors.newFixedThreadPool(workerThreads);
    if(!serverThread.isAlive()) {
      running.set(true);
      serverThread.run();
    }

  }

  protected synchronized void stop() {
    running.set(false);
    threadPool.shutdown();
  }

  protected class ServerThread extends Thread {
    @Override
    public void run() {
      try {
        @SuppressWarnings("resource")
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(address, port));
        System.out.println("Listening on " + address + ":" + port);
        while (running.get()) {
          Socket client = serverSocket.accept();
          ClientWorker cw = new ClientWorker(client);
          threadPool.submit(cw);
        }
      } catch (IOException e) {
        System.out.println("Problem with opening server socket.");
        return;
      }

    }
  }

  private class ClientWorker implements Runnable {
    private Socket csocket;
    private PushbackInputStream istream;
    private OutputStream ostream;
    private String version;
    private String method;
    private String host;
    private Map<String, String> params = new HashMap<String, String>();
    private Map<String, String> tempParams = new HashMap<String, String>();
    private Map<String, String> permParams = new HashMap<String, String>();
    private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
    private String SID;

    public ClientWorker(Socket csocket) {
      super();
      this.csocket = csocket;
    }

    @Override
    public void run() {
      try {
        istream = new PushbackInputStream(csocket.getInputStream());
        ostream = csocket.getOutputStream();
      } catch (IOException e) {
        return;
      }

      List<String> request = readRequest();
      if(request.size() < 1) {
        sendError(400, "Bad request");
        return;
      }

      String firstLine = request.get(0);
      String[] firstLineParts = firstLine.split(" ");

      method = firstLineParts[0].toUpperCase();
      if(!method.equals("GET")) {
        sendError(400, "Only post method allowed.");
        return;
      }

      String requestedPath = firstLineParts[1];

      version = firstLineParts[2].toUpperCase();
      if(!version.equals("HTTP/1.1") && !version.equals("HTTP/1.0")) {
        sendError(400, "Only HTTP/1.1 and HTTP/1.0 supported.");
        return;
      }

      for (String requestLine : request) {
        if(requestLine.startsWith("Host:")) {
          host = requestLine.substring(5);
        }
      }
      if(host == null) {
        host = domainName;
      } else if(host.split(":").length == 2) {
        try {
          Double.parseDouble(host.split(":")[1]);
          host = host.split(":")[0];
        } catch (Exception e) {
        }
      }

      String path = null;
      String paramString = null;
      if(requestedPath.contains("?")) {
        if(requestedPath.split("?").length == 2) {
          path = requestedPath.split("?")[0];
          paramString = requestedPath.split("?")[1];
        }
      } else {
        path = requestedPath;
      }

      if(paramString != null) {
        parseParameters(paramString);
      }

      Path requestedFile = documentRoot.resolve(path.substring(1)).toAbsolutePath();
      if(!requestedFile.startsWith(documentRoot)) {
        sendError(403, "Forbidden.");
        return;
      }

      if(!Files.exists(requestedFile) || !Files.isRegularFile(requestedFile) || !Files.isReadable(requestedFile)) {
        sendError(404, "File not found");
        return;
      }

      String fileName = requestedFile.getFileName().toString();
      String extension = fileName.substring(fileName.lastIndexOf('.'));
      String mimeType = mimeTypes.get(extension);
      mimeType = mimeType == null ? "octet-stream" : mimeType;
      RequestContext rc = new RequestContext(ostream, params, permParams, outputCookies);
      rc.setMimeType(mimeType);
      rc.setStatusCode(200);
      rc.setContentLength(requestedFile.toFile().length());
      try {
        InputStream fileStream = Files.newInputStream(requestedFile);
        byte[] buf = new byte[1024];
        while (true) {
          int len = fileStream.read(buf);
          if(len < 1)
            break;
          rc.write(buf, 0, len);
        }
      } catch (IOException e) {
        System.out.println("Problem with writing to socket.");
        return;
      }

    }

    /**
     * Helper method which parses request line parameters and stores them in map
     * parameters.
     * 
     * @param paramString unparsed string containing parameters
     */
    private void parseParameters(String paramString) {
      String[] parameters = paramString.split("&");
      for (int i = 0; i < parameters.length; i++) {
        params.put(parameters[i].split("=")[0], parameters[i].split("=")[1]);
      }
    }

    /**
     * Helper method used to send error to client socket's output stream.
     * 
     * @param statusCode status code which will be sent
     * @param statusText status text which will be sent
     * @throws IOException if there is problem with writing to output stream
     */
    private void sendError(int statusCode, String statusText) {

      try {
        ostream.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Server: simple java server\r\n"
            + "Content-Type: text/plain;charset=UTF-8\r\n" + "Content-Length: 0\r\n" + "Connection: close\r\n" + "\r\n")
                .getBytes(StandardCharsets.US_ASCII));
        ostream.flush();
      } catch (Exception e) {
        return;
      }

    }

    /**
     * Reads client request, saves it to list of strings and returns that list of
     * strings.
     * 
     * @return request in form of list of strings
     */
    private List<String> readRequest() {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      int state = 0;
      l: while (true) {
        int b;
        try {
          b = istream.read();
        } catch (IOException e) {
          return null;
        }
        if(b == -1)
          return null;
        if(b != 13) {
          bos.write(b);
        }
        switch (state) {
        case 0:
          if(b == 13) {
            state = 1;
          } else if(b == 10)
            state = 4;
          break;
        case 1:
          if(b == 10) {
            state = 2;
          } else
            state = 0;
          break;
        case 2:
          if(b == 13) {
            state = 3;
          } else
            state = 0;
          break;
        case 3:
          if(b == 10) {
            break l;
          } else
            state = 0;
          break;
        case 4:
          if(b == 10) {
            break l;
          } else
            state = 0;
          break;
        }
      }
      return Arrays.asList(bos.toString().split("\n"));
    }
  }
}