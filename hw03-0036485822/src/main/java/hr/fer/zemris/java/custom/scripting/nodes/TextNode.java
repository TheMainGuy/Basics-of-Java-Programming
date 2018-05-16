package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Class which represents parser text node.
 * 
 * @author tin
 *
 */
public class TextNode extends Node {
  private String text;

  /**
   * Constructor.
   * 
   * @param text text
   * @throws NullPointerException if text is null
   */
  public TextNode(String text) {
    super();
    if(text == null) {
      throw new NullPointerException("Text can not be null.");
    }
    this.text = text;
  }

  /**
   * Returns text.
   * 
   * @return text
   */
  public String getText() {
    return text;
  }
  
  
}
