package hr.fer.zemris.java.hw17.gallery.init;

import java.nio.file.Paths;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.hw17.gallery.model.ImageDB;

@WebListener
public class Initialization implements ServletContextListener{

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    ImageDB.fillFromFile(Paths.get("./src/main/webapp/WEB-INF/opisnik.txt"));
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    // TODO Auto-generated method stub
    
  }

}
