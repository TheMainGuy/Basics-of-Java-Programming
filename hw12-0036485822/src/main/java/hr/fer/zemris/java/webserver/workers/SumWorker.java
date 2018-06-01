package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class SumWorker implements IWebWorker {

  @Override
  public void processRequest(RequestContext context) throws Exception {
    int a;
    int b;
    try {
     a = Integer.parseInt(context.getParameter("a"));
    } catch (Exception e) {
      a = 1;
    }
    
    try {
      b = Integer.parseInt(context.getParameter("b"));
    } catch (Exception e) {
      b = 2;
    }
    context.setTemporaryParameter("a", Integer.toString(a));
    context.setTemporaryParameter("b", Integer.toString(b));
    context.setTemporaryParameter("zbroj", Integer.toString(a + b));
    context.getDispatcher().dispatchRequest("/private/calc.smscr");
  }

}
