package hr.fer.zemris.java.hw11.jnotepadpp;

public class StatusBarInfo {
  int length;
  int line;
  int column;
  int selection;
  public StatusBarInfo(int length, int line, int column, int selection) {
    super();
    this.length = length;
    this.line = line;
    this.column = column;
    this.selection = selection;
  }
  
  public StatusBarInfo() {
    this(0, 0, 0, 0);
  }
  /**
   * @return the length
   */
  public int getLength() {
    return length;
  }
  /**
   * @param length the length to set
   */
  public void setLength(int length) {
    this.length = length;
  }
  /**
   * @return the line
   */
  public int getLine() {
    return line;
  }
  /**
   * @param line the line to set
   */
  public void setLine(int line) {
    this.line = line;
  }
  /**
   * @return the column
   */
  public int getColumn() {
    return column;
  }
  /**
   * @param column the column to set
   */
  public void setColumn(int column) {
    this.column = column;
  }
  /**
   * @return the selection
   */
  public int getSelection() {
    return selection;
  }
  /**
   * @param selection the selection to set
   */
  public void setSelection(int selection) {
    this.selection = selection;
  }
  
  
}
