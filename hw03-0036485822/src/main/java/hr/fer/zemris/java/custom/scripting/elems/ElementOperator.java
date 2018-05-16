package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class which represents tag operator.
 * 
 * @author tin
 *
 */
public class ElementOperator extends Element {
  private String symbol;

  /**
   * Creates operator element.
   * @param symbol symbol
   */
  public ElementOperator(String symbol) {
    super();
    this.symbol = symbol;
  }

  /**
   * Method which returns symbol.
   * @return symbol
   */
  @Override
  public String asText() {
    return symbol;
  }
}
