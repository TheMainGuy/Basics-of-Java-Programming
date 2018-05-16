package hr.fer.zemris.java.hw06.observer2;

/**
 * Implements simple observer which prints out number of value changes since it
 * was attached to {@link IntegerStorage}.
 * 
 * @author tin
 *
 */
public class ChangeCounter implements IntegerStorageObserver {
  // Number of value changes counter
  private int counter = 0;

  /**
   * Method called when value of {@link IntegerStorage} on which this object is
   * attached to changes. Prints the number of value changes since it was attached
   * to istorage.
   * 
   * @param istorage {@link IntegerStorage} on which this object is attached to
   */
  @Override
  public void valueChanged(IntegerStorageChange storageChange) {
    counter++;
    System.out.println("Number of value changes since tracking: " + counter);

  }

}
