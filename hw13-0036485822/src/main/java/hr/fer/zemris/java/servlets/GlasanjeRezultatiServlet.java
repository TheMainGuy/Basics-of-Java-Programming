package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Implements servlet, which when its doGet method is called, creates or updates
 * glasanje-rezultati.txt file if needed, sorts bands and defines which bands
 * are the winning ones.
 * 
 * Then renders voting results using glasanjeRez.jsp file.
 * 
 * File glasanje-rezultati.txt will be created if it does not exist or if it
 * does not contain any bands.
 * 
 * @author tin
 *
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
    if(!Files.exists(Paths.get(fileName))) {
      ServletUtil.createVoteFile(req, fileName);
    }
    String file = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);

    String[] voteResults = file.split("\n");
    if(voteResults.length < 2) {
      ServletUtil.createVoteFile(req, fileName);
      file = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
      voteResults = file.split("\n");
    }

    List<VoteData> sortedVotes = new ArrayList<>();
    for (String voteResult : voteResults) {
      String id = voteResult.split("\t")[0];
      sortedVotes.add(new VoteData(Integer.parseInt(id), ServletUtil.getBand(req, id),
          Integer.parseInt(voteResult.split("\t")[1]), ServletUtil.getSong(req, id)));
    }

    sortedVotes.sort(new Comparator<VoteData>() {

      @Override
      public int compare(VoteData arg0, VoteData arg1) {
        if(arg0.getVoteCount() > arg1.getVoteCount()) {
          return -1;
        } else if(arg0.getVoteCount() == arg1.getVoteCount()) {
          return 0;
        } else {
          return 1;
        }
      }

    });

    List<VoteData> winningBands = new ArrayList<>();
    winningBands.add(sortedVotes.get(0));
    for (int i = 1, n = sortedVotes.size(); i < n; i++) {
      if(sortedVotes.get(i).getVoteCount() == sortedVotes.get(i - 1).getVoteCount()) {
        winningBands.add(sortedVotes.get(i));
      } else {
        break;
      }
    }

    req.setAttribute("sortedVotes", sortedVotes);
    req.setAttribute("winningBands", winningBands);
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
