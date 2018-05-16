package hr.fer.zemris.java.hw06.observer2;

/**
 * Represents one change in {@link IntegerStorage} value. Has information about
 * old value, new value and reference to {@link IntegerStorage} in which the
 * change was made.
 * 
 * @author tin
 *
 */
public class IntegerStorageChange {
  // Value stored before the change
  private int oldValue;

  // Value stored after the change
  private int newValue;

  // Reference to IntegerStorage
  private IntegerStorage integerStorage;

  /**
   * Constructor.
   * 
   * @param oldValue old value
   * @param newValue new value
   * @param integerStorage reference to integer storage
   */
  public IntegerStorageChange(int oldValue, int newValue, IntegerStorage integerStorage) {
    this.oldValue = oldValue;
    this.newValue = newValue;
    this.integerStorage = integerStorage;
  }

  /**
   * Returns value stored before the change.
   * @return value stored before the change
   */
  public int getOldValue() {
    return oldValue;
  }

  /**
   * Returns value stored after the change.
   * @return value stored after the change
   */
  public int getNewValue() {
    return newValue;
  }

  /**
   * Returns {@link IntegerStorage} in which the change was made.
   * @return {@link IntegerStorage} in which the change was made
   */
  public IntegerStorage getIntegerStorage() {
    return integerStorage;
  }

}
