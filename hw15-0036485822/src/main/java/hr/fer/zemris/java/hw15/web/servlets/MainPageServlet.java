package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.LoginForm;

/**
 * Implements servlet, which when its doGet method is called, renders main page.
 * If user tried to log in but something went wrong, this servlet fills nick
 * field in form with nick user provided.
 * 
 * @author tin
 *
 */
@WebServlet("/servleti/main")
public class MainPageServlet extends HttpServlet {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    LoginForm form = new LoginForm();
    form.fillFromHttpRequest(req);
    List<String> nicks = DAOProvider.getDAO().getListOfNicks();
    req.setAttribute("form", form);
    req.setAttribute("authors", nicks);
    req.getRequestDispatcher("/WEB-INF/pages/MainPage.jsp").forward(req, resp);
  }

}
