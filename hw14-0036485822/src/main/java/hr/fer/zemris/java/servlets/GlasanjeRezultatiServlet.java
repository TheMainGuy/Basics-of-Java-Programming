package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.model.PollData;
import hr.fer.zemris.java.model.PollData.PollOption;
import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * Implements servlet, which when its doGet method is called, prepares poll data
 * for rendering.
 * 
 * Then renders voting results using glasanjeRez.jsp file.
 * 
 * @author tin
 *
 */
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

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
    PollData poll = DAOProvider.getDao().getPoll(Long.parseLong(req.getParameter("pollID")));

    poll.getOptions().sort(new Comparator<PollOption>() {

      @Override
      public int compare(PollOption arg0, PollOption arg1) {
        if(arg0.getVotesCount() > arg1.getVotesCount()) {
          return -1;
        } else if(arg0.getVotesCount() == arg1.getVotesCount()) {
          return 0;
        } else {
          return 1;
        }
      }

    });

    List<PollOption> winners = new ArrayList<>();
    winners.add(poll.getOptions().get(0));
    for (int i = 1, n = poll.getOptions().size(); i < n; i++) {
      if(poll.getOptions().get(i).getVotesCount() == poll.getOptions().get(i - 1).getVotesCount()) {
        winners.add(poll.getOptions().get(i));
      } else {
        break;
      }
    }

    req.setAttribute("poll", poll);
    req.setAttribute("winners", winners);
    req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
  }

  public static class VoteData {
    int id;
    String bandName;
    int voteCount;
    String song;

    public VoteData(int id, String bandName, int voteCount) {
      this(id, bandName, voteCount, "https://www.youtube.com/watch?v=dQw4w9WgXcQ");
    }

    public VoteData(int id, String bandName, int voteCount, String song) {
      this.id = id;
      this.bandName = bandName;
      this.voteCount = voteCount;
      this.song = song;
    }

    /**
     * @return the id
     */
    public int getId() {
      return id;
    }

    /**
     * @return the bandName
     */
    public String getBandName() {
      return bandName;
    }

    /**
     * @return the voteCount
     */
    public int getVoteCount() {
      return voteCount;
    }

    public String getSong() {
      return song;
    }

  }
}
