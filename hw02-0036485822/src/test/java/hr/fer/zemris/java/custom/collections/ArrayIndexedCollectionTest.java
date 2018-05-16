package hr.fer.zemris.java.custom.collections;

import org.junit.Assert;
import org.junit.Test;

public class ArrayIndexedCollectionTest {

  ArrayIndexedCollection collection = new ArrayIndexedCollection(3);
  int x = 5;
  int y = 6;
  String s = "test";

  @Test
  public void size() {
    Assert.assertEquals(0, collection.size());
    collection.add(x);
    collection.add(y);
    Assert.assertEquals(2, collection.size());
    collection.add(s);
    Assert.assertEquals(3, collection.size());
  }

  @Test(expected = NullPointerException.class)
  public void addNullObject() {
    collection.add(null);
  }

  @Test
  public void addAndGet() {
    collection.add(x);
    collection.add(y);
    collection.add(s);
    Assert.assertEquals(5, collection.get(0));
    Assert.assertEquals(6, collection.get(1));
    Assert.assertEquals("test", collection.get(2));
  }

  @Test
  public void addOverCapacity() {
    collection.add(x);
    collection.add(y);
    collection.add(s);
    collection.add(x);
    Assert.assertEquals(5, collection.get(3));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void getIndexOutOfBounds() {
    collection.get(-1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void getIndexOutOfBounds2() {
    collection.get(10);
  }

  @Test
  public void addAll() {
    collection.add(x);
    collection.add(y);
    collection.add(s);
    ArrayIndexedCollection collection2 = new ArrayIndexedCollection(5);
    Assert.assertEquals(0, collection2.size());
    collection2.addAll(collection);
    Assert.assertEquals(3, collection.size());
    Assert.assertEquals(3, collection2.size());
    Assert.assertEquals(x, collection2.get(0));
  }

  @Test
  public void constructors() {
    collection.add(x);
    collection.add(y);
    collection.add(s);
    ArrayIndexedCollection collection2 = new ArrayIndexedCollection(collection);
    Assert.assertEquals(5, collection2.get(0));
    Assert.assertEquals(3, collection2.size());
  }

  @Test
  public void clear() {
    collection.add(x);
    collection.add(y);
    collection.add(s);
    collection.clear();
    Assert.assertEquals(0, collection.size());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void insertOutOfBounds() {
    collection.insert(x, -1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void insertOutOfBounds2() {
    collection.insert(x, 5);
  }

  @Test
  public void insertValue() {
    collection.add(x);
    collection.add(y);
    collection.insert(x, 0);
    collection.insert(x, 1);
    collection.insert(y, 1);
    Assert.assertEquals(5, collection.get(0));
  }

  @Test
  public void indexOfNullObject() {
    Assert.assertEquals(-1, collection.indexOf(null));
    collection.add(x);
    Assert.assertEquals(-1, collection.indexOf(null));
  }

  @Test
  public void indexOf() {
    collection.add(x);
    Assert.assertEquals(0, collection.indexOf(x));
    collection.add(y);
    Assert.assertEquals(1, collection.indexOf(y));
  }

  @Test
  public void indexOfElementNotInCollection() {
    collection.add(x);
    Assert.assertEquals(-1, collection.indexOf(y));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void removeFromEmptyCollection() {
    collection.remove(0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void removeIndexOutOfBounds() {
    collection.add(x);
    collection.add(y);
    collection.remove(2);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void removeIndexOutOfBounds2() {
    collection.add(x);
    collection.add(y);
    collection.remove(-1);
  }

  @Test
  public void containsNull() {
    collection.add(x);
    Assert.assertEquals(false, collection.contains(null));
  }

  @Test
  public void containsForEmptyCollection() {
    Assert.assertEquals(false, collection.contains(x));
  }

  @Test
  public void contains() {
    collection.add(x);
    collection.add(y);
    Assert.assertEquals(true, collection.contains(x));
    Assert.assertEquals(true, collection.contains(y));
    Assert.assertEquals(false, collection.contains(s));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void toArrayForEmptyCollection() {
    collection.toArray();
  }

  @Test
  public void toArray() {
    collection.add(x);
    collection.add(y);
    collection.add(s);
    Object[] elements = { x, y, s };
    Assert.assertArrayEquals(elements, collection.toArray());
  }

}
