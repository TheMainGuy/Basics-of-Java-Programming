package hr.fer.zemris.java.webserver;

/**
 * Defines dispatcher object which implements method for handling requests.
 * 
 * @author tin
 *
 */
public interface IDispatcher {

  /**
   * Method which handles file request from given urlPath.
   * 
   * @param urlPath path to file
   * @throws Exception if there is problem with reading file or writing it
   */
  void dispatchRequest(String urlPath) throws Exception;
}
