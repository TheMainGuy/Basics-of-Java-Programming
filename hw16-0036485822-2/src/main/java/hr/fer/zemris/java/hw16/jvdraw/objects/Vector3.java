package hr.fer.zemris.java.hw16.jvdraw.objects;

/**
 * Class represents a 3D vector. Vector has coordinates (x,y,z) which determine
 * its direction and magnitude.
 * 
 * @author tin
 *
 */
public class Vector3 {
  /**
   * First compoonent.
   */
  private double x;

  /**
   * Second component.
   */
  private double y;

  /**
   * Third component.
   */
  private double z;

  /**
   * Constructor
   * 
   * @param x x coordinate
   * @param y y coordinate
   * @param z z coordinate
   */
  public Vector3(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  /**
   * Returns voctor's norm ie. length.
   * 
   * @return norm
   */
  public double norm() {
    return Math.sqrt(x * x + y * y + z * z);
  }

  /**
   * Creates a new {@link Vector3} by normalizing this vector.
   * 
   * @return normalized vector
   */
  public Vector3 normalized() {
    return scale(1 / norm());
  }

  /**
   * Creates a new {@link Vector3} by adding given vector to this vector. Addition
   * is performed by summing each vector component with its corresponding
   * component from other vector.
   * 
   * @param other other vector
   * @return new {@link Vector3} that is result addition of this vector and the
   *         other vector
   * @throws NullPointerException if offset vector is null
   */
  public Vector3 add(Vector3 other) {
    if(other == null) {
      throw new NullPointerException("Other vector can not be null.");
    }
    return new Vector3(x + other.getX(), y + other.getY(), z + other.getZ());
  }

  /**
   * Creates a new {@link Vector3} by subtracting given vector from this vector.
   * Subtraction is performed by subtracting each vector component from its
   * corresponding component from other vector.
   * 
   * @param other other vector
   * @return new {@link Vector3} that is result of subtraction of the other vector
   *         from this vector
   * @throws NullPointerException if offset vector is null
   */
  public Vector3 sub(Vector3 other) {
    if(other == null) {
      throw new NullPointerException("Other vector can not be null.");
    }
    return new Vector3(x - other.getX(), y - other.getY(), z - other.getZ());
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
  public double dot(Vector3 other) {
    if(other == null) {
      throw new NullPointerException("Other vector can not be null.");
    }
    return x * other.getX() + y * other.getY() + z * other.getZ();
  }

  /**
   * Creates a new {@link Vector3} by performing cross product operation between
   * this vector and the other vector.
   * 
   * @param other other vector
   * @return new {@link Vector3} created by performing cross product between this
   *         vector and the other vector
   * @throws NullPointerException if other vector is null
   */
  public Vector3 cross(Vector3 other) {
    if(other == null) {
      throw new NullPointerException("Other vector can not be null.");
    }
    return new Vector3(y * other.getZ() - z * other.getY(), z * other.getX() - x * other.getZ(),
        x * other.getY() - y * other.getX());
  }

  /**
   * Creates a new {@link Vector3} by scaling this vector by given value.
   * 
   * @param s scaler
   * @return new {@link Vector3} that is created by scaling this vector
   */
  public Vector3 scale(double s) {
    return new Vector3(x * s, y * s, z * s);
  }

  /**
   * Calculates cosine of angle between this vector and the other vector.
   * 
   * @param other other vector
   * @return cosine of angle between this vector and the other vector
   * @throws NullPointerException if other vector is null
   */
  public double cosAngle(Vector3 other) {
    if(other == null) {
      throw new NullPointerException("Other vector can not be null.");
    }
    return this.dot(other) / (this.norm() * other.norm());
  }

  /**
   * Returns value of x.
   * 
   * @return value of x
   */
  public double getX() {
    return x;
  }

  /**
   * Returns value of y.
   * 
   * @return value of y
   */
  public double getY() {
    return y;
  }

  /**
   * Returns value of z.
   * 
   * @return value of z
   */
  public double getZ() {
    return z;
  }

  /**
   * Returns array of vector components.
   * 
   * @return array of vector components
   */
  public double[] toArray() {
    double[] array = { x, y, z };
    return array;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(30);
    stringBuilder.append("(").append(String.format("%.6f", x)).append(", ").append(String.format("%.6f", y))
        .append(", ").append(String.format("%.6f", z)).append(")");
    return stringBuilder.toString();
  }

}
