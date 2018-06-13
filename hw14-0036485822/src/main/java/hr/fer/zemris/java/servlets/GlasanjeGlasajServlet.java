package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.model.PollData;
import hr.fer.zemris.java.model.PollData.PollOption;
import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * Implements servlet, which when its doGet method is called, increments
 * votesCount parameter in PollOptions table for given optionID.
 * 
 * @author tin
 *
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if(req.getParameter("pollID") == null || req.getParameter("optionID") == null) {
      // TODO error
      return;
    }

    PollData poll = DAOProvider.getDao().getPoll(Long.parseLong(req.getParameter("pollID")));
    PollOption selectedOption = null;
    long selectedOptionId = Long.parseLong(req.getParameter("optionID"));
    for (PollOption option : poll.getOptions()) {
      if(option.getOptionId() == selectedOptionId) {
        selectedOption = option;
        break;
      }
    }
    DAOProvider.getDao().updateVotesCount(selectedOption.getVotesCount() + 1, selectedOption.getOptionId());
    resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID=" + poll.getId());
  }
}
