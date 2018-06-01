package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Implements web worker which returns parameters given by client in form of
 * table.
 * 
 * @author tin
 *
 */
public class EchoParams implements IWebWorker {

  @Override
  public void processRequest(RequestContext context) throws Exception {
    context.setMimeType("text/html");
    
    try {
      context.write("<html><body>");
      context.write("<table>\n");
      context.write("<thead>\n");
      context.write("<tr><th>Parameter</th><th>value</th></tr>\n");
      context.write("</thead>\n");
      context.write("<tbody>");
      for(String parameter : context.getParameterNames()) {
        context.write("<tr><td>");
        context.write(parameter);
        context.write("</td><td>");
        context.write(context.getParameter(parameter));
        context.write("</td></tr>\n");
      }
      context.write("</tbody>\n");
      context.write("</body></html>");
    } catch (IOException ex) {
      // Log exception to servers log...
      ex.printStackTrace();
    }
  }

}
