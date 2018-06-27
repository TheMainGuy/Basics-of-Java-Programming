package hr.fer.zemris.java.hw16.objects;

import java.awt.Color;

import hr.fer.zemris.java.hw16.objects.editors.CircleEditor;
import hr.fer.zemris.java.hw16.objects.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.objects.visitors.GeometricalObjectVisitor;

public class Circle extends GeometricalObject {
  private int x;
  private int y;
  private int radius;

  /**
   * @return the x
   */
  public int getX() {
    return x;
  }

  /**
   * @param x the x to set
   */
  public void setX(int x) {
    this.x = x;
    notifyAllListeners(l -> {
      l.geometricalObjectChanged(this);
      return null;
    });
  }

  /**
   * @return the y
   */
  public int getY() {
    return y;
  }

  /**
   * @param y the y to set
   */
  public void setY(int y) {
    this.y = y;
    notifyAllListeners(l -> {
      l.geometricalObjectChanged(this);
      return null;
    });
  }

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

  public Circle(int x, int y, Color color, int x2, int y2, int radius) {
    super(x, y, color);
    x = x2;
    y = y2;
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

}
