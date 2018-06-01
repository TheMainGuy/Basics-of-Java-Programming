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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Implements http server which handles requests from clients using
 * parallelization. Server listens on given address and port.
 * 
 * Other parameters like domain name, session timeout, document root directory
 * and number of worker threads can also be set through server.properties file
 * whose location must be given as the only command line argument when starting
 * the program.
 * 
 * Mime types can be defined in mime.properties file whose location must be
 * defined in server.properties file.
 * 
 * Workers can be defined in workers.properties file whose location must be
 * defined in server.properties file. Workers can also be called for using
 * /ext/WORKER_NAME.
 * 
 * Server implements smart scripts using {@link SmartScriptEngine} and
 * {@link SmartScriptParser} to run smart scripts which can dynamically write
 * response to clients. Smart scripts will run when client requests .smscr file.
 * 
 * Basic session management is implemented through httpOnly cookies. Cookies
 * have session time defined in server.properties file.
 * 
 * @author tin
 *
 */
public class SmartHttpServer {
  /**
   * Server address.
   */
  private String address;

  /**
   * Server domain name.
   */
  private String domainName;

  /**
   * Server port.
   */
  private int port;

  /**
   * Number of worker threads.
   */
  private int workerThreads;

  /**
   * Number of seconds after which created cookie becomes invalid.
   */
  private int sessionTimeout;

  /**
   * Map of mime types.
   */
  private Map<String, String> mimeTypes = new HashMap<String, String>();

  /**
   * Server thread object.
   */
  private ServerThread serverThread;

  /**
   * Thread pool.
   */
  private ExecutorService threadPool;

  /**
   * Document root directory.
   */
  private Path documentRoot;

  /**
   * Flag which indicates whether server thread is running or not.
   */
  private final AtomicBoolean running = new AtomicBoolean(false);

  /**
   * Map of workers defined in workers.properties file.
   */
  private Map<String, IWebWorker> workersMap = new HashMap<>();

  /**
   * Map of client sessions.
   */
  private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();

  /**
   * Random number generator used to random session ids.
   */
  private Random sessionRandom = new Random();

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
   * Defines entry for sessions map.
   * 
   * @author tin
   *
   */
  private static class SessionMapEntry {
    /**
     * Session id.
     */
    String sid;

    /**
     * Host address or domain name, whichever did client use in request.
     */
    String host;

    /**
     * Indicates time at which this session becomes invalid.
     */
    long validUntil;

    /**
     * Map for storing client's data.
     */
    Map<String, String> map = new ConcurrentHashMap<>();

    public SessionMapEntry(String sid, String host, long validUntil) {
      this.sid = sid;
      this.host = host;
      this.validUntil = validUntil;
    }

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

