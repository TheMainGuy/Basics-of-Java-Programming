package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Implements servlet, which when its doGet method is called, creates or updates
 * glasanje-rezultati.txt file and redirects request to /glasanje-rezultati.
 * 
 * File glasanje-rezultati.txt will be created if it does not exist or if it
 * does not contain any bands.
 * 
 * @author tin
 *
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if(req.getParameter("id") == null) {
      // TODO error
      return;
    }

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
    String id = req.getParameter("id");
    for (int i = 0; i < voteResults.length; i++) {
      if(voteResults[i].split("\t")[0].equals(id)) {
        int numberOfVotes = Integer.parseInt(voteResults[i].split("\t")[1]);
        numberOfVotes++;
        voteResults[i] = id + "\t" + Integer.toString(numberOfVotes);
        break;
      }
    }
    OutputStream fileStream = Files.newOutputStream(Paths.get(fileName));
    for (int i = 0; i < voteResults.length; i++) {
      fileStream.write((voteResults[i] + "\n").getBytes());
    }

    resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
  }
}
