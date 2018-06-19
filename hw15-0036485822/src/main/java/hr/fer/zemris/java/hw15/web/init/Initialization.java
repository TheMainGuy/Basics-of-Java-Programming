package hr.fer.zemris.java.hw15.web.init;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.hw15.dao.jpa.JPAEMFProvider;

/**
 * Implements servlet context listener which, when context is initialized,
 * creates {@link EntityManagerFactory} object which will be used to provide
 * connections to database. Adds that object to servlet context under
 * "my.application.emf" key.
 * 
 * When context is destroyed, destroys sets its value to null in servlet context
 * and closes {@link EntityManagerFactory} object.
 * 
 * @author tin
 *
 */
@WebListener
public class Initialization implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("baza.podataka.za.blog");
    sce.getServletContext().setAttribute("my.application.emf", emf);
    JPAEMFProvider.setEmf(emf);
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    JPAEMFProvider.setEmf(null);
    EntityManagerFactory emf = (EntityManagerFactory) sce.getServletContext().getAttribute("my.application.emf");
    if(emf != null) {
      emf.close();
    }
  }
}