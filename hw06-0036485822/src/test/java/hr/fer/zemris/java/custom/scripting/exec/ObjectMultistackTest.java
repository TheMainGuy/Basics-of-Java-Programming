package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;

import org.junit.Assert;
import org.junit.Test;


public class ObjectMultistackTest {
  ObjectMultistack multistack = new ObjectMultistack();
 
  @Test
  public void pushPopAndPeekTest() {multistack.push("testici", new ValueWrapper(4));
    multistack.push("test", new ValueWrapper(5));
    multistack.push("test", new ValueWrapper(4));
    multistack.push("test2", new ValueWrapper(1));
    multistack.push("test5", new ValueWrapper(4.6));
    Assert.assertEquals(Integer.valueOf(4), (Integer) multistack.pop("test").getValue());
    Assert.assertEquals(Integer.valueOf(1), (Integer) multistack.peek("test2").getValue());
    Assert.assertEquals(Integer.valueOf(1), (Integer) multistack.pop("test2").getValue());
    Assert.assertEquals(Integer.valueOf(5), (Integer) multistack.pop("test").getValue());
    Assert.assertEquals(Double.valueOf(4.6), (Double) multistack.peek("test5").getValue());
    Assert.assertEquals(Double.valueOf(4.6), (Double) multistack.pop("test5").getValue());
  }
  
  @Test(expected = NullPointerException.class)
  public void pushNullKey() {
    multistack.push(null, new ValueWrapper(4));
  }
  
  @Test(expected = NullPointerException.class)
  public void pushNullValue() {
    multistack.push("test", null);
  }
  
  @Test(expected = EmptyStackException.class)
  public void popEmptyStack() {
    multistack.push("test", new ValueWrapper(5));
    multistack.pop("test");
    multistack.pop("test");
  }
  
  @Test(expected = NullPointerException.class)
  public void popNonexistingStack() {
    multistack.pop("test");
  }
  
  @Test(expected = EmptyStackException.class)
  public void peekEmptyStack() {
    multistack.push("test", new ValueWrapper(5));
    multistack.pop("test");
    multistack.peek("test");
  }
  
  @Test(expected = NullPointerException.class)
  public void peekNonexistingStack() {
    multistack.peek("test");
  }
  
  @Test
  public void isEmptyTest() {
    multistack.push("test", new ValueWrapper(5));
    Assert.assertFalse(multistack.isEmpty("test"));
    multistack.pop("test");
    Assert.assertTrue(multistack.isEmpty("test"));
  }
  
  @Test(expected = NullPointerException.class)
  public void isEmptyForNonexistentStack() {
    multistack.isEmpty("test");
  }
  
  
}
