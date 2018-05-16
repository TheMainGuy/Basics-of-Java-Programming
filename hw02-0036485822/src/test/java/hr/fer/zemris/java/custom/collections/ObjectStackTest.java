package hr.fer.zemris.java.custom.collections;

import org.junit.Assert;
import org.junit.Test;

public class ObjectStackTest {
  ObjectStack stack = new ObjectStack();

  @Test
  public void pushAndPop() {
    stack.push(5);
    stack.push(3);
    Assert.assertEquals(3, stack.pop());
    Assert.assertEquals(5, stack.pop());
  }

  @Test(expected = NullPointerException.class)
  public void pushNull() {
    stack.push(null);
  }

  @Test
  public void isEmpty() {
    Assert.assertEquals(true, stack.isEmpty());
    stack.push(4);
    Assert.assertEquals(false, stack.isEmpty());
    stack.pop();
    Assert.assertEquals(true, stack.isEmpty());
  }

  @Test(expected = EmptyStackException.class)
  public void popEmpty() {
    stack.pop();
  }

  @Test(expected = EmptyStackException.class)
  public void peekEmpty() {
    stack.peek();
  }

  @Test
  public void peek() {
    stack.push(3);
    stack.push(5);
    Assert.assertEquals(5, stack.peek());
    Assert.assertEquals(5, stack.peek());
    Assert.assertEquals(5, stack.peek());
    Assert.assertEquals(5, stack.pop());
    Assert.assertEquals(3, stack.peek());
  }

  @Test
  public void size() {
    Assert.assertEquals(0, stack.size());
    stack.push(5);
    Assert.assertEquals(1, stack.size());
    stack.push(5);
    stack.push(1);
    stack.push(4);
    Assert.assertEquals(4, stack.size());
  }

  @Test
  public void clear() {
    stack.push(4);
    stack.push(5);
    stack.push(1);
    Assert.assertEquals(3, stack.size());
    stack.clear();
    Assert.assertEquals(0, stack.size());
  }

}
