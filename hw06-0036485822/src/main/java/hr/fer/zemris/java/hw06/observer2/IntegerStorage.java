package hr.fer.zemris.java.hw06.observer2;

import java.util.ArrayList;
import java.util.List;

/**
 * Class implements storage of 1 int number value. It also has list of attached
 * {@link IntegerStorageObserver} observers and calls valueChanged method on
 * each one of them every time value is changed. Observers can be added and
 * removed using public methods.
 * 
 * @author tin
 *
 */
public class IntegerStorage {
  // Stored int value
  private int value;

  // List of observers that are attached to this object
  private List<IntegerStorageObserver> observers; // use ArrayList here!!!

  /**
   * Creates {@link IntegerStorage} with given initial value.
   * 
   * @param initialValue initial stored value
   */
  public IntegerStorage(int initialValue) {
    this.value = initialValue;
    observers = new ArrayList<>();
  }

  /**
   * Adds new observer to list of attached observers. Upon every added observer
   * will be called valueChanged method.
   * 
   * @param observer observer to be added
   * @throws NullPointerException if observer is <code>null</code>
   */
  public void addObserver(IntegerStorageObserver observer) {
    // add the observer in observers if not already there ...
    if(observer == null) {
      throw new NullPointerException("Observer can not be null.");
    }
    if(observers.contains(observer)) {
      return;
    }
    observers.add(observer);
  }

  /**
   * Removes given observer if it exists in observers {@link List}. Does nothing
   * if given observer is not in observers. Reference to removed observers is no
   * longer kept and valueChanged will no longer be called upon them when value is
   * changed.
   * 
   * @param observer observer to be removed
   */
  public void removeObserver(IntegerStorageObserver observer) {
    // remove the observer from observers if present ...
    observers.remove(observer);
  }

  /**
   * Removes all observers from observers {@link List}.
   */
  public void clearObservers() {
    // remove all observers from observers list ...
    observers.clear();
  }

  /**
   * Returns int value.
   * 
   * @return value
   */
  public int getValue() {
    return value;
  }

  /**
   * Sets value variable to given value. If new value if different than previous
   * value, calls valueChanged upon all attached observers.
   * 
   * @param value value
   */
  public void setValue(int value) {
    // Only if new value is different than the current value:
    if(this.value != value) {
      IntegerStorageChange storageChange = new IntegerStorageChange(this.value, value, this);
      // Update current value
      this.value = value;
      // Notify all registered observers
      if(observers != null) {

        for (int i = 0; i < observers.size(); i++) {
          int tempSize = observers.size();
          observers.get(i).valueChanged(storageChange);
          if(observers.size() < tempSize) {
            i--;
          }
        }
      }
    }
  }
}