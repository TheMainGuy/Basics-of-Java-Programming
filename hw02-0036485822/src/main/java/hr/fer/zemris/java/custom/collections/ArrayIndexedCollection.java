package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

/**
 * Class which defines a collection implemented using indexed array. Defines
 * methods for operating a collection.
 * 
 * @author tin
 *
 */
public class ArrayIndexedCollection extends Collection {
  private static final int DEFAULT_CAPACITY = 16;
  private int size;
  private int capacity;
  private Object[] elements;

  /**
   * Default constructor, initializes array of capacity 16.
   *
   */
  public ArrayIndexedCollection() {
    this(DEFAULT_CAPACITY);
  }

  /**
   * Constructor with defined capacity.
   * 
   * @param initialCapacity initial capacity of the collection
   * @throws IllegalArgumentException if initial capacity < 1
   */
  public ArrayIndexedCollection(int initialCapacity) {
    if (initialCapacity < 1) {
      throw new IllegalArgumentException();
    }
    capacity = initialCapacity;
    elements = new Object[capacity];
    size = 0;
  }

  /**
   * Constructor which copies other collection.
   * 
   * @param other collection which is copied
   */
  public ArrayIndexedCollection(Collection other) {
    this(other, DEFAULT_CAPACITY);
  }

  /**
   * Constructor which copies other collection but defines certain capacity. If
   * the capacity of other collection is greater than initial capacity, the
   * initial capacity is set to the other collection's size.
   * 
   * @param other collection which is copied
   * @param initialCapacity initial capacity of the collection
   * @throws NullPointerException if other collection is null
   */
  public ArrayIndexedCollection(Collection other, int initialCapacity) {
    if (other == null) {
      throw new NullPointerException();
    }

    if (initialCapacity < other.size()) {
      initialCapacity = other.size();
    }

    capacity = initialCapacity;
    elements = new Object[capacity];
    size = 0;
    addAll(other);
  }

  /**
   * Method which adds the given object into the collection.
   * 
   * @param value object to be added to collection
   * @throws NullPointerException if value is null
   */
  @Override
  public void add(Object value) {
    if (value == null) {
      throw new NullPointerException();
    }

    if (size == capacity) {
      capacity *= 2;
      elements = Arrays.copyOf(elements, capacity);
    }
    elements[size] = value;
    size++;
  }

  /**
   * Method which returns the object that is located at given index in collection
   * .
   * 
   * @param index index from which the object is taken
   * @return object at given index
   * @throws IndexOutOfBoundsException if index < 0 or index >= size
   */
  public Object get(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }
    return elements[index];
  }

  /**
   * Method which clears the whole array.
   */
  @Override
  public void clear() {
    for (int i = 0; i < size; i++) {
      elements[i] = null;
    }
    size = 0;
  }

  /**
   * Method which inserts the given object at given position in collection.
   * Objects stored at and after that position are shifted to their old index + 1.
   * 
   * @param value object to be inserted
   * @param position position to which the object will be inserted
   * @throws NullPointerException if value is null
   * @throws IndexOutOfBoundsException if position < 0 or position > size
   */
  public void insert(Object value, int position) {
    if (value == null) {
      throw new NullPointerException();
    }

    if (position < 0 || position > size) {
      throw new IndexOutOfBoundsException();
    }

    if (size == capacity) {
      capacity *= 2;
      elements = Arrays.copyOf(elements, capacity);
    }

    for (int i = size; i > position; i--) {
      elements[i] = elements[i - 1];
    }

    elements[position] = value;
    size++;
  }

  /**
   * Method which finds the first occurence of the given object.
   * 
   * @param value object for which the method will search for
   * @return index at first occurence of the given object or -1 if the value is
   *         not found or is null
   */
  public int indexOf(Object value) {
    if (value == null) {
      return -1;
    }

    for (int i = 0; i < size; i++) {
      if (elements[i].equals(value)) {
        return i;
      }
    }

    return -1;
  }
  
  /**
   * Method which removes first occurence of the given object from the collection.
   * 
   * @param value object to be removed from collection
   * @return true if the object is found and removed, false otherwise
   */
  @Override
  public boolean remove(Object value) {
    int index = indexOf(value);
    if(index == -1) {
      return false;
    }
    else {
      remove(index);
      return true;
    }
  }

  /**
   * Method which removes object at given index from the collection.
   * Objects after it have their index reduced by 1.
   * 
   * @param index index of an object to be removed from collection
   * @throws IndexOutOfBoundsException if index < 0 or index >= size
   */
  public void remove(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }

    for (int i = index; i < size - 1; i++) {
      elements[i] = elements[i + 1];
    }
    elements[size - 1] = null;
    size--;
  }

  /**
   * Method which returns the number of elements in the collection.
   * 
   * @return number of elements in collection
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Method which checks if the collection contains the given object.
   * 
   * @param value object to be checked
   * @return true if object exsists in collection and false if it does not or it
   *         is null
   */
  @Override
  public boolean contains(Object value) {
    if (value == null || size == 0) {
      return false;
    }

    for (int i = 0; i < size; i++) {
      if (elements[i].equals(value)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Method which converts the collection to Object array.
   * 
   * @return object array filled with collection elements
   * @throws UnsupportedOperationException if there are no elements in the
   *           collection
   */
  @Override
  public Object[] toArray() {
    if (size == 0) {
      throw new UnsupportedOperationException();
    }

    Object[] array = new Object[size];
    for (int i = 0; i < size; i++) {
      array[i] = elements[i];
    }
    return array;
  }

  /**
   * Method calls processor.process(.) for each element of this collection.
   * 
   * @param processor processor in which the method process will be called
   */
  @Override
  public void forEach(Processor processor) {
    for (int i = 0; i < size; i++) {
      processor.process(elements[i]);
    }
  }

}
