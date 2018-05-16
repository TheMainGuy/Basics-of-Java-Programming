package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 * Class which represents one turtle state. It has turtle position, angle in
 * form of a unit vector upon x-axis, color in which turtle draws when shifting
 * and shift length which represents turtle shift length.
 * 
 * @author tin
 *
 */
public class TurtleState {
  private Vector2D position;
  private Vector2D angle;
  private Color color;
  private double shiftLength;

  /**
   * Creates turtle state.
   * 
   * @param position turtle position
   * @param angle degree angle upon x-axis
   * @param color draw color
   * @param shiftLength shift length
   * @throws NullPointerException if any argument is null
   */
  public TurtleState(Vector2D position, double angle, Color color, double shiftLength) {
    if(position == null || color == null) {
      throw new NullPointerException("TurtleState arguments can not be null.");
    }
    this.position = position;
    this.angle = new Vector2D(1,0);
    this.angle.rotate(angle);
    this.color = color;
    this.shiftLength = shiftLength;
  }

  /**
   * Creates copy of this turtle state.
   * 
   * @return new turtle state that is copy of this turtle state
   */
  public TurtleState copy() {
    return new TurtleState(position, angle.getDegreeAngle() , color, shiftLength);
  }

  /**
   * Returns turtle position.
   * 
   * @return turtle position
   */
  public Vector2D getPosition() {
    return position;
  }

  /**
   * Returns turtle view angle.
   * 
   * @return turtle view angle
   */
  public Vector2D getAngle() {
    return angle;
  }

  /**
   * Returns turtle draw color.
   * 
   * @return turtle draw color
   */
  public Color getColor() {
    return color;
  }

  /**
   * Returns turtle shift length.
   * 
   * @return turtle shift length
   */
  public double getShiftLength() {
    return shiftLength;
  }
  
}
