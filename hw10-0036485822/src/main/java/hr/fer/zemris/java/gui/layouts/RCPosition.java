package hr.fer.zemris.java.gui.layouts;

/**
 * Represents row and column position in layout.
 * 
 * @author tin
 *
 */
public class RCPosition {
  /**
   * Row number.
   */
  private int row;
  /**
   * Column number.
   */
  private int column;

  /**
   * Constructor.
   * @param row row number
   * @param column column number
   */
  public RCPosition(int row, int column) {
    this.row = row;
    this.column = column;
  }

  /**
   * Returns row number.
   * @return row number
   */
  public int getRow() {
    return row;
  }

  /**
   * Returns column number.
   * @return column number
   */
  public int getColumn() {
    return column;
  }

}
