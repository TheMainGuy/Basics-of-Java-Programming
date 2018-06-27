package hr.fer.zemris.java.hw16.objects;

import java.awt.Color;

import hr.fer.zemris.java.hw16.objects.editors.CircleEditor;
import hr.fer.zemris.java.hw16.objects.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.objects.visitors.GeometricalObjectVisitor;

public class Circle extends GeometricalObject {
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

}
