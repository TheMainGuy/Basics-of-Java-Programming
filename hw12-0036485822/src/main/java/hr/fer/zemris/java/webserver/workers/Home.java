package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Implements web worker for basic page with background color. If color is
 * changed for current session, its context's map of persistent parameters will
 * contain new color. If there is no value for key bgcolor, default color will
 * be used and stored.
 * 
 * @author tin
 *
 */
public class Home implements IWebWorker {
  /**
   * Default background color.
   */
  private final String DEFAULT_BACKGROUND_COLOR = "7F7F7F";

  @Override
  public void processRequest(RequestContext context) throws Exception {
    if(context.getPersistentParameter("bgcolor") != null) {
      context.setTemporaryParameter("background", context.getPersistentParameter("bgcolor"));
    } else {
      context.setTemporaryParameter("background", DEFAULT_BACKGROUND_COLOR);
    }

    context.getDispatcher().dispatchRequest("/private/home.smscr");

  }

}
