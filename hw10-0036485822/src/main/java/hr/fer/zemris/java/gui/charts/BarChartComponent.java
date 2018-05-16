package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
 * Component which draws bar chart in itself using {@link Graphics2D}.
 * 
 * @author tin
 *
 */
public class BarChartComponent extends JComponent {
  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Fixed number of pixels between axis description and axis numbers.
   */
  private final int fixedSpace = 10;

  /**
   * Fixed number of pixels between axis numbers and axis line.
   */
  private final int fixedSpace2 = 4;

  /**
   * Bar chart.
   */
  private BarChart barChart;

  /**
   * Constructor.
   * 
   * @param barChart bar chart which will be drawn
   */
  public BarChartComponent(BarChart barChart) {
    super();
    this.barChart = barChart;
  }

  /**
   * Method which draws bar chart.
   */
  public void paint(Graphics g) {
    Graphics2D graphics2D = (Graphics2D) g;
    /**
     * Container height.
     */
    int height = getHeight();
    /**
     * Container width.
     */
    int width = getWidth();

    /**
     * Coordinates of x-axis description.
     */
    XYValue xDescriptionCoordinates = new XYValue(
        width / 2 - graphics2D.getFontMetrics().stringWidth(barChart.getxDescription()) / 2, height - 1);

    /**
     * Draws x-axis description.
     */
    graphics2D.drawString(barChart.getxDescription(), xDescriptionCoordinates.getX(), xDescriptionCoordinates.getY());

    /**
     * Draws y-axis description.
     */
    XYValue yDescriptionCoordinates = new XYValue(graphics2D.getFontMetrics().getHeight(),
        height / 2 + graphics2D.getFontMetrics().stringWidth(barChart.getyDescription()) / 2);
    AffineTransform defaultAt = graphics2D.getTransform();
    AffineTransform at = new AffineTransform();
    at.rotate(-Math.PI / 2);
    graphics2D.setTransform(at);
    graphics2D.drawString(barChart.getyDescription(), -yDescriptionCoordinates.getY(), yDescriptionCoordinates.getX());
    graphics2D.setTransform(defaultAt);

    /**
     * Defines chart origin coordinates.
     */
    XYValue chartOrigin = new XYValue(
        yDescriptionCoordinates.getX() + fixedSpace + fixedSpace2
            + graphics2D.getFontMetrics().stringWidth(Integer.toString(barChart.getMaxY())),
        xDescriptionCoordinates.getY() - fixedSpace - fixedSpace2 - graphics2D.getFontMetrics().getHeight() * 2);

    /**
     * Draws axis lines.
     */
    graphics2D.drawLine(chartOrigin.getX(), chartOrigin.getY(), (int) Math.floor(width * 0.98), chartOrigin.getY());
    graphics2D.drawLine(chartOrigin.getX(), chartOrigin.getY(), chartOrigin.getX(), (int) Math.floor(height * 0.02));

    /**
     * Defines starting coordinate for y-axis numbers.
     */
    XYValue yNumbers = new XYValue(yDescriptionCoordinates.getX() + fixedSpace, chartOrigin.getY());
    /**
     * How many numbers will be displayed on y-axis.
     */
    int numberOfNumbers = (chartOrigin.getY() - (int) Math.floor(height * 0.02)) / 25;
    numberOfNumbers = numberOfNumbers > barChart.getMaxY() ? barChart.getMaxY() : numberOfNumbers;
    /**
     * Distance between numbers on y-axis.
     */
    int distance = 0;
    if(numberOfNumbers != 0) {
      distance = (chartOrigin.getY() - (int) Math.floor(height * 0.02)) / numberOfNumbers;
    }

    /**
     * Bar width.
     */
    int barWidth = ((int) Math.floor(width * 0.98) - chartOrigin.getX()) / barChart.getValues().size();

    /**
     * Iterates and draws y-axis numbers and lines.
     */
    for (int i = 0; i <= numberOfNumbers; i++) {
      if(numberOfNumbers == 0) {
        break;
      }
      /**
       * Coordinates of single y-axis number.
       */
      XYValue numberCoordinate = new XYValue(yNumbers.getX(),
          chartOrigin.getY() + graphics2D.getFontMetrics().getHeight() / 3 - i * distance);

      /**
       * Draws y-axis number.
       */
      graphics2D.drawString(Integer.toString(i * (barChart.getMaxY() - barChart.getMinY()) / numberOfNumbers),
          numberCoordinate.getX(), numberCoordinate.getY());

      /**
       * Draws line.
       */
      graphics2D.drawLine(
          numberCoordinate.getX() + fixedSpace2
              + graphics2D.getFontMetrics()
                  .stringWidth(Integer.toString(i * (barChart.getMaxY() - barChart.getMinY()) / numberOfNumbers)),
          numberCoordinate.getY() - graphics2D.getFontMetrics().getHeight() / 3, (int) Math.floor(width * 0.98),
          numberCoordinate.getY() - graphics2D.getFontMetrics().getHeight() / 3);
    }

    /**
     * Draws x-axis numbers and fills charts.
     */
    for (int i = 1, n = barChart.getValues().size(); i <= n; i++) {
      XYValue numberCoordinate = new XYValue(
          chartOrigin.getX() + barWidth * i - barWidth / 2
              - graphics2D.getFontMetrics().stringWidth(Integer.toString(i)),
          chartOrigin.getY() + fixedSpace2 + graphics2D.getFontMetrics().getHeight());
      graphics2D.drawString(Integer.toString(i), numberCoordinate.getX(), numberCoordinate.getY());

      /**
       * Draws bar in orange color.
       */
      graphics2D.setColor(Color.ORANGE);
      graphics2D.fillRect(chartOrigin.getX() + barWidth * (i - 1) + 1,
          chartOrigin.getY() - numberOfNumbers * (barChart.getValues().get(i - 1).getY() - barChart.getMinY())
              * distance / (barChart.getMaxY() - barChart.getMinY()),
          barWidth - 1, numberOfNumbers * (barChart.getValues().get(i - 1).getY() - barChart.getMinY()) * distance
              / (barChart.getMaxY() - barChart.getMinY()));
      graphics2D.setColor(Color.BLACK);
    }
  }

}
