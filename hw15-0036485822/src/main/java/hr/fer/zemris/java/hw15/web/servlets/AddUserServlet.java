package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.SignUpForm;

/**
 * Implements servlet, which when its doGet method is called, validates filled
 * sign up form and if does not have any errors, creates new user. If any field
 * is not filled or has error, form must be resubmited.
 * 
 * @author tin
 *
 */
@WebServlet("/servleti/addUser")
public class AddUserServlet extends HttpServlet {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if(req.getParameter("method").equals("Cancel")) {
      resp.sendRedirect("main");
      return;
    }

    SignUpForm form = new SignUpForm();
    form.fillFromHttpRequest(req);
    form.validate();

    if(form.hasErrors()) {
      req.setAttribute("form", form);
      req.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(req, resp);
      return;
    }

    BlogUser blogUser = new BlogUser();
    form.fillToUser(blogUser);
    DAOProvider.getDAO().addNewUser(blogUser);
    System.out.println("Tu je greska");
    req.getRequestDispatcher("/WEB-INF/pages/SuccessfulRegistration.jsp").forward(req, resp);

  }
}
