package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.LoginForm;

/**
 * Implements servlet, which when its doPost method is called, validates filled
 * log in form and if does not have any errors, checks if given input is
 * correct. If any field is not filled or has error, form must be resubmited.
 * 
 * @author tin
 *
 */
@WebServlet("/servleti/checkLogin")
public class CheckLoginServlet extends HttpServlet {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if(req.getParameter("method").equals("cancel")) {
      resp.sendRedirect("main");
    }

    LoginForm form = new LoginForm();
    form.fillFromHttpRequest(req);
    form.validate();

    req.setAttribute("form", form);
    if(form.hasErrors()) {
      List<String> nicks = DAOProvider.getDAO().getListOfNicks();
      req.setAttribute("authors", nicks);
      req.getRequestDispatcher("/WEB-INF/pages/MainPage.jsp").forward(req, resp);
      return;
    }

    BlogUser user = DAOProvider.getDAO().getUserFromNick(form.getNick());
    req.getSession().setAttribute("current.user.id", user.getId());
    req.getSession().setAttribute("current.user.fn", user.getFirstName());
    req.getSession().setAttribute("current.user.ln", user.getLastName());
    req.getSession().setAttribute("current.user.nick", user.getNick());

    resp.sendRedirect("main");
  }

}
