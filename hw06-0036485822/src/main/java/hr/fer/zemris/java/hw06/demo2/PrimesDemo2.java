package hr.fer.zemris.java.hw06.demo2;

/**
 * Used for testing {@link PrimesCollection} class.
 * 
 * @author tin
 *
 */
public class PrimesDemo2 {
  /**
   * Main method which is called when program starts.
   * 
   * @param args command line arguments
   */
  public static void main(String[] args) {
    PrimesCollection primesCollection = new PrimesCollection(2);
    for (Integer prime : primesCollection) {
      for (Integer prime2 : primesCollection) {
        System.out.println("Got prime pair: " + prime + ", " + prime2);
      }
    }
  }
}
