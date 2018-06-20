package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.model.LoginForm;

/**
 * Implements servlet, which when its doGet method is called, redirects to main
 * servlet.
 * 
 * @author tin
 *
 */
@WebServlet("/index.jsp")
public class IndexServlet extends HttpServlet {
  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    LoginForm form = new LoginForm();
    req.setAttribute("form", form);
    resp.sendRedirect("servleti/main");
  }
}
