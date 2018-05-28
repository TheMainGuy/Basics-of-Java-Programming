package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class with represents variable tag element.
 * @author tin
 *
 */
public class ElementVariable extends Element {
  private String name;
  
  /**
   * Creates variable tag element.
   * @param name variable name
   */
  public ElementVariable(String name) {
    this.name = name;
  }
  
  /**
   * Method which returns name of this variable element.
   * @return name
   */
  @Override
  public String asText() {
    return name;
  }
}
