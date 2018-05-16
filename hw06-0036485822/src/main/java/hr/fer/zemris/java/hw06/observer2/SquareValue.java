package hr.fer.zemris.java.hw06.observer2;

/**
 * Implements simple observer which prints out squared value each time value
 * variable of {@link IntegerStorage} on which this object is attached to is
 * changed.
 * 
 * @author tin
 *
 */
public class SquareValue implements IntegerStorageObserver {

  /**
   * Method called when value of {@link IntegerStorage} on which this object is
   * attached to changes. Prints squared changed value.
   * 
   * @param storageChange {@link IntegerStorageChange} change
   */
  @Override
  public void valueChanged(IntegerStorageChange storageChange) {
    System.out.println(
        "Provided new value: " + storageChange.getNewValue() + ", square is " + storageChange.getNewValue() * storageChange.getNewValue());
  }

}
