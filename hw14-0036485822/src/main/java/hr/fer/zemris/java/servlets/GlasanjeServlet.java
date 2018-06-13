package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.model.PollData;
import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * Implements servlet, which when its doGet method is called, renders list of
 * poll options defined in database.
 * 
 * @author tin
 *
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if(req.getParameter("pollID") == null) {
      // TODO error
      return;
    }

    long id = Long.parseLong(req.getParameter("pollID"));
    PollData poll = DAOProvider.getDao().getPoll(id);
    req.setAttribute("poll", poll);
    req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
  }
}
