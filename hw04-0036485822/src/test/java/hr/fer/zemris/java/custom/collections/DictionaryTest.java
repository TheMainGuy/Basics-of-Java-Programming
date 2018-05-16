package hr.fer.zemris.java.custom.collections;

import org.junit.Assert;
import org.junit.Test;

public class DictionaryTest {
  @Test
  public void putAndGet() {
    Dictionary dictionary = new Dictionary();
    dictionary.put("key", "value");
    Assert.assertEquals("value", dictionary.get("key"));
  }

  @Test
  public void putAndGetMore() {
    Dictionary dictionary = new Dictionary();
    dictionary.put("key", "value");
    dictionary.put("0", "1");
    dictionary.put("0", "1");
    Assert.assertEquals("1", dictionary.get("0"));
    Assert.assertEquals("1", dictionary.get("0"));
    dictionary.put("0", "2");
    Assert.assertEquals("2", dictionary.get("0"));
    Assert.assertEquals("value", dictionary.get("key"));
    Assert.assertNull(dictionary.get("nista"));
  }

  @Test(expected = NullPointerException.class)
  public void putNull() {
    Dictionary dictionary = new Dictionary();
    dictionary.put(null, "fjj");
  }
  
  @Test(expected = NullPointerException.class)
  public void getNull() {
    Dictionary dictionary = new Dictionary();
    dictionary.put("0", "0");
    dictionary.get(null);
  }

  @Test
  public void isEmptyTest() {
    Dictionary dictionary = new Dictionary();
    Assert.assertTrue(dictionary.isEmpty());
    dictionary.put("0", "1");
    Assert.assertFalse(dictionary.isEmpty());
  }

  @Test
  public void size() {
    Dictionary dictionary = new Dictionary();
    Assert.assertEquals(0, dictionary.size());
    dictionary.put("0", "1");
    dictionary.put("key", "value");
    Assert.assertEquals(2, dictionary.size());
    dictionary.put("0", "1");
    dictionary.put("0", "1");
    Assert.assertEquals(2, dictionary.size());
  }

  @Test
  public void clear() {
    Dictionary dictionary = new Dictionary();
    Assert.assertEquals(0, dictionary.size());
    dictionary.put("0", "1");
    dictionary.put("key", "value");
    Assert.assertEquals(2, dictionary.size());
    dictionary.clear();
    Assert.assertEquals(0, dictionary.size());
  }
}
