package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Class which represents parser root node.
 * 
 * @author tin
 *
 */
public class DocumentNode extends Node {
  private String name;

  /**
   * Constructor.
   */
  public DocumentNode() {

  }

  /**
   * Returns document name.
   * 
   * @return name
   */
  public String getName() {
    return name;
  }
  
  /**
   * Calls visitor's visitDocumentNode method using this object as method parameter.
   * 
   * @param visitor {@link INodeVisitor} object whose visitDocumentNode method will be called
   */
  public void accept(INodeVisitor visitor) {
    visitor.visitDocumentNode(this);
  }

}
