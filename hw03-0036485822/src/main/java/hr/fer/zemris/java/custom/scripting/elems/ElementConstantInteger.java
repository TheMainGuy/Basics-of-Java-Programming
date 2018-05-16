package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class with represents integer number tag element.
 * @author tin
 *
 */
public class ElementConstantInteger extends Element {
  private int value;
  

  /**
   * Creates integer number element.
   * @param value integer value
   */
  public ElementConstantInteger(int value) {
    super();
    this.value = value;
  }


  /**
   * Method which returns string representation of this element.
   * @return this element converted to string.
   */
  @Override
  public String asText() {
    return Integer.toString(value);
  }
}
