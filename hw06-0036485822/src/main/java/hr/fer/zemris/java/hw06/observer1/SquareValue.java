package hr.fer.zemris.java.hw06.observer1;

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
   * @param istorage {@link IntegerStorage} on which this object is attached to
   */
  @Override
  public void valueChanged(IntegerStorage istorage) {
    System.out.println(
        "Provided new value: " + istorage.getValue() + ", square is " + istorage.getValue() * istorage.getValue());
  }

}
