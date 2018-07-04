package hr.fer.zemris.java.hw17.gallery.init;

import java.nio.file.Paths;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.hw17.gallery.model.ImageDB;

/**
 * Context listener which, when context is initialized, calls {@link ImageDB}
 * fillFromFile method using path to file containing image info.
 * 
 * @author tin
 *
 */
@WebListener
public class Initialization implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    ImageDB.fillFromFile(Paths.get(sce.getServletContext().getRealPath("/WEB-INF/opisnik.txt")), sce);
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    // TODO Auto-generated method stub

  }

}
