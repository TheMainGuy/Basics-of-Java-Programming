package hr.fer.zemris.java.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.gui.charts.PieChart;
import hr.fer.zemris.java.servlets.GlasanjeRezultatiServlet.VoteData;

@WebServlet("/glasanje-grafika")
public class BandVoteReportServlet extends HttpServlet {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
    String file = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
    String[] voteResults = file.split("\n");
    List<VoteData> sortedVotes = new ArrayList<>();
    for (String voteResult : voteResults) {
      String id = voteResult.split("\t")[0];
      sortedVotes.add(new VoteData(Integer.parseInt(id), ServletUtil.getBand(req, id),
          Integer.parseInt(voteResult.split("\t")[1])));
    }
    
    PieChart pieChart = new PieChart("Band votes", "Band popularity", sortedVotes);
    BufferedImage bim = pieChart.getBufferedImage();
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    try {
      ImageIO.write(bim, "png", bos);
      resp.setContentType("image/png");
      resp.getOutputStream().write(bos.toByteArray());
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
