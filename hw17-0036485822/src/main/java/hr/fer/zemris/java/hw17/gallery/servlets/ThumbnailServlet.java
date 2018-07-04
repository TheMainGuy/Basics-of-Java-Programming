package hr.fer.zemris.java.hw17.gallery.servlets;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("thumbnail/*")
public class ThumbnailServlet extends HttpServlet {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String fileName = req.getServletContext().getRealPath(Util.IMAGES_PATH + req.getPathInfo());
    File thumbnailsDirectory = new File(req.getServletContext().getRealPath(Util.THUMBNAILS_PATH));
    if(!thumbnailsDirectory.exists()) {
      thumbnailsDirectory.mkdir();
    }
    File thumbnailFile = new File(fileName.replace(Util.IMAGES_PATH, Util.THUMBNAILS_PATH));
    if(!thumbnailFile.exists()) {
      Util.createThumbnail(fileName, thumbnailFile);
    }
    System.out.println("Sending thumbnail " + fileName);
    Util.sendImage(resp, thumbnailFile.getAbsolutePath().toString());
  }
}
