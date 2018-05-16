package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class which implements {@link TurtleState} stack. It allows pushing, popping
 * and returning top stack state.
 * 
 * @author tin
 *
 */
public class Context {
  private ObjectStack stack;

  /**
   * Creates stack.
   */
  public Context() {
    stack = new ObjectStack();
  }

  /**
   * Returns state on top of the stack.
   * 
   * @return turtle state on top of the stack
   */
  public TurtleState getCurrentState() {
    return (TurtleState) stack.peek();
  }

  /**
   * Pushes state on top of the stack.
   * 
   * @param state state
   * @throws NullPointerException if state is null
   */
  public void pushState(TurtleState state) {
    if(state == null) {
      throw new NullPointerException("State can not be null.");
    }
    stack.push(state);
  }

  /**
   * Removes one state from top of the stack.
   */
  public void popState() {
    stack.pop();
  }
}
