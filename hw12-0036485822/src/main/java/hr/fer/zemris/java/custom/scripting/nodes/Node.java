package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Class which represents a single parser node.
 * 
 * @author tin
 *
 */
public abstract class Node {
  private ArrayIndexedCollection nodes;

  /**
   * Constructor.
   */
  public Node() {

  }

  /**
   * Adds child node to this node.
   * 
   * @param child child node
   * @throws NullPointerException if child node is null
   */
  public void addChildNode(Node child) {
    if(child == null) {
      throw new NullPointerException("Child can not be null.");
    }

    if(nodes == null) {
      nodes = new ArrayIndexedCollection();
    }

    nodes.add(child);
  }

  /**
   * Returns number of node's children.
   * 
   * @return number of node's children
   */
  public int numberOfChildren() {
    if(nodes == null) {
      return 0;
    }

    return nodes.size();
  }

  /**
   * Finds child on specified index.
   * 
   * @param index index of child
   * @return child node on index index
   * @throws IndexOutOfBoundsException if index < 0 or index >= number of children
   */
  public Node getChild(int index) {
    if(nodes == null || index < 0 || index >= numberOfChildren()) {
      throw new IndexOutOfBoundsException("Node has no children");
    }

    return (Node) nodes.get(index);
  }

  /**
   * Returns this node converted to string. Not implemented here.
   * 
   * @return node converted to string
   */
  public String getText() {
    return "";
  }
  
  public abstract void accept(INodeVisitor visitor);
}
