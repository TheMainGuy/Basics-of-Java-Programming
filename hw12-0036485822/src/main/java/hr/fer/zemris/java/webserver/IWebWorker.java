package hr.fer.zemris.java.webserver;

/**
 * Defines web worker which can process request.
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
