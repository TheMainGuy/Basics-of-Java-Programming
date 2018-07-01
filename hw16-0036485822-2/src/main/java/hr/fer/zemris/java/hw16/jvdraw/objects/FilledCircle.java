package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.objects.editors.FilledCircleEditor;
import hr.fer.zemris.java.hw16.jvdraw.objects.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.objects.visitors.GeometricalObjectVisitor;

/**
 * Represents filled circle which has two color properties. One for its circular
 * and one for its fill.
 * 
 * @author tin
 *
 */
public class FilledCircle extends Circle {
  /**
   * Color used to fill.
   */
  private Color fillColor;

  /**
   * Constructor.
   * 
   * @param x x coordinate of center
   * @param y y coordinate of center
   * @param color color
   * @param radius radius
   * @param fillColor fill color
   */
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

  @Override
  public GeometricalObjectEditor createGeometricalObjectEditor() {
    return new FilledCircleEditor(this);
  }

  @Override
  public String toString() {
    String colorString = String.format("%02X%02X%02X", fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue());
    return super.toString() + ", #" + colorString;
  }
}
