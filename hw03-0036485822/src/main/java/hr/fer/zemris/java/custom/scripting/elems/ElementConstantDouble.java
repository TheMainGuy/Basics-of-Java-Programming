package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class with represents decimal number tag element.
 * @author tin
 *
 */
public class ElementConstantDouble extends Element {
  private double value;
  
  /**
   * Creates double number element.
   * @param value decimal value
   */
  public ElementConstantDouble(double value) {
    super();
    this.value = value;
  }

  /**
   * Method which returns string representation of this element.
   * @return this element converted to string.
   */
  @Override
  public String asText() {
    return Double.toString(value);
  }
}
