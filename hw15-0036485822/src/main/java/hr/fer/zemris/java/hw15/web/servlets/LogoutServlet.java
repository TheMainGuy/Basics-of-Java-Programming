package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Implements servlet, which when its doGet method is called, sets
 * current.user.id, current.user.fn, current.user.ln and current.user.nick
 * session attributes to null, which effectively logs current user out.
 * 
 * @author tin
 *
 */
@WebServlet("/servleti/logout")
public class LogoutServlet extends HttpServlet {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.getSession().setAttribute("current.user.id", null);
    req.getSession().setAttribute("current.user.fn", null);
    req.getSession().setAttribute("current.user.ln", null);
    req.getSession().setAttribute("current.user.nick", null);

    resp.sendRedirect("main");
  }
}
