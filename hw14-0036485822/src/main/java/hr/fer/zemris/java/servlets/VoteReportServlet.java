package hr.fer.zemris.java.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.gui.charts.PieChart;
import hr.fer.zemris.java.model.PollData;
import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * Implements servlet, which when its doGet method is called, generates vote
 * report by creating pie chart and sending it as response. Pie chart generated
 * will contain all vote options from PollOptions table for given poll.
 * 
 * @author tin
 *
 */
@WebServlet("/servleti/glasanje-grafika")
public class VoteReportServlet extends HttpServlet {

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
    if(poll == null) {
      return;
    }
    PieChart pieChart = new PieChart("Votes", "Popularity", poll.getOptions());
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
