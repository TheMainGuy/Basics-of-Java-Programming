package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Implements servlet, which when its doGet method is called, sets web
 * application color to parameter color for current session.
 * 
 * @author tin
 *
 */
@WebServlet("/setcolor")
public class SetColorServlet extends HttpServlet {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String color = req.getParameter("color");
    req.getSession().setAttribute("pickedBgColor", color);
    req.getRequestDispatcher("color.jsp").forward(req, resp);
  }

}
