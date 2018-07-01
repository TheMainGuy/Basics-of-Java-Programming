package hr.fer.zemris.java.hw17.gallery.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw17.gallery.model.ImageDB;

/**
 * Servle which, when its doGet method is called, writes all tags in JSON format
 * using {@link HttpServletResponse} getWriter method.
 * 
 * @author tin
 *
 */
@WebServlet("/servlets/tags")
public class TagsServlet extends HttpServlet {

  /**
   * Serial version UID.
   * 
   */
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("application/json;charset=UTF-8");
    Gson gson = new Gson();
    resp.getWriter().write(gson.toJson(ImageDB.getTags()));
    resp.getWriter().flush();
  }
}
