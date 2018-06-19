package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.LoginForm;

@WebServlet("/servleti/checkLogin")
public class CheckLoginServlet extends HttpServlet {

  /**
   * 
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