    for (Object workerPath : workersProperties.keySet()) {
      String path = workerPath.toString();
      String fqcn = workersProperties.getProperty(path).toString();

      Class<?> referenceToClass;
      try {
        referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
        Object newObject = referenceToClass.getDeclaredConstructor().newInstance();
        IWebWorker iww = (IWebWorker) newObject;
        workersMap.put(path, iww);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

    serverThread = new ServerThread();

    createCleanerThread();
    this.start();
  }

  /**
   * Creates cleaner daemon thread which cleans sessions map every 5 minutes.
   * Every 5 minutes, thread will remove all expired sessions from sessions map.
   */
  private void createCleanerThread() {
    Thread t = new Thread(() -> {
      while (true) {
        try {
          Thread.sleep(300000);
          for (String sid : sessions.keySet()) {
            if(sessions.get(sid).validUntil < Calendar.getInstance().getTimeInMillis()) {
              sessions.remove(sid);
            }
          }
        } catch (InterruptedException e) {
          break;
        }
        ;

      }
    });
    t.setDaemon(true);
    t.start();

  }

  /**
   * Initializes thread pool and starts server thread. Also sets running flag to
   * true.
   */
  protected synchronized void start() {
    threadPool = Executors.newFixedThreadPool(workerThreads);
    if(!serverThread.isAlive()) {
      running.set(true);
      serverThread.run();
    }

  }

  /**
   * Sets running flag to false and shuts thread pool down.
   */
  protected synchronized void stop() {
    running.set(false);
    threadPool.shutdown();
  }

  /**
   * Implements server thread which listens on set address and port for clients.
   * When client sends request, accepts connection, creates {@link ClientWorker}
   * object to handle request and submits it to thread pool.
   * 
   * Server thread will continue running until running flag is set to false.
   * 
   * @author tin
   *
   */
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

  /**
   * Worker which is created upon accepting TCP connection from client. Reades
   * request and gives client response using HTTP/1.1.
   * 
   * @author tin
   *
   */
  private class ClientWorker implements Runnable, IDispatcher {
    /**
     * Client socket.
     */
    private Socket csocket;

    /**
     * Stream from which data is read.
     */
    private PushbackInputStream istream;

    /**
     * Stream in which data is written.
     */
    private OutputStream ostream;

    /**
     * HTTP version.
     */
    private String version;

    /**
     * HTTP method.
     */
    private String method;

    /**
     * Host name which client used to access site. Set to server domain by default.
     */
    private String host;

    /**
     * Parameters map.
     */
    private Map<String, String> params = new HashMap<String, String>();

    /**
     * Temporary parameters map.
     */
    private Map<String, String> tempParams = new HashMap<String, String>();

    /**
     * Persistent parameters map.
     */
    private Map<String, String> permParams = new HashMap<String, String>();

    /**
     * List of output cookies.
     */
    private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();

    /**
     * Session id.
     */
    //Could not find purpose for it (maybe instead of sidCandidate?)
    //private String SID = null;

    /**
     * Context used for writing to socket and store data.
     */
    private RequestContext context;

    /**
     * Constructor.
     * 
     * @param csocket client socket
     */
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
          break;
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

      checkSessions(request);

      String path = null;
      String paramString = null;
      if(requestedPath.contains("?")) {
        path = requestedPath.split("[?]")[0];
        paramString = requestedPath.split("[?]")[1];
      } else {
        path = requestedPath;
      }

      if(paramString != null) {
        parseParameters(paramString);
      }

      try {
        internalDispatchRequest(path, true);
        csocket.close();
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }

    }

    /**
     * Method which checks if given request contains cookie which is valid. If
     * cookie is not given, does not exist in map or exists and is not valid,
     * generates new session id and cookie.
     * 
     * @param request list of request lines
     */
    private void checkSessions(List<String> request) {
      String sidCandidate = null;
      for (String requestLine : request) {
        if(requestLine.startsWith("Cookie:")) {
          String[] cookies = requestLine.substring(8).split(";");
          for (int i = 0; i < cookies.length; i++) {
            if(cookies[i].split("=")[0].equals("sid")) {
              sidCandidate = cookies[i].split("=")[1].substring(1, cookies[i].split("=")[1].length() - 1);
              break;
            }
          }
          break;
        }
      }

      if(sidCandidate != null) {
        SessionMapEntry candidate = sessions.get(sidCandidate);
        if(candidate != null && host.equals(candidate.host)
            && candidate.validUntil >= Calendar.getInstance().getTimeInMillis()) {
          candidate.validUntil = Calendar.getInstance().getTimeInMillis() + sessionTimeout * 1000;
          permParams = candidate.map;
          return;
        }
      }

      SessionMapEntry sessionMapEntry = new SessionMapEntry(generateSid(), host,
          Calendar.getInstance().getTimeInMillis() + sessionTimeout * 1000);
      sessions.put(sessionMapEntry.sid, sessionMapEntry);
      outputCookies.add(new RCCookie("sid", sessionMapEntry.sid, host, "/", null));
      permParams = sessionMapEntry.map;
    }

    /**
     * Generates random session id. Session id is created by concatenating 20 random
     * uppercase letters.
     * 
     * @return session id
     */
    private String generateSid() {
      StringBuilder stringBuilder = new StringBuilder(20);
      for (int i = 0; i < 20; i++) {
        stringBuilder.append('a' + sessionRandom.nextInt(26));
      }
      return stringBuilder.toString();
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

    @Override
    public void dispatchRequest(String urlPath) throws Exception {
      internalDispatchRequest(urlPath, false);
    }

    /**
     * Method which handles file request. Finds given file from path, checks if file
     * exists, is readable and regular file and if it is located in documentRoot. If
     * file satisfies all criteria, reads file and writes it to created
     * {@link RequestContext} object.
     * 
     * If directCall parameter is <code>true</code> and file specified from given
     * url is located in private directory, error with status code 404 will be
     * returned. If <code>false</code>, this will be ignored.
     * 
     * @param urlPath path to file
     * @param directCall if <code>true</code>, 404 will be returned if specified
     *          path is located in private directory
     * @throws Exception if there is problem with writing to output stream.
     */
    public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
      Path requestedFile = documentRoot.resolve(urlPath.substring(1)).toAbsolutePath();

      if(directCall && urlPath.startsWith("/private")) {
        sendError(404, "File not found.");
        return;
      }

      String fileName = requestedFile.getFileName().toString();
      String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
      String mimeType = mimeTypes.get(extension);
      mimeType = mimeType == null ? "octet-stream" : mimeType;
      if(context == null) {
        context = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this);
      }

      if(urlPath.startsWith("/ext/")) {
        String fqcn = "hr.fer.zemris.java.webserver.workers." + urlPath.substring(5);
        Class<?> referenceToClass;
        try {
          referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
          Object newObject = referenceToClass.getDeclaredConstructor().newInstance();
          IWebWorker iww = (IWebWorker) newObject;
          iww.processRequest(context);
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
        return;
      }

      for (String worker : workersMap.keySet()) {
        if(worker.equals(urlPath)) {
          workersMap.get(worker).processRequest(context);
          return;
        }
      }

      if(!requestedFile.startsWith(documentRoot)) {
        sendError(403, "Forbidden.");
        return;
      }

      if(!Files.exists(requestedFile) || !Files.isRegularFile(requestedFile) || !Files.isReadable(requestedFile)) {
        sendError(404, "File not found");
        return;
      }

      if(extension.equals("smscr")) {
        String documentBody = null;
        try {
          documentBody = new String(Files.readAllBytes(requestedFile), StandardCharsets.UTF_8);
        } catch (IOException e1) {
          System.out.println("File " + urlPath + " cannot be read.");
          System.exit(-1);
        }
        context.setMimeType(mimeType);
        context.setStatusCode(200);
        new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), context).execute();
        ostream.close();
        return;
      }
      context.setMimeType(mimeType);
      context.setStatusCode(200);
      context.setContentLength(requestedFile.toFile().length());
      try {
        InputStream fileStream = Files.newInputStream(requestedFile);
        byte[] buf = new byte[1024];
        while (true) {
          int len = fileStream.read(buf);
          if(len < 1)
            break;
          context.write(buf, 0, len);
        }
      } catch (IOException e) {
        System.out.println("Problem with writing to socket.");
        return;
      }
    }
  }
}