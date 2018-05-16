package hr.fer.zemris.java.gui.charts;

/**
 * Represents one bar chart bar. X represents bar index starting at 1. Y
 * represents bar height.
 * 
 * @author tin
 *
 */
public class XYValue {
  /**
   * Bar index.
   */
  private int x;

  /**
   * Bar height.
   */
  private int y;

  /**
   * Constructor.
   * 
   * @param x bar index
   * @param y bar height
   */
  public XYValue(int x, int y) {
    super();
    this.x = x;
    this.y = y;
  }

  /**
   * Returns bar index.
   * 
   * @return bar index
   */
  public int getX() {
    return x;
  }

  /**
   * Returns bar height.
   * 
   * @return bar height
   */
  public int getY() {
    return y;
  }

}
