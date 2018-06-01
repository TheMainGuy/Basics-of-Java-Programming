package hr.fer.zemris.java.webserver;

/**
 * Defines web worker which can process requests. Worker can run scripts located
 * on server to create content or directly write using {@link RequestContext}
 * object.
 * 
 * @author tin
 *
 */
public interface IWebWorker {
  /**
   * Creates content for client from given {@link RequestContext} object.
   * 
   * @param context request context from which content will be created
   * @throws Exception if there is problem with...
   */
  public void processRequest(RequestContext context) throws Exception;
}
