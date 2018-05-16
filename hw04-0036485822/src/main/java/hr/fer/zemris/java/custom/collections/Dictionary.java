package hr.fer.zemris.java.custom.collections;

/**
 * Class which represents an instance of dictionary. Stores key value pairs
 * which can be accessed using keys. Key can not be null while value can.
 * 
 * @author tin
 *
 */
public class Dictionary {
  class Pair {
    Object key;
    Object value;

    /**
     * Class which represents one key value pair. Key can not be null
     * 
     * @param key key
     * @param value value
     * @throws NullPointerException when key is null
     */
    Pair(Object key, Object value) {
      if(key == null) {
        throw new NullPointerException("Key can not be null.");
      }
      this.key = key;
      this.value = value;
    }
  }

  private ArrayIndexedCollection dictionary = new ArrayIndexedCollection();

  /**
   * Constructor
   */
  public Dictionary() {

  }

  /**
   * Checks if the dictionary is empty. Returns true if it is and false if it is
   * not.
   * 
   * @return true if dictionary is empty, false otherwise
   */
  public boolean isEmpty() {
    return dictionary.isEmpty();
  }

  /**
   * Returns the number of key value pairs in dictionary.
   * 
   * @return number of key value pairs
   */
  public int size() {
    return dictionary.size();
  }

  public void clear() {
    dictionary.clear();
  }

  /**
   * Puts new key value pair in dictionary. If key value pair already exsists in
   * dictionary, replaces its value with new value.
   * 
   * @param key key
   * @param value value
   * @throws NullPointerException if key is null
   */
  public void put(Object key, Object value) {
    if(key == null) {
      throw new NullPointerException("Key can not be null.");
    }
    for (int i = 0; i < dictionary.size(); i++) {
      if(((Pair) dictionary.get(i)).key.equals(key)) {
        dictionary.remove(i);
        dictionary.insert(new Pair(key, value), i);
        return;
      }
    }
    dictionary.add(new Pair(key, value));
  }

  /**
   * Finds value for given key. If there is no value for given key, returns null.
   * 
   * @param key key
   * @return value if key is in dictionary, null otherwise
   * @throws NullPointerException if key is null
   */
  public Object get(Object key) {
    if(key == null) {
      throw new NullPointerException("Key can not be null.");
    }
    for (int i = 0; i < dictionary.size(); i++) {
      if(((Pair) dictionary.get(i)).key.equals(key)) {
        return ((Pair) dictionary.get(i)).value;
      }
    }
    return null;
  }
}
