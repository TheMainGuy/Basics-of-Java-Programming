package hr.fer.zemris.java.hw06.observer1;

/**
 * Implements simple observer which prints out doubled value each time value
 * variable of {@link IntegerStorage} on which this object is attached to is
 * changed. Does this for a maximum of n times, where n is given in constructor,
 * and then removes itself from list of attached observers.
 * 
 * 
 * @author tin
 *
 */
public class DoubleValue implements IntegerStorageObserver {
  // Number of times this object will print out doubled value
  private int n;

  /**
   * Constructor.
   * 
   * @param n number of times this object will print out doubled value
   * @throws IllegalArgumentException if n is not positive integer
   */
  public DoubleValue(int n) {
    if(n <= 0) {
      throw new IllegalArgumentException("Constructor must be positive integer.");
    }
    this.n = n;
  }

  /**
   * Method called when value of {@link IntegerStorage} on which this object is
   * attached to changes. Prints double changed value.
   * 
   * @param istorage {@link IntegerStorage} on which this object is attached to
   */
  @Override
  public void valueChanged(IntegerStorage istorage) {
    System.out.println("Double value: " + istorage.getValue() * 2);

    n--;
    if(n == 0) {
      istorage.removeObserver(this);
    }
  }

}
