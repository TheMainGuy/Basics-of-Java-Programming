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

/**
 * Implements servlet, which when its doGet method is called, renders demo
 * version of pie chart with dummy data. Used to demonstrate pie chart.
 * 
 * @author tin
 *
 */
@WebServlet("/reportImage")
public class ReportImageServlet extends HttpServlet {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    PieChart pieChart = new PieChart("test", "Which operating system is used the most?");
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
