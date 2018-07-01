package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.objects.editors.CircleEditor;
import hr.fer.zemris.java.hw16.jvdraw.objects.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.objects.visitors.GeometricalObjectVisitor;

/**
 * Represents circle object with some basic properties and methods.
 * 
 * @author tin
 *
 */
public class Circle extends GeometricalObject {
  
  /**
   * Radius.
   */
  private int radius;

  /**
   * @return the radius
   */
  public int getRadius() {
    return radius;
  }

  /**
   * @param radius the radius to set
   */
  public void setRadius(int radius) {
    this.radius = radius;
    notifyAllListeners(l -> {
      l.geometricalObjectChanged(this);
      return null;
    });
  }

  /**
   * Constructor.
   * 
   * @param x x coordinate of center
   * @param y y coordinate of center
   * @param color color
   * @param radius radius
   */
  public Circle(int x, int y, Color color, int radius) {
    super(x, y, color);
    this.radius = radius;
  }

  @Override
  public void accept(GeometricalObjectVisitor v) {
    v.visit(this);
  }

  @Override
  public GeometricalObjectEditor createGeometricalObjectEditor() {
    return new CircleEditor(this);
  }

  @Override
  public String toString() {
    return "Circle (" + getX() + "," + getY() + "), " + getRadius();
  }

}
