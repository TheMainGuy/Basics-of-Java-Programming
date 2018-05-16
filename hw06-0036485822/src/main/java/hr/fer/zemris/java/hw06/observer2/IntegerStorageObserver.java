package hr.fer.zemris.java.hw06.observer2;

/**
 * Interface defines {@link IntegerStorage} observer. Each observer must
 * implement method value changed which is called upon value changing in given
 * {@link IntegerStorage}.
 * 
 * @author tin
 *
 */
public interface IntegerStorageObserver {
  /**
   * Method called when value is changed in given {@link IntegerStorage} istorage.
   * 
   * @param istorage integer storage
   */
  public void valueChanged(IntegerStorageChange storageChange);
}