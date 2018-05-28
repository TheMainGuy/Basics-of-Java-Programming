package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Class which represents parser for loop node.
 * 
 * @author tin
 *
 */
public class ForLoopNode extends Node {
  private ElementVariable variable;
  private Element startExpression;
  private Element endExpression;
  private Element stepExpression;

  /**
   * Constructor.
   * 
   * @param variable for loop variable
   * @param startExpression for loop start expression
   * @param endExpression for loop end expression
   * @param stepExpression for loop step expression
   * @throws NullPointerException if either variable, startExpression or
   *           endExpression is null
   */
  public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression, Element stepExpression) {
    super();
    if(variable == null || startExpression == null || endExpression == null) {
      throw new NullPointerException("Constructor must get 3 non-null arguments.");
    }
    this.variable = variable;
    this.startExpression = startExpression;
    this.endExpression = endExpression;
    this.stepExpression = stepExpression;
  }

  /**
   * Returns for loop variable
   * 
   * @return variable
   */
  public ElementVariable getVariable() {
    return variable;
  }

  /**
   * Returns for loop start expression
   * 
   * @return startExpression
   */
  public Element getStartExpression() {
    return startExpression;
  }

  /**
   * Returns for loop end expression
   * 
   * @return endExpression
   */
  public Element getEndExpression() {
    return endExpression;
  }

  /**
   * Returns for loop step expression
   * 
   * @return stepExpression
   */
  public Element getStepExpression() {
    return stepExpression;
  }

  /**
   * /** Returns for loop node converted to string in form: {$for elements
   * separated by spaces$}
   * 
   * @return for loop node converted to string
   */
  @Override
  public String getText() {
    String s = "{$for " + variable.asText() + " " + startExpression.asText() + " " + endExpression.asText();
    if(stepExpression != null) {
      s += " " + getStepExpression().asText();
    }
    s += "$}";
    return s;
  }
  
  /**
   * Calls visitor's visitForLoopNode method using this object as method parameter.
   * 
   * @param visitor {@link INodeVisitor} object whose visitForLoopNode method will be called
   */
  public void accept(INodeVisitor visitor) {
    visitor.visitForLoopNode(this);
  }
}
