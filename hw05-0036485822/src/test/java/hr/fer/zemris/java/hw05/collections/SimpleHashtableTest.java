package hr.fer.zemris.java.hw05.collections;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

public class SimpleHashtableTest {
  SimpleHashtable<String, Integer> simpleHashtable = new SimpleHashtable<>(4);
  
  @Test
  public void putAndGet() {
    simpleHashtable.put("test", 7);
    simpleHashtable.put("test2", 4);
    Assert.assertEquals(4, simpleHashtable.get("test2").intValue());
    Assert.assertEquals(7, simpleHashtable.get("test").intValue());
    simpleHashtable.put("test", 8);
    Assert.assertEquals(8, simpleHashtable.get("test").intValue());
  }
  
  @Test
  public void putOverCapacity() {
    simpleHashtable.put("test", 7);
    simpleHashtable.put("test2", 4);
    simpleHashtable.put("test3", -1);
    simpleHashtable.put("test4", 0);
    simpleHashtable.put("test5", 10);
    Assert.assertEquals(7, simpleHashtable.get("test").intValue());
    Assert.assertEquals(4, simpleHashtable.get("test2").intValue());
    Assert.assertEquals(-1, simpleHashtable.get("test3").intValue());
    Assert.assertEquals(0, simpleHashtable.get("test4").intValue());
    Assert.assertEquals(10, simpleHashtable.get("test5").intValue());
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void putNullKey() {
    simpleHashtable.put(null, 2);
  }
  
  @Test
  public void putNullValue() {
    simpleHashtable.put("test", null);
    Assert.assertNull(simpleHashtable.get("test"));
  }
  
  @Test
  public void getNullKey() {
    simpleHashtable.put("test", 2);
    Assert.assertNull(simpleHashtable.get(null));
  }
  
  @Test
  public void containsKeyAndContainsValue() {
    simpleHashtable.put("test", 7);
    simpleHashtable.put("test2", 4);
    simpleHashtable.put("test3", -1);
    simpleHashtable.put("test4", 0);
    simpleHashtable.put("test5", 10);
    Assert.assertTrue(simpleHashtable.containsKey("test"));
    Assert.assertTrue(simpleHashtable.containsKey("test2"));
    Assert.assertTrue(simpleHashtable.containsKey("test3"));
    Assert.assertTrue(simpleHashtable.containsKey("test4"));
    Assert.assertTrue(simpleHashtable.containsKey("test5"));
    Assert.assertFalse(simpleHashtable.containsKey("test6"));

    Assert.assertTrue(simpleHashtable.containsValue(7));
    Assert.assertTrue(simpleHashtable.containsValue(4));
    Assert.assertTrue(simpleHashtable.containsValue(-1));
    Assert.assertTrue(simpleHashtable.containsValue(0));
    Assert.assertTrue(simpleHashtable.containsValue(10));
    Assert.assertFalse(simpleHashtable.containsValue(5));
  }
  
  @Test
  public void containsNull() {
    simpleHashtable.put("test", 7);
    Assert.assertFalse(simpleHashtable.containsValue(null));
    simpleHashtable.put("test2", null);
    Assert.assertTrue(simpleHashtable.containsValue(null));
    Assert.assertFalse(simpleHashtable.containsKey(null));
  }
  
  @Test
  public void removeTest() {
    Assert.assertEquals(0, simpleHashtable.size());
    simpleHashtable.put("test", 7);
    simpleHashtable.put("test2", 4);
    simpleHashtable.put("test3", -1); 
    Assert.assertTrue(simpleHashtable.containsKey("test"));
    Assert.assertTrue(simpleHashtable.containsKey("test2"));
    Assert.assertTrue(simpleHashtable.containsKey("test3"));
    Assert.assertEquals(3, simpleHashtable.size());
    simpleHashtable.remove("test2");
    Assert.assertTrue(simpleHashtable.containsKey("test"));
    Assert.assertFalse(simpleHashtable.containsKey("test2"));
    Assert.assertTrue(simpleHashtable.containsKey("test3"));
    Assert.assertEquals(2, simpleHashtable.size());
  }
  
  @Test
  public void isEmptyTest() {
    Assert.assertTrue(simpleHashtable.isEmpty());
    simpleHashtable.put("test", 7);
    Assert.assertFalse(simpleHashtable.isEmpty());
    simpleHashtable.remove("test");
    Assert.assertTrue(simpleHashtable.isEmpty());
  }
  
  @Test
  public void toStringWithCapacityExpansion() {
    simpleHashtable.put("test", 7);
    simpleHashtable.put("test2", 4);
    simpleHashtable.put("test3", -1);
    System.out.println(simpleHashtable.toString());
    simpleHashtable.put("test4", 0);
    simpleHashtable.put("test5", 10);
    simpleHashtable.put("test6", 0);
    simpleHashtable.put("test7", 10);
    simpleHashtable.put("test8", 0);
    simpleHashtable.put("test9", 10);
    simpleHashtable.put("test10", 0);
    simpleHashtable.put("test11", 10);
    System.out.println(simpleHashtable.toString());
  }
  
  @Test
  public void clearTest() {
    Assert.assertTrue(simpleHashtable.isEmpty());
    simpleHashtable.put("test", 7);
    Assert.assertFalse(simpleHashtable.isEmpty());
    simpleHashtable.clear();
    Assert.assertTrue(simpleHashtable.isEmpty());
    simpleHashtable.put("test", 7);
    simpleHashtable.put("test2", 4);
    simpleHashtable.put("test3", -1);
    simpleHashtable.put("test4", 0);
    simpleHashtable.put("test5", 10);
    simpleHashtable.put("test6", 0);
    simpleHashtable.put("test7", 10);
    simpleHashtable.put("test8", 0);
    simpleHashtable.put("test9", 10);
    simpleHashtable.put("test10", 0);
    simpleHashtable.put("test11", 10);
    simpleHashtable.clear();
    Assert.assertTrue(simpleHashtable.isEmpty());
  }
  
  @Test
  public void removeAdvanced() {
    SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
    // fill data:
    examMarks.put("Ivana", 2);
    examMarks.put("Ante", 2);
    examMarks.put("Jasna", 2);
    examMarks.put("Kristina", 5);
    examMarks.put("Ivana", 5); // overwrites old grade for Ivana
    
    Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
    while (iter.hasNext()) {
      SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
      System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
      iter.remove();
    }
    System.out.printf("Veličina: %d%n", examMarks.size());
  }
  
}
