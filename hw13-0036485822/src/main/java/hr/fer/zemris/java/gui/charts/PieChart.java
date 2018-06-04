package hr.fer.zemris.java.gui.charts;

import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import hr.fer.zemris.java.servlets.GlasanjeRezultatiServlet.VoteData;

public class PieChart extends JFrame {

  private static final long serialVersionUID = 1L;
  JFreeChart chart;

  /**
   * Default constructor for demonstration.
   * 
   * @param applicationTitle application title
   * @param chartTitle chart title which will be displayed
   */
  public PieChart(String applicationTitle, String chartTitle) {
    super(applicationTitle);
    // This will create the dataset
    PieDataset dataset = createDataset();
    // based on the dataset we create the chart
    chart = createChart(dataset, chartTitle);
    // we put the chart into a panel
    ChartPanel chartPanel = new ChartPanel(chart);
    // default size
    chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
    // add it to our application
    setContentPane(chartPanel);
  }
  
  public PieChart(String applicationTitle, String chartTitle, List<VoteData> voteData) {
    super(applicationTitle);
    PieDataset dataset = createDataset(voteData);
    chart = createChart(dataset, chartTitle);
    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
    setContentPane(chartPanel);
  }

  private PieDataset createDataset(List<VoteData> voteData) {
    DefaultPieDataset result = new DefaultPieDataset();
    for(VoteData bandVoteData : voteData) {
      result.setValue(bandVoteData.getBandName(), bandVoteData.getVoteCount());
    }
    return result;
  }

  /**
   * Creates a sample dataset
   */
  private PieDataset createDataset() {
    DefaultPieDataset result = new DefaultPieDataset();
    result.setValue("Linux", 100);
    result.setValue("Mac", 20);
    result.setValue("Windows", 51);
    return result;

  }

  /**
   * Creates a chart
   */
  private JFreeChart createChart(PieDataset dataset, String title) {

    JFreeChart chart = ChartFactory.createPieChart3D(title, // chart title
        dataset, // data
        true, // include legend
        true, false);

    PiePlot3D plot = (PiePlot3D) chart.getPlot();
    plot.setStartAngle(290);
    plot.setDirection(Rotation.CLOCKWISE);
    plot.setForegroundAlpha(0.5f);
    return chart;
  }

  /**
   * Returns buffered image made from drawn pie chart.
   * 
   * @return buffered image made from drawn pie chart
   */
  public BufferedImage getBufferedImage() {
    return chart.createBufferedImage(500, 270);
  }
}