package hr.fer.zemris.math;

/**
 * Class represents a 2D vector. Vector has coordinates (x,y) which determine
 * its direction and
 * 
 * @author tin
 *
 */
public class Vector2D {
  private double x;
  private double y;

  /**
   * Creates 2D vector with x and y coordinates.
   * 
   * @param x x coordinate
   * @param y y coordinate
   */
  public Vector2D(double x, double y) {
    this.x = x;
    this.y = y;
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
   * @return value of y.
   */
  public double getY() {
    return y;
  }

  /**
   * Translates vector by given offset. Adds offset x and y to this vector's x and
   * y.
   * 
   * @param offset offset vector
   * @throws NullPointerException if offset is null
   */
  public void translate(Vector2D offset) {
    if(offset == null) {
      throw new NullPointerException("Offset vector can not be null.");
    }
    x += offset.getX();
    y += offset.getY();
  }

  /**
   * Creates new vector that is translated by given offset. Sums offset x and y
   * with this vector's x and y.
   * 
   * @param offset offset vector
   * @return new vector that is combination of this vector and offset vector
   * @throws NullPointerException if offset vector is null
   */
  public Vector2D translated(Vector2D offset) {
    if(offset == null) {
      throw new NullPointerException("Offset vector can not be null.");
    }
    return new Vector2D(x + offset.getX(), y + offset.getY());
  }

  /**
   * Rotates this vector by given angle. Angle is in degrees and the vector is
   * rotated counterclockwise.
   * 
   * @param angle angle
   */
  public void rotate(double angle) {
    Vector2D tempVector = rotated(angle);
    x = tempVector.getX();
    y = tempVector.getY();
  }

  /**
   * Creates new vector by rotating this vector by given angle. Angle is in
   * degrees and the vector is rotated counterclockwise.
   * 
   * @param angle angle
   * @return new vector that is created by rotating this vector
   */
  public Vector2D rotated(double angle) {
    double angleRad = angle * Math.PI / 180;
    double newX = Math.cos(angleRad) * x - Math.sin(angleRad) * y;
    double newY = Math.sin(angleRad) * x + Math.cos(angleRad) * y;
    return new Vector2D(newX, newY);
  }

  /**
   * Scales this vector by scaler value.
   * 
   * @param scaler scaler
   */
  public void scale(double scaler) {
    x *= scaler;
    y *= scaler;
  }

  /**
   * Creates new vector by scaling this vector by scaler value.
   * 
   * @param scaler scaler
   * @return new vector that is created by scaling this vector
   */
  public Vector2D scaled(double scaler) {
    return new Vector2D(x * scaler, y * scaler);
  }

  /**
   * Creates new vector that is copy of this vector.
   * 
   * @return copy of this vector
   */
  public Vector2D copy() {
    return new Vector2D(x, y);
  }
  
  public double getDegreeAngle() {
    double degreeAngle = Math.atan2(y, x) * 180 / Math.PI;
    if(degreeAngle < 0) {
      degreeAngle+=360;
    }
    return degreeAngle;
  }
}
