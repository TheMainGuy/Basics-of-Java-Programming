package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Represents one bar chart. Bar chart has x and y axis.
 * 
 * @author tin
 *
 */
public class BarChart {
  private List<XYValue> values;
  private String xDescription;
  private String yDescription;
  private int minY;
  private int maxY;
  private int space;

  /**
   * Constructor.
   * 
   * @param values values
   * @param xDescription x-axis description
   * @param yDescription y-axis description
   * @param minY minimum y
   * @param maxY maximum y
   * @param space space between bars
   */
  public BarChart(List<XYValue> values, String xDescription, String yDescription, int minY, int maxY, int space) {
    super();
    this.values = values;
    this.xDescription = xDescription;
    this.yDescription = yDescription;
    this.minY = minY;
    this.maxY = maxY;
    this.space = space;
  }

  /**
   * Returns bar values.
   * 
   * @return the values
   */
  public List<XYValue> getValues() {
    return values;
  }

  /**
   * Returns description alnogside x-axis.
   * 
   * @return the xDescription
   */
  public String getxDescription() {
    return xDescription;
  }

  /**
   * Returns description alnogside y-axis.
   * 
   * @return the yDescription
   */
  public String getyDescription() {
    return yDescription;
  }

  /**
   * Returns minimum y value.
   * 
   * @return the minY
   */
  public int getMinY() {
    return minY;
  }

  /**
   * Returns maximum y value.
   * 
   * @return the maxY
   */
  public int getMaxY() {
    return maxY;
  }

  /**
   * Returns space between bars.
   * 
   * @return the space
   */
  public int getSpace() {
    return space;
  }

}
