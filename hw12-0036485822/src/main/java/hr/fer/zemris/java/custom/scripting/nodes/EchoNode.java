package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Class which represents parser echo node.
 * 
 * @author tin
 *
 */
public class EchoNode extends Node {
  private Element[] elements;

  /**
   * Constructor.
   * 
   * @param elements array of elements
   */
  public EchoNode(Element[] elements) {
    super();
    this.elements = elements;
  }

  /**
   * Returns array of elements
   * 
   * @return elements
   */
  public Element[] getElements() {
    return elements;
  }

  /**
   * Returns echo node converted to string in form: {$elements separated by
   * spaces$}
   * 
   * @return echo node converted to string
   */
  @Override
  public String getText() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("{$");
    for (int i = 0; i < elements.length; i++) {
      if(i > 0) {
        stringBuilder.append(" ");
      }
      stringBuilder.append(elements[i].asText());
    }
    stringBuilder.append("$}");

    return stringBuilder.toString();
  }
  
  /**
   * Calls visitor's visitEchoNode method using this object as method parameter.
   * 
   * @param visitor {@link INodeVisitor} object whose visitEchoNode method will be called
   */
  public void accept(INodeVisitor visitor) {
    visitor.visitEchoNode(this);
  }
}
