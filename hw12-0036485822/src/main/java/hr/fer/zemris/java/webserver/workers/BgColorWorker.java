package hr.fer.zemris.java.webserver.workers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Implements web worker which changes index2.html background color for one
 * session. Notifies client if the color change went successfully or not.
 * 
 * @author tin
 *
 */
public class BgColorWorker implements IWebWorker {

  @Override
  public void processRequest(RequestContext context) throws Exception {
    String bgColor = context.getParameter("bgcolor");
    context.write("<html><body>");
    context.write("<a href=\"/index2.html\" target=\"_blank\">index2.html</a><br>");
    if(bgColor != null) {
      Pattern colorPattern = Pattern.compile("[0-9a-fA-F]{6}");
      Matcher m = colorPattern.matcher(bgColor);
      if(m.matches() && bgColor.length() == 6) {
        context.setPersistentParameter("bgcolor", bgColor);
        context.write("<p>Color successfully updated.");
        context.write("</body></html>");
        return;
      }
    }
    context.write("<p>Color did not update.");
    context.write("</body></html>");
  }

}
