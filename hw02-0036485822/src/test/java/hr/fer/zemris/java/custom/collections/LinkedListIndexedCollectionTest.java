package hr.fer.zemris.java.custom.collections;

import org.junit.Assert;
import org.junit.Test;

public class LinkedListIndexedCollectionTest {
  LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
  int x = 5;
  int y = 6;
  String s = "test";

  @Test
  public void addAndGet() {
    collection.add(x);
    collection.add(y);
    collection.add(s);
    Assert.assertEquals(x, collection.get(0));
    Assert.assertEquals(y, collection.get(1));
    Assert.assertEquals(s, collection.get(2));
  }

  @Test(expected = NullPointerException.class)
  public void addNull() {
    collection.add(null);
  }

  @Test
  public void insert() {
    collection.insert(x, 0);
    collection.insert(y, 0);
    collection.insert(s, 1);
    Assert.assertEquals(x, collection.get(2));
    Assert.assertEquals(y, collection.get(0));
    Assert.assertEquals(s, collection.get(1));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void insertIndexOutOfBounds() {
    collection.insert(x, -1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void insertIndexOutOfBounds2() {
    collection.insert(x, 0);
    collection.insert(y, 2);
  }

  @Test(expected = NullPointerException.class)
  public void insertNull() {
    collection.insert(null, 0);
  }

  @Test
  public void indexOf() {
    collection.add(x);
    collection.add(y);
    Assert.assertEquals(0, collection.indexOf(x));
    Assert.assertEquals(1, collection.indexOf(y));
    Assert.assertEquals(-1, collection.indexOf(s));
  }

  @Test
  public void IndexOfNull() {
    collection.add(x);
    collection.add(y);
    Assert.assertEquals(-1, collection.indexOf(null));
  }

  @Test
  public void IndexOfForEmptyCollection() {
    Assert.assertEquals(-1, collection.indexOf(x));
    Assert.assertEquals(-1, collection.indexOf(null));
  }

  @Test
  public void remove() {
    collection.add(x);
    collection.add(y);
    collection.add(s);
    collection.remove(1);
    Assert.assertEquals(x, collection.get(0));
    Assert.assertEquals(s, collection.get(1));
    Assert.assertEquals(-1, collection.indexOf(y));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void removeIndexOutOfBounds() {
    collection.remove(0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void removeIndexOutOfBounds2() {
    collection.add(x);
    collection.add(y);
    collection.remove(2);
  }

  @Test
  public void size() {
    Assert.assertEquals(0, collection.size());
    collection.add(x);
    collection.add(y);
    Assert.assertEquals(2, collection.size());
    collection.insert(y, 1);
    collection.add(s);
    Assert.assertEquals(4, collection.size());
  }

  @Test
  public void contains() {
    collection.add(x);
    collection.add(y);
    Assert.assertEquals(true, collection.contains(x));
    Assert.assertEquals(true, collection.contains(y));
    Assert.assertEquals(false, collection.contains(s));
  }

  @Test
  public void containsForEmpty() {
    Assert.assertEquals(false, collection.contains(x));
  }

  @Test
  public void containsNull() {
    Assert.assertEquals(false, collection.contains(null));
    collection.add(x);
    collection.add(y);
    Assert.assertEquals(false, collection.contains(null));
  }

  @Test
  public void toArray() {
    collection.add(x);
    collection.add(y);
    collection.add(s);
    Object[] elements = { x, y, s };
    Assert.assertArrayEquals(elements, collection.toArray());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void toArrayEmpty() {
    collection.toArray();
  }

}
