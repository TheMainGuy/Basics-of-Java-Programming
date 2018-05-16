package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

/**
 * Implements multistack map by mapping each key to a stack of values. Each
 * stack has O(1) complexity for push pop and peek operation.
 * 
 * @author tin
 *
 */
public class ObjectMultistack {

  /**
   * Map of names and stacks.
   */
  SimpleHashtable<String, MultistackEntry> table = new SimpleHashtable<>();

  /**
   * Class defines 1 map entry that stores value and reference to next element in
   * stack.
   * 
   * @author tin
   *
   */
  private static class MultistackEntry {
    /**
     * Reference to next entry.
     */
    private MultistackEntry next;

    /**
     * Stored value.
     */
    private ValueWrapper value;

    /**
     * Creates 1 entry with given value.
     * 
     * @param value value
     */
    public MultistackEntry(ValueWrapper value, MultistackEntry next) {
      this.value = value;
      this.next = next;
    }

  }

  /**
   * Method adds new {@link ValueWrapper} object to stack in map or creates new
   * stack if does not exist already. Throws exception if either key or value is
   * <code>null</code>.
   * 
   * @param name map key
   * @param valueWrapper value object
   * @throws NullPointerException if name or value is <code>null</code>
   */
  public void push(String name, ValueWrapper valueWrapper) {
    if(name == null) {
      throw new NullPointerException("Name can not be null.");
    }
    if(valueWrapper == null) {
      throw new NullPointerException("Value can not be null.");
    }
    if(table.containsKey(name)) {
      table.put(name, new MultistackEntry(valueWrapper, table.get(name)));
    }
    else {
      table.put(name, new MultistackEntry(valueWrapper, null));
    }
  }

  /**
   * Pops and returns 1 object from stack mapped to given key. Throws exception if
   * stack does not exist or is empty.
   * 
   * @param name map key
   * @return object popped
   * @throws NullPointerException if there is no stack mapped to given key
   * @throws EmptyStackException if stack is empty
   */
  public ValueWrapper pop(String name) {
    if(!table.containsKey(name)) {
      throw new NullPointerException("There is no stack mapped to given key.");
    }
    MultistackEntry entry = table.get(name);
    if(entry == null) {
      throw new EmptyStackException();
    }
    if(entry.next != null) {
      table.put(name, entry.next);
    }
    else {
      table.put(name, null);
    }

    return entry.value;
  }

  /**
   * Returns 1 object from stack mapped to given key. Throws exception if stack
   * does not exist or is empty.
   * 
   * @param name map key
   * @return object peeked
   * @throws NullPointerException if there is no stack mapped to given key
   * @throws EmptyStackException if stack is empty
   */
  public ValueWrapper peek(String name) {
    if(!table.containsKey(name)) {
      throw new NullPointerException("There is no stack mapped to given key.");
    }
    MultistackEntry entry = table.get(name);
    if(entry == null) {
      throw new EmptyStackException();
    }
    return entry.value;
  }

  /**
   * Checks if stack is empty and returns <code>true</code> if it is. If it is
   * not, returns <code>false</code>.
   * 
   * @param name map key
   * @return <code>true</code> if stack is empty, <code>false</code> if it is not
   */
  public boolean isEmpty(String name) {
    if(!table.containsKey(name)) {
      throw new NullPointerException("There is not stack for given key.");
    }
    if(table.get(name) == null) {
      return true;
    }
    return false;
  }
}
