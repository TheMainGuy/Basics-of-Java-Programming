package hr.fer.zemris.java.custom.collections;

/**
 * Class which defines a collection implemented with linked list. Defines
 * methods for operating a collection.
 * 
 * @author tin
 *
 */
public class LinkedListIndexedCollection extends Collection {
  private static class ListNode {
    ListNode previous;
    ListNode next;
    Object value;
  }

  private int size;
  private ListNode first;
  private ListNode last;

  /**
   * Default constructor for initializing a collection.
   */
  public LinkedListIndexedCollection() {
    first = null;
    last = first;
    size = 0;
  }

  /**
   * Constructor which copies other collection.
   * 
   * @param other collection which is copied
   */
  public LinkedListIndexedCollection(Collection other) {
    this();
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

    if (size == 0) {
      first = new ListNode();
      first.value = value;
      last = first;
    } else {
      last.next = new ListNode();
      last.next.value = value;
      last.next.previous = last;
      last = last.next;
    }
    size++;
  }

  /**
   * Method which returns the object that is located at given index in collection.
   * 
   * @param index index from which the object is taken
   * @return object at given index
   * @throws IndexOutOfBoundsException if index < 0 or index >= size
   */
  public Object get(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }

    ListNode iterator;
    if (index >= size / 2 + 1) {
      iterator = last;
      for (int i = size - 1; i > index; i--) {
        iterator = iterator.previous;
      }
    } else {
      iterator = first;
      for (int i = 0; i < index; i++) {
        iterator = iterator.next;
      }
    }
    return iterator.value;
  }

  /**
   * Method which clears the whole array.
   */
  @Override
  public void clear() {
    first = null;
    last = null;
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

    if (position == size) {
      add(value);
      return;
    }

    if (position == 0) {
      first.previous = new ListNode();
      first.previous.value = value;
      first.previous.next = first;
      first = first.previous;

      size++;
      return;
    }

    ListNode iterator = first;
    for (int i = 0; i < position - 1; i++) {
      iterator = iterator.next;
    }
    ListNode insertedNode = new ListNode();
    insertedNode.previous = iterator;
    insertedNode.value = value;
    insertedNode.next = iterator.next;
    iterator.next.previous = insertedNode;
    iterator.next = insertedNode;

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

    ListNode iterator = first;
    for (int i = 0; i < size; i++) {
      if (iterator.value.equals(value)) {
        return i;
      }
      iterator = iterator.next;
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
   * Method which removes object at given index from the collection. Objects after
   * it have their index reduced by 1.
   * 
   * @param index index of an object to be removed from collection
   * @throws IndexOutOfBoundsException if index < 0 or index >= size
   */
  public void remove(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }

    ListNode iterator = first;
    for (int i = 0; i < index; i++) {
      iterator = iterator.next;
    }
    iterator.previous.next = iterator.next;
    iterator.next.previous = iterator.previous;

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
    if (value == null) {
      return false;
    }

    ListNode iterator = first;
    for (int i = 0; i < size; i++) {
      if (iterator.value.equals(value)) {
        return true;
      }
      iterator = iterator.next;
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
    Object[] elements = new Object[size];
    ListNode iterator = first;
    for (int i = 0; i < size; i++) {
      elements[i] = iterator.value;
      iterator = iterator.next;
    }
    return elements;
  }

  /**
   * Method calls processor.process(.) for each element of this collection.
   * 
   * @param processor processor in which the method process will be called
   */
  @Override
  public void forEach(Processor processor) {
    ListNode iterator = first;
    for (int i = 0; i < size; i++) {
      processor.process(iterator.value);
      iterator = iterator.next;
    }
  }

}
