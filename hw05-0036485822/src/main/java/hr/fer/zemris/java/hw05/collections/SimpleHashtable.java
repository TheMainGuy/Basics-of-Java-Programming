package hr.fer.zemris.java.hw05.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class implements simple hashtable that stores key value pairs
 * {@link TableEntry}. Keys are unique and hash is calculated from key. Table is
 * divided in lists and hash function distributes entries to lists.
 * 
 * @author tin
 *
 * @param <K> key type
 * @param <V> value type
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

  // Array of list heads.
  private TableEntry<K, V>[] table;

  // Number of table entries in table
  private int size = 0;

  // Counts the number of modifications in table. Used by iterator to check if
  // there are modifications from outside
  private int modificationCount;

  /**
   * Helper class which represents table entry for {@link SimpleHashtable}. Once
   * set, key can not be modified. Single {@link TableEntry} has link to next
   * {@link TableEntry} element in its list.
   * 
   * @author tin
   *
   * @param <K> key type
   * @param <V> value type
   */
  public static class TableEntry<K, V> {
    // Key parameter
    private K key;

    // Value parameter
    private V value;

    // TableEntry pointing to next item in list
    private TableEntry<K, V> next;

    /**
     * Constructor which creates table entry. If next is not null, this
     * {@link TableEntry} will point to it.
     * 
     * @param key key
     * @param value value
     * @param next next {@link TabableEntry}
     */
    public TableEntry(K key, V value, TableEntry<K, V> next) {
      super();
      this.key = key;
      this.value = value;
      this.next = next;
    }

    /**
     * Returns {@link TableEntry} value.
     * 
     * @return value
     */
    public V getValue() {
      return value;
    }

    /**
     * Sets {@link TableEntry} value.
     * 
     * @param value to be set
     */
    public void setValue(V value) {
      this.value = value;
    }

    /**
     * Returns {@link TableEntry} key.
     * 
     * @return key
     */
    public K getKey() {
      return key;
    }

    @Override
    public String toString() {
      return key.toString() + " " + value.toString();
    }

  }

  /**
   * Default constructor. Creates {@link SimpleHashtable} with 16 slots.
   */
  public SimpleHashtable() {
    this(16);
  }

  /**
   * Constructor which creates {@link SimpleHashtable} with tableSize number of
   * slots. If tableSize is not power of 2, it is increased to next power of 2.
   * 
   * @param capacity number of table slots
   * @throws IllegalArgumentException if tableSize is less than 1
   */
  @SuppressWarnings("unchecked")
  public SimpleHashtable(int capacity) {
    if(capacity < 1) {
      throw new IllegalArgumentException("Table size must be greater than 0.");
    }
    modificationCount = 0;
    table = (TableEntry<K, V>[]) new TableEntry[calculateTableSize(capacity)];
  }

  /**
   * Calculates number of slots in table. If table is power of 2, it does nothing.
   * If it is not, returns next power of 2 greater than tableSize parameter.
   * 
   * @param tableSize tableSize
   * @return first power of 2 greater than or equal than tableSize
   */
  private int calculateTableSize(int tableSize) {
    for (int i = 1; i < tableSize * 2; i *= 2) {
      if(i >= tableSize) {
        return i;
      }
    }
    return tableSize;
  }

  /**
   * Adds key value pair in one of table lists based on key hash. If key already
   * exists in table, sets its value to value parameter.
   * 
   * @param key key
   * @param value value
   * @throws IllegalArgumentException if key is null
   */
  public void put(K key, V value) {
    if(key == null) {
      throw new IllegalArgumentException("Key can not be null.");
    }
    int slot = key.hashCode() % table.length;
    if(slot < 0) {
      slot += table.length;
    }
    if(table[slot] == null) {
      table[slot] = new TableEntry<K, V>(key, value, null);
      size++;
      modificationCount++;
    }
    else {
      TableEntry<K, V> iterator = table[slot];
      while (true) {
        if(iterator.key.equals(key)) {
          iterator.setValue(value);
          return;
        }
        else {
          if(iterator.next == null) {
            iterator.next = new TableEntry<K, V>(key, value, null);
            modificationCount++;
            size++;
            if((double) size / (double) table.length >= 0.75) {
              increaseTableCapacity();
            }
          }
          iterator = iterator.next;
        }
      }
    }
  }

  /**
   * Method which doubles the number of table slots. It also reassigns all table
   * entries according to new hashing function ie. in more slots.
   */
  private void increaseTableCapacity() {
    @SuppressWarnings("unchecked")
    TableEntry<K, V>[] newTable = (TableEntry<K, V>[]) new TableEntry[table.length * 2];
    for (int i = 0; i < table.length; i++) {
      TableEntry<K, V> iterator = table[i];
      if(table[i] == null) {
        continue;
      }
      while (true) {
        saveToTable(iterator, newTable);
        if(iterator.next == null) {
          break;
        }
        iterator = iterator.next;
      }
    }
    table = newTable;

  }

  /**
   * Saves {@link TableEntry} entry to table table according to the hash function
   * of entry key modded by table size.
   * 
   * @param table table
   * @param entry table entry
   */
  private void saveToTable(TableEntry<K, V> entry, TableEntry<K, V>[] table) {
    int slot = entry.key.hashCode() % table.length;
    if(slot < 0) {
      slot += table.length;
    }
    if(table[slot] == null) {
      table[slot] = new TableEntry<K, V>(entry.key, entry.value, null);
    }
    else {
      TableEntry<K, V> iterator = table[slot];
      while (true) {
        if(iterator.next == null) {
          iterator.next = new TableEntry<K, V>(entry.key, entry.value, null);
          return;
        }
        iterator = iterator.next;
      }
    }

  }

  /**
   * Finds value for given key. If key is <code>null</code> or does not exist in
   * table, returns <code>null</code>. If it does, returns its value.
   * 
   * @param key key
   * @return <code>null</code> if key is <code>null</code> or does not exist, its
   *         value otherwise
   */
  public V get(Object key) {
    if(key == null) {
      return null;
    }

    int slot = key.hashCode() % table.length;

    if(slot < 0) {
      slot += table.length;
    }
    if(table[slot] == null) {
      return null;
    }
    else {
      TableEntry<K, V> iterator = table[slot];
      while (true) {
        if(iterator.key.equals(key)) {
          return iterator.value;
        }
        else {
          iterator = iterator.next;
          if(iterator == null) {
            return null;
          }
        }
      }
    }
  }

  /**
   * Returns number of elements in table.
   * 
   * @return number of elements in table
   */
  public int size() {
    return size;
  }

  /**
   * Finds key in table. If key is <code>null</code> or does not exist, returns
   * <code>false</code>. If it does, returns <code>true</code>.
   * 
   * @param key key
   * @return <code>false</code> if key is <code>null</code> or does not exist,
   *         <code>true</code> otherwise
   */
  public boolean containsKey(Object key) {
    if(key == null) {
      return false;
    }
    int slot = key.hashCode() % table.length;

    if(slot < 0) {
      slot += table.length;
    }
    
    TableEntry<K, V> iterator = table[slot];
    if(iterator == null) {
      return false;
    }
    while (true) {
      if(iterator.key.equals(key)) {
        return true;
      }
      else {
        iterator = iterator.next;
        if(iterator == null) {
          return false;
        }
      }
    }
  }

  /**
   * Finds value in table. If value does not exist, returns <code>false</code>. If
   * it does, returns <code>true</code>.
   * 
   * @param value value
   * @return <code>false</code> if value does not exist, <code>true</code>
   *         otherwise
   */
  public boolean containsValue(Object value) {
    for (int i = 0; i < table.length; i++) {
      TableEntry<K, V> iterator = table[i];
      if(iterator == null) {
        return false;
      }
      while (true) {
        if(iterator.value == null) {
          if(value == null) {
            return true;
          }
        }
        else {
          if(value != null && iterator.value.equals(value)) {
            return true;
          }
        }
        if(iterator.next != null) {
          iterator = iterator.next;
        }
        else {
          break;
        }
      }
    }

    return false;
  }

  /**
   * Removes key value pair for given key. If key is <code>null</code> or does not
   * exist in table, does nothing.
   * 
   * @param key key
   */
  public void remove(Object key) {
    if(key == null) {
      return;
    }
    int slot = key.hashCode() % table.length;

    if(slot < 0) {
      slot += table.length;
    }
    if(table[slot] == null) {
      return;
    }
    if(table[slot].key.equals(key)) {
      if(table[slot].next != null) {
        table[slot] = table[slot].next;
      }
      else {
        table[slot] = null;
      }
      modificationCount++;
      size--;
      return;
    }

    while (true) {
      TableEntry<K, V> iterator = table[slot];
      while (true) {
        if(iterator.next.key.equals(key)) {
          iterator.next = iterator.next.next;
          modificationCount++;
          size--;
          return;
        }
        if(iterator.next == null) {
          return;
        }
        else {
          iterator = iterator.next;
        }
      }
    }
  }

  /**
   * Checks if table is empty and returns <code>true</code> if it is. If it is
   * not, returns <code>false</code>.
   * 
   * @return <code>true</code> if tablee is empty, <code>false</code> otherwise
   */
  public boolean isEmpty() {
    if(size == 0) {
      return true;
    }
    return false;
  }

  /**
   * Returns table entries in string. Formats them like: [key1=value1,
   * key2=value2, key3=value3]. if there are no entries, returns empty brackets.
   * 
   * @return string made from table entries
   */
  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    boolean emptyBuilder = true;
    stringBuilder.append("[");
    for (int i = 0; i < table.length; i++) {
      TableEntry<K, V> iterator = table[i];
      while (true) {
        if(iterator == null) {
          break;
        }
        if(emptyBuilder) {
          emptyBuilder = false;
        }
        else {
          stringBuilder.append(", ");
        }
        stringBuilder.append(iterator.key.toString()).append("=").append(iterator.value.toString());
        iterator = iterator.next;
      }
    }

    stringBuilder.append("]");
    return stringBuilder.toString();
  }

  /**
   * Removes all etries from {@link SimpleHashtable}. Does not affect capacity.
   */
  public void clear() {
    for (int i = 0; i < table.length; i++) {
      table[i] = null;
    }
    size = 0;
  }

  /**
   * Creates {@link Iterator} that iterates over all {@link SimpleHashtable}
   * etries.
   */
  @Override
  public Iterator<TableEntry<K, V>> iterator() {
    return new IteratorImpl();
  }

  /**
   * {@link Iterator} class which implements methods for iterating over all
   * {@link SimpleHashtable} entries.
   * 
   * @author tin
   *
   */
  private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

    private int slot;
    private TableEntry<K, V> iterator;
    private TableEntry<K, V> iteratorNext;
    private int expectedCount;

    public IteratorImpl() {
      for (int i = 0; i < table.length; i++) {
        iteratorNext = table[i];
        if(iteratorNext != null) {
          break;
        }
        i++;
        slot = i;
      }
      expectedCount = modificationCount;
    }

    /**
     * Checks if there are entries over which this {@link Iterator} did not iterate.
     * If there are, returns <code>true</code>. If there are no entries left,
     * returns <code>false</code>.
     * 
     * @return <code>true</code> if there are entries over {@link Iterator} did not
     *         iterate, false otherwise
     */
    @Override
    public boolean hasNext() {
      checkIfAlteredFromOutside();
      if(iteratorNext == null) {
        return false;
      }
      return true;
    }

    /**
     * Returns next {@link TableEntry} from {@link SimpleHashtable}. If there are no
     * entries left, throws exception.
     * 
     * @return next table entries
     * @throws NoSuchElementException if there are no entries left
     */
    @Override
    public TableEntry<K, V> next() {
      checkIfAlteredFromOutside();
      if(!hasNext()) {
        throw new NoSuchElementException("There are no elements left");
      }
      iterator = iteratorNext;
      findNext();
      return iterator;
    }

    /**
     * Removes current {@link TableEntry} from {@link SimpleHashtable}. Throws
     * exception if it is called 2 or more times for same {@link TableEntry}.
     * 
     * @throws IllegalStateException if this {@link TableEntry} is already removed
     */
    public void remove() {
      checkIfAlteredFromOutside();
      if(iterator == null) {
        throw new IllegalStateException("Remove method can not be called because current entry is already removed.");
      }
      SimpleHashtable.this.remove(iterator.getKey());
      iterator = null;
      expectedCount++;
    }

    /**
     * Checks if {@link SimpleHashtable} over which this {@link Iterator} iterates
     * was modified from other source. If it has, throws exception.
     * 
     * @throws ConcurrentModificationException if {@link SimpleHashtable} is
     *           modified by other source
     */
    private void checkIfAlteredFromOutside() {
      if(expectedCount != modificationCount) {
        throw new ConcurrentModificationException();
      }
    }

    /**
     * Iterates to find next {@link TableEntry} and saves it to iteratorNext
     * variable.
     * 
     */
    private void findNext() {
      if(iterator == null) {
        return;
      }
      if(iterator.next != null) {
        iteratorNext = iterator.next;
        return;
      }

      for (int i = slot + 1; i < table.length; i++) {
        if(table[i] != null) {
          iteratorNext = table[i];
          slot = i;
          return;
        }
      }

      iteratorNext = null;
    }

  }
}
