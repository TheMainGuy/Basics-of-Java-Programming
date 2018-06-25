package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents a vector used to store data. Vector can have any number of
 * elements, but operations between two vectors require both vectors to have
 * the same number of elements.
 * 
 * @author tin
 *
 */
public class Vector {

  /**
   * List of vector coordinates.
   */
  private List<Double> values = new ArrayList<>();

  /**
   * Constructor
   */
  public Vector(List<Double> values) {
    this.values = values;
  }

  /**
   * Returns voctor's norm ie. length.
   * 
   * @return norm
   */
  public double norm() {
    double norm = 0;
    for (double value : values) {
      norm += value * value;
    }
    return Math.sqrt(norm);
  }

  /**
   * Calculates dot product operation between this vector and the other vector.
   * Dot product is calculated by summing products of each curresponding
   * components.
   * 
   * @param other other vector
   * @return dot product of this vector and the other vector
   * @throws NullPointerException if offset vector is null
   */
  public double dot(Vector other) {
    if(other == null) {
      throw new NullPointerException("Other vector can not be null.");
    }
    if(size() != other.size()) {
      throw new UnsupportedOperationException();
    }
    double dot = 0;
    for (int i = 0, n = values.size(); i < n; i++) {
      dot += values.get(i) * other.getElementAt(i);
    }
    return dot;
  }

  /**
   * Returns size of this vector. Size is defined by the number of elements in
   * this vector.
   * 
   * @return number of elements in this vector
   */
  private int size() {
    return values.size();
  }

  /**
   * Calculates cosine of angle between this vector and the other vector.
   * 
   * @param other other vector
   * @return cosine of angle between this vector and the other vector
   * @throws NullPointerException if other vector is null
   */
  public double cosAngle(Vector other) {
    if(other == null) {
      throw new NullPointerException("Other vector can not be null.");
    }
    if(size() != other.size()) {
      throw new UnsupportedOperationException("Vectors must have same number of elements.");
    }
    return this.dot(other) / (this.norm() * other.norm());
  }

  /**
   * Returns this vector's element at given index.
   * 
   * @param index index used to get element
   * @return element at given index
   */
  public double getElementAt(int index) {
    return values.get(index);
  }
}
