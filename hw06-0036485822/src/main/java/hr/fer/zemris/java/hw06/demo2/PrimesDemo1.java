package hr.fer.zemris.java.hw06.demo2;

/**
 * Used for testing {@link PrimesCollection} class.
 * 
 * @author tin
 *
 */
public class PrimesDemo1 {
  /**
   * Main method which is called when program starts.
   * 
   * @param args command line arguments
   */
  public static void main(String[] args) {
    PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them
    for (Integer prime : primesCollection) {
      System.out.println("Got prime: " + prime);
    }
  }
}
