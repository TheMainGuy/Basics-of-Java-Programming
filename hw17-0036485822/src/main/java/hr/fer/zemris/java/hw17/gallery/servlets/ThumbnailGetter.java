package hr.fer.zemris.java.hw17.gallery.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("thumbnail/*")
public class ThumbnailGetter extends HttpServlet {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String fileName = req.getServletContext().getRealPath("WEB-INF/slike" + req.getPathInfo());
    resp.setContentType(fileName);
    File file = new File(fileName);
    resp.setContentLength((int) file.length());
    FileInputStream inputStream = new FileInputStream(file);
    OutputStream outputStream = resp.getOutputStream();
    byte[] buf = new byte[1024];
    int count = 0;
    while ((count = inputStream.read(buf)) >= 0) {
      outputStream.write(buf, 0, count);
    }
    outputStream.close();
    inputStream.close();
  }
}
