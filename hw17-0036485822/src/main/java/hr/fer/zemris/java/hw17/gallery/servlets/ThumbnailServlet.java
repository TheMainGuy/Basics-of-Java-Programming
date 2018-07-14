package hr.fer.zemris.java.hw17.gallery.servlets;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet which, when its doGet method is called, reads requested file from
 * {@link HttpServletRequest} object and sends its thumbnail using response
 * output stream.
 * 
 * If thumbnail directory does not exist, creates it. If requested thumbnail
 * does not exist, uses {@link Util#createThumbnail(String, File)} method to
 * create it.
 * 
 * After checking for thumbnail file and creating it if necessary, sends
 * thumbnail file using {@link Util#sendImage(HttpServletResponse, String)}
 * method.
 * 
 * @author tin
 *
 */
@WebServlet("/thumbnail/*")
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
    Util.sendImage(resp, thumbnailFile.getAbsolutePath().toString());
  }
}
