package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.model.SignUpForm;

@WebServlet("servleti/register")
public class RegisterServlet extends HttpServlet{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    SignUpForm form = new SignUpForm();
    form.fillFromHttpRequest(req);
    req.setAttribute("form", form);
    req.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(req, resp);
  }
}
