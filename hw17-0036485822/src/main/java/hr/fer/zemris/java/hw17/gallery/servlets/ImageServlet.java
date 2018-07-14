package hr.fer.zemris.java.hw17.gallery.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet which, when its doGet method is called, reads requested file from
 * {@link HttpServletRequest} object and sends it using response output stream.
 * Uses {@link Util#sendImage(HttpServletResponse, String)} method to send
 * image.
 * 
 * @author tin
 *
 */
@WebServlet("/image/*")
public class ImageServlet extends HttpServlet {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String fileName = req.getServletContext().getRealPath(Util.IMAGES_PATH + req.getPathInfo());
    Util.sendImage(resp, fileName);
  }
}
