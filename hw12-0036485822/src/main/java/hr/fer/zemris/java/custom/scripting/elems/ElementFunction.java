package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class with represents function tag element.
 * @author tin
 *
 */
public class ElementFunction extends Element {
  private String name;
  
  /**
   * Creates function tag element.
   * @param value function name
   */
  public ElementFunction(String name) {
    super();
    this.name = name;
  }

  /**
   * Method which returns name of this function element.
   * @return name
   */
  @Override
  public String asText() {
    return "@" + name;
  }
}
