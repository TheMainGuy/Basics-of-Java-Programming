package hr.fer.zemris.java.servlets;

import java.util.Calendar;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Implements servlet context listener which stores time of application start.
 * 
 * @author tin
 *
 */
@WebListener
public class ServletContextListenerImpl implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    sce.getServletContext().setAttribute("time", Calendar.getInstance().getTimeInMillis());
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    // TODO Auto-generated method stub

  }

}
