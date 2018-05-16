package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class with represents string tag element.
 * @author tin
 *
 */
public class ElementString extends Element {
  private String value;
  
  /**
   * Creates string tag element.
   * @param value function name
   */
  public ElementString(String value) {
    this.value = value;
  }
  
  /**
   * Method which returns string element.
   * @return string value
   */
  @Override
  public String asText() {
    return value;
  }
}
