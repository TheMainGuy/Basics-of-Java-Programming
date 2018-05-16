package hr.fer.zemris.java.custom.collections;

/**
 * Class which defines an object stack and all the methods used for stack. Only
 * last element added on stack is visible and can be removed in order to gain
 * access to previous element added.
 * 
 * @author tin
 *
 */
public class ObjectStack {
  ArrayIndexedCollection stack = new ArrayIndexedCollection();

  /**
   * Method which returns true if size is 0 which means that the stack is empty.
   * Otherwise false.
   * 
   * @return true if stack is empty and false if it is not.
   */
  public boolean isEmpty() {
    return stack.isEmpty();
  }

  /**
   * Method which returns the number of elements on the stack.
   * 
   * @return number of elements on stack
   */
  public int size() {
    return stack.size();
  }

  /**
   * Method which adds object to top of the stack.
   * 
   * @param value object to be added on stack
   */
  public void push(Object value) {
    stack.add(value);
  }

  /**
   * Method which removes the last element added on stack and returns it.
   * 
   * @return last element on stack
   */
  public Object pop() {
    if (size() == 0) {
      throw new EmptyStackException();
    }
    Object poppedObject = stack.get(stack.size() - 1);
    stack.remove(stack.size() - 1);
    return poppedObject;
  }

  /**
   * Method which returns the last element on stack.
   * 
   * @return last element on stack
   */
  public Object peek() {
    if (size() == 0) {
      throw new EmptyStackException();
    }
    return stack.get(stack.size() - 1);
  }

  /**
   * Method which removes all elements from stack.
   */
  public void clear() {
    stack.clear();
  }

}
