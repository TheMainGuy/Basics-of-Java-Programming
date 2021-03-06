package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.objects.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.objects.editors.LineEditor;
import hr.fer.zemris.java.hw16.jvdraw.objects.visitors.GeometricalObjectVisitor;

/**
 * Represents line object with some basic properties and methods.
 * 
 * @author tin
 *
 */
public class Line extends GeometricalObject {

  /**
   * x end coordinate.
   */
  int x2;

  /**
   * y end coordinate.
   */
  int y2;

  /**
   * Constructor.
   * 
   * @param x x start coordinate
   * @param y y start coordinate
   * @param color color 
   * @param x2 x end coordinate
   * @param y2 y end coordinate
   */
  public Line(int x, int y, Color color, int x2, int y2) {
    super(x, y, color);
    this.x2 = x2;
    this.y2 = y2;
  }

  /**
   * @return the x2
   */
  public int getX2() {
    return x2;
  }

  /**
   * @param x2 the x2 to set
   */
  public void setX2(int x2) {
    this.x2 = x2;
    notifyAllListeners(l -> {
      l.geometricalObjectChanged(this);
      return null;
    });
  }

  /**
   * @return the y2
   */
  public int getY2() {
    return y2;
  }

  /**
   * @param y2 the y2 to set
   */
  public void setY2(int y2) {
    this.y2 = y2;
    notifyAllListeners(l -> {
      l.geometricalObjectChanged(this);
      return null;
    });
  }

  @Override
  public void accept(GeometricalObjectVisitor v) {
    v.visit(this);
  }

  @Override
  public GeometricalObjectEditor createGeometricalObjectEditor() {
    return new LineEditor(this);
  }

  @Override
  public String toString() {
    return "Line (" + getX() + "," + getY() + ")-(" + x2 + "," + y2 + ")";
  }

}
