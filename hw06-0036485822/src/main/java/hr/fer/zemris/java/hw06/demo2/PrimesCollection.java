package hr.fer.zemris.java.hw06.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implements {@link Iterable} collection of prime numbers without storing any
 * number in any kind of collection.
 * 
 * @author tin
 *
 */
public class PrimesCollection implements Iterable<Integer> {
  // Number of prime numbers in this collection
  private int numberOfPrimes;

  /**
   * Constructor. Determines how many prime numbers this collection defines.
   * 
   * @param numberOfPrimes number of prime numbers
   */
  public PrimesCollection(int numberOfPrimes) {
    this.numberOfPrimes = numberOfPrimes;
  }

  /**
   * Creates and returns {@link Iterator} to iterate the prime numbers collection.
   * 
   * @return iterator used to iterate this collection
   */
  @Override
  public Iterator<Integer> iterator() {
    return new IteratorImpl();
  }

  /**
   * Implementation of iterator. Iterates for a maximum of numberOfPrimes times
   * and returns prime numbers on each next call.
   * 
   * @author tin
   *
   */
  private class IteratorImpl implements Iterator<Integer> {
    // Stores the number of prime numbers already iterated over
    int currentPrimeIndex = 0;

    // Last prime number returned
    int lastPrime = 1;

    @Override
    public boolean hasNext() {
      return currentPrimeIndex < numberOfPrimes;
    }

    @Override
    public Integer next() {
      if(!hasNext()) {
        throw new NoSuchElementException("There are no elements left");
      }
      currentPrimeIndex++;
      return getPrime();
    }

    /**
     * Calculates prime number based on last prime number.
     * 
     * @return next prime number
     */
    private Integer getPrime() {
      while (true) {
        lastPrime++;
        if(isPrime(lastPrime)) {
          return lastPrime;
        }
      }
    }

    /**
     * Checks if given number is prime number. Returns <code>true</code> if it is
     * and <code>false</code> if it is not.
     * 
     * @param lastPrime number
     * @return <code>true</code> if lastPrime is prime, <code>false</code> if it is
     *         not
     */
    private boolean isPrime(int lastPrime) {
      if(lastPrime == 2) {
        return true;
      }
      for (int i = 2, n = (int) Math.sqrt(lastPrime); i <= n; i++) {
        if(lastPrime % i == 0) {
          return false;
        }
      }
      return true;
    }

  }
}
