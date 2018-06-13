package hr.fer.zemris.java.gui.charts;

import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import hr.fer.zemris.java.model.PollData.PollOption;

/**
 * Implements pie chart using {@link JFreeChart}. Generates pie chart with
 * different color for each compared item.
 * 
 * @author tin
 *
 */
public class PieChart extends JFrame {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Reference to chart.
   */
  JFreeChart chart;

  /**
   * Default constructor for demonstration.
   * 
   * @param applicationTitle application title
   * @param chartTitle chart title which will be displayed above chart
   */
  public PieChart(String applicationTitle, String chartTitle) {
    super(applicationTitle);
    // This will create the dataset
    PieDataset dataset = createDataset();
    // based on the dataset we create the chart
    chart = createChart(dataset, chartTitle);
  }

  /**
   * Constructor.
   * 
   * @param applicationTitle application title
   * @param chartTitle chart title which will be displayed above chart
   * @param voteData list of data which will be used to generate chart
   */
  public PieChart(String applicationTitle, String chartTitle, List<PollOption> voteData) {
    super(applicationTitle);
    PieDataset dataset = createDataset(voteData);
    chart = createChart(dataset, chartTitle);

  }

  /**
   * Creates dataset for generating pie chart from given list of data.
   * 
   * @param voteData list of data which will be used to generate chart
   * @return {@link PieDataset} object for generating chart
   */
  private PieDataset createDataset(List<PollOption> voteData) {
    DefaultPieDataset result = new DefaultPieDataset();
    for (PollOption vote : voteData) {
      result.setValue(vote.getOptionTitle(), vote.getVotesCount());
    }
    return result;
  }

  /**
   * Creates a sample dataset for demonstration.
   * 
   * @return demonstration dataset
   */
  private PieDataset createDataset() {
    DefaultPieDataset result = new DefaultPieDataset();
    result.setValue("Linux", 100);
    result.setValue("Mac", 20);
    result.setValue("Windows", 51);
    return result;

  }

  /**
   * Creates pie chart using given dataset and title.
   * 
   * @param dataset dataset which will be used to draw chart
   * @param title title which will be rendered above chart
   * @return created pie chart
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