package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RequestContext {
  private OutputStream outputStream;
  private Charset charset;
  private String encoding = "UTF-8";
  private int statusCode = 200;
  private String statusText = "OK";
  private String mimeType = "text/html";
  Long contentLength = null;
  private Map<String, String> parameters;
  private Map<String, String> temporaryParameters = new HashMap<>();
  private Map<String, String> persistentParameters;
  private List<RCCookie> outputCookies;
  private boolean headerGenerated = false;

  public RequestContext(OutputStream outputStream, Map<String, String> parameters,
      Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
    if(outputStream == null) {
      throw new NullPointerException("Output stream can not be null.");
    }
    this.outputStream = outputStream;
    this.parameters = parameters != null ? parameters : new HashMap<>();
    this.persistentParameters = persistentParameters != null ? persistentParameters : new HashMap<>();
    this.outputCookies = outputCookies != null ? outputCookies : new ArrayList<>();
  }

  /**
   * Sets encoding to given string. Throws exception if header is already
   * generated.
   * 
   * @param encoding encoding to be set
   * @throws RuntimeException if header is already generated
   */
  public void setEncoding(String encoding) {
    if(headerGenerated) {
      throw new RuntimeException("Header already generated.");
    }
    this.encoding = encoding;
  }

  /**
   * Sets statusCode to given number. Throws exception if header is already
   * generated.
   * 
   * @param statusCode statusCode to be set
   * @throws RuntimeException if header is already generated
   */
  public void setStatusCode(int statusCode) {
    if(headerGenerated) {
      throw new RuntimeException("Header already generated.");
    }
    this.statusCode = statusCode;
  }

  /**
   * Sets statusText to given string. Throws exception if header is already
   * generated.
   * 
   * @param statusText statusText to be set
   * @throws RuntimeException if header is already generated
   */
  public void setStatusText(String statusText) {
    if(headerGenerated) {
      throw new RuntimeException("Header already generated.");
    }
    this.statusText = statusText;
  }

  /**
   * Sets mimeType to given string. Throws exception if header is already
   * generated.
   * 
   * @param mimeType mimeType to be set
   * @throws RuntimeException if header is already generated
   */
  public void setMimeType(String mimeType) {
    if(headerGenerated) {
      throw new RuntimeException("Header already generated.");
    }
    this.mimeType = mimeType;
  }

  /**
   * Sets contentLength to given number. Throws exception if header is already
   * generated.
   * 
   * @param contentLength content length to be set
   * @throws RuntimeException if header is already generated
   */
  public void setContentLength(long contentLength) {
    if(headerGenerated) {
      throw new RuntimeException("Header already generated.");
    }
    this.contentLength = contentLength;
  }

  /**
   * Adds given cookie to outputCookies list. Throws exception if cookie is null
   * or header is already generated.
   * 
   * @param cookie cookie to be added
   * @throws RuntimeException if header is already generated
   * @throws NullPointerException if given cookie is <code>null</code>
   */
  public void addRCCookie(RCCookie cookie) {
    if(headerGenerated) {
      throw new RuntimeException("Header already generated.");
    }
    if(cookie == null) {
      throw new NullPointerException("Cookie can not be null");
    }
    this.outputCookies.add(cookie);
  }

  /**
   * Returns parameter from parameters map for given key.
   * 
   * @param name key for which parameter will be get
   * @return parameter
   */
  public String getParameter(String name) {
    return parameters.get(name);
  }

  /**
   * Retruns set of keys for parameters map
   * 
   * @return set of keys for parameters map
   */
  public Set<String> getParameterNames() {
    return parameters.keySet();
  }

  /**
   * Returns parameter from persistentParameters map for given key.
   * 
   * @param name key for which parameter will be get
   * @return parameter
   */
  public String getPersistentParameter(String name) {
    return persistentParameters.get(name);
  }

  /**
   * Retruns set of keys for persistentParameters map
   * 
   * @return set of keys for persistentParameters map
   */
  public Set<String> getPersistentParameterNames() {
    return persistentParameters.keySet();
  }

  /**
   * Puts given key value pair in persistentParameters map.
   * 
   * @param name key
   * @param value value
   */
  public void setPersistentParameter(String name, String value) {
    persistentParameters.put(name, value);
  }

  /**
   * Removes key value pair from persistentParameters map.
   * 
   * @param name key
   */
  public void removePersistentParameter(String name) {
    persistentParameters.remove(name);
  }

  /**
   * Returns parameter from temporaryParameters map for given key.
   * 
   * @param name key for which parameter will be get
   * @return parameter
   */
  public String getTemporaryParameter(String name) {
    return temporaryParameters.get(name);
  }

  /**
   * Retruns set of keys for temporaryParameters map
   * 
   * @return set of keys for temporaryParameters map
   */
  public Set<String> getTemporaryParameterNames() {
    return temporaryParameters.keySet();
  }

  /**
   * Puts given key value pair in temporaryParameters map.
   * 
   * @param name key
   * @param value value
   */
  public void setTemporaryParameter(String name, String value) {
    temporaryParameters.put(name, value);
  }

  /**
   * Removes key value pair from temporaryParameters map.
   * 
   * @param name key
   */
  public void removeTemporaryParameter(String name) {
    temporaryParameters.remove(name);
  }

  /**
   * Writes data into outputStream. If header is not generated, generates header
   * before writing any data.
   * 
   * @param data data to be written in outputStream
   * @return this object
   * @throws IOException if there is problem with writing to outputStream
   */
  public RequestContext write(byte[] data) throws IOException {
    if(!headerGenerated) {
      generateHeader();
    }
    outputStream.write(data);
    return this;
  }

  /**
   * Writes text into outputStream. If header is not generated, generates header
   * before writing any data.
   * 
   * @param text text to be written in outputStream
   * @return this object
   * @throws IOException if there is problem with writing to outputStream
   */
  public RequestContext write(String text) throws IOException {
    if(!headerGenerated) {
      generateHeader();
    }
    outputStream.write(text.getBytes(charset));
    return this;
  }

  /**
   * Writes len bytes of data into outputStream starting from offset byte. If
   * header is not generated, generates header before writing any data.
   * 
   * @param data data to be written in outputStream
   * @param offset offset from which data will start to write
   * @param len number of bytes written
   * @return this object
   * @throws IOException if there is problem with writing to outputStream
   */
  public RequestContext write(byte[] data, int offset, int len) throws IOException {
    if(!headerGenerated) {
      generateHeader();
    }

    outputStream.write(data, offset, len);
    return this;
  }

  /**
   * Method which generates header and writes it to outputStream. Then sets
   * headerGenerated to true.
   * 
   * @throws IOException if there is problem with writing to outputStream
   */
  private void generateHeader() throws IOException {
    charset = Charset.forName(encoding);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("HTTP/1.1 ").append(statusCode).append(" ").append(statusText).append("\r\n");
    stringBuilder.append("Content-Type: ").append(mimeType);
    if(mimeType.startsWith("text/")) {
      stringBuilder.append("; charset=").append(encoding);
    }
    stringBuilder.append("\r\n");
    if(contentLength != null) {
      stringBuilder.append("Content-Length: " + contentLength + "\r\n");
    }

    for (RCCookie outputCookie : outputCookies) {
      stringBuilder.append("Set-Cookie: ").append(outputCookie.getName()).append("=\"").append(outputCookie.getValue())
          .append("\"");
      if(outputCookie.getDomain() != null) {
        stringBuilder.append("; Domain=").append(outputCookie.getDomain());
      }
      if(outputCookie.getPath() != null) {
        stringBuilder.append("; Path=").append(outputCookie.getPath());
      }
      if(outputCookie.getMaxAge() != null) {
        stringBuilder.append("; Max-Age=").append(outputCookie.getMaxAge());
      }
      stringBuilder.append("\r\n");
    }
    stringBuilder.append("\r\n");

    outputStream.write(stringBuilder.toString().getBytes(StandardCharsets.ISO_8859_1));
    headerGenerated = true;
  }

  /**
   * Class represents cookie with name, value, domain, path and max age variables.
   * Each cookie can not be modified after created.
   * 
   * @author tin
   *
   */
  public static class RCCookie {
    /**
     * Cookie name.
     */
    private String name;

    /**
     * Cookie value.
     */
    private String value;

    /**
     * Cookie domain.
     */
    private String domain;

    /**
     * Cookie path.
     */
    private String path;

    /**
     * Cookie max age.
     */
    private Integer maxAge;

    /**
     * Constructor.
     * 
     * @param name cookie name
     * @param value cookie value
     * @param domain cookie domain
     * @param path cookie path
     * @param maxAge cookie max age
     */
    public RCCookie(String name, String value, String domain, String path, Integer maxAge) {
      super();
      if(name == null) {
        throw new NullPointerException("Cookie name can not be null.");
      }
      if(value == null) {
        throw new NullPointerException("Cookie value can not be null.");
      }

      this.name = name;
      this.value = value;
      this.domain = domain;
      this.path = path;
      this.maxAge = maxAge;
    }

    /**
     * Constructor for example given in homework.
     * 
     * @param name cookie name
     * @param value cookie value
     * @param domain cookie domain
     * @param path cookie path
     * @param maxAge cookie max age
     */
    public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
      this(name, value, domain, path, maxAge);
    }

    /**
     * Returns cookie name.
     * 
     * @return cookie name
     */
    public String getName() {
      return name;
    }

    /**
     * Returns cookie value.
     * 
     * @return cookie value
     */
    public String getValue() {
      return value;
    }

    /**
     * Returns cookie domain.
     * 
     * @return cookie domain
     */
    public String getDomain() {
      return domain;
    }

    /**
     * Returns cookie path.
     * 
     * @return cookie path
     */
    public String getPath() {
      return path;
    }

    /**
     * Returns cookie max age.
     * 
     * @return cookie max age
     */
    public Integer getMaxAge() {
      return maxAge;
    }

  }
}
