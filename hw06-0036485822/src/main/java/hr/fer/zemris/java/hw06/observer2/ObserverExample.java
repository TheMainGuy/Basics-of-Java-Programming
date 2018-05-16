package hr.fer.zemris.java.hw06.observer2;

/**
 * Class used for testing {@link IntegerStorage} and {@link IntegerStorageObserver}.
 * 
 * @author tin
 *
 */
public class ObserverExample {
  /**
   * Main method which is called when program starts.
   * 
   * @param args command line arguments
   */
  public static void main(String[] args) {
    IntegerStorage istorage = new IntegerStorage(20);
    IntegerStorageObserver observer = new SquareValue();
    istorage.addObserver(new ChangeCounter());
    istorage.addObserver(new DoubleValue(3));
    istorage.addObserver(observer);
    
    istorage.setValue(5);
    istorage.setValue(2);
    istorage.setValue(25);
    istorage.setValue(13);
    istorage.setValue(22);
    istorage.setValue(15);
  }
}