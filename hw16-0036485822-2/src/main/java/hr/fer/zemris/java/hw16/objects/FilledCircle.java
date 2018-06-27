package hr.fer.zemris.java.hw16.objects;

import java.awt.Color;

import hr.fer.zemris.java.hw16.objects.visitors.GeometricalObjectVisitor;

public class FilledCircle extends Circle {
  private Color fillColor;

  public FilledCircle(int x, int y, Color color, int radius, Color fillColor) {
    super(x, y, color, radius);
    this.fillColor = fillColor;
  }

  /**
   * @return the fillColor
   */
  public Color getFillColor() {
    return fillColor;
  }

  /**
   * @param fillColor the fillColor to set
   */
  public void setFillColor(Color fillColor) {
    this.fillColor = fillColor;
    notifyAllListeners(l -> {
      l.geometricalObjectChanged(this);
      return null;
    });
  }

  @Override
  public void accept(GeometricalObjectVisitor v) {
    v.visit(this);
  }
}
