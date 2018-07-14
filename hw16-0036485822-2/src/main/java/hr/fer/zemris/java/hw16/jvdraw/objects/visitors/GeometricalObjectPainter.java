package hr.fer.zemris.java.hw16.jvdraw.objects.visitors;

import java.awt.Graphics2D;

import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledPolygon;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

/**
 * Geometrical object visitor which paints every object it visits. Paints using
 * {@link Graphics2D} object which was given as parameter in constructor.
 * 
 * @author tin
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {
  /**
   * Graphics2D object used to paint.
   */
  Graphics2D g2d;

  /**
   * Constructor.
   * 
   * @param g2d graphics which will be used to paint
   */
  public GeometricalObjectPainter(Graphics2D g2d) {
    this.g2d = g2d;
  }

  @Override
  public void visit(Line line) {
    g2d.setColor(line.getColor());
    g2d.drawLine(line.getX(), line.getY(), line.getX2(), line.getY2());
  }

  @Override
  public void visit(Circle circle) {
    g2d.setColor(circle.getColor());
    int x = circle.getX() - circle.getRadius();
    int y = circle.getY() - circle.getRadius();
    g2d.drawOval(x, y, circle.getRadius() * 2, circle.getRadius() * 2);
  }

  @Override
  public void visit(FilledCircle filledCircle) {
    int x = filledCircle.getX() - filledCircle.getRadius();
    int y = filledCircle.getY() - filledCircle.getRadius();
    g2d.setColor(filledCircle.getFillColor());
    g2d.fillOval(x, y, filledCircle.getRadius() * 2, filledCircle.getRadius() * 2);
    g2d.setColor(filledCircle.getColor());
    g2d.drawOval(x, y, filledCircle.getRadius() * 2, filledCircle.getRadius() * 2);
  }

  @Override
  public void visit(FilledPolygon filledPolygon) {
    int n = filledPolygon.getCoordinates().size();
    int[] x = new int[n];
    int[] y = new int[n];
    for(int i = 0; i < filledPolygon.getCoordinates().size(); i++) {
      x[i] = filledPolygon.getCoordinates().get(i).getX();
      y[i] = filledPolygon.getCoordinates().get(i).getY();
    }
    g2d.setColor(filledPolygon.getFillColor());
    g2d.fillPolygon(x, y, n);
    g2d.setColor(filledPolygon.getColor());
    g2d.drawPolygon(x, y, n);
  }

}
