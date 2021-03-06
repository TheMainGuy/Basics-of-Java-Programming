package hr.fer.zemris.java.hw16.jvdraw.objects.visitors;

import java.awt.Rectangle;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.Coordinate;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledPolygon;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

/**
 * Geometrical object visitor which calculates bounding box of all objects it
 * visits. Bounding box is defined as the smallest rectangle containing all
 * given objects.
 * 
 * @author tin
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {
  /**
   * Flag which is set to false after first object is used to calculate bounding
   * box.
   */
  boolean firstObject = true;

  /**
   * Leftmost coordinate.
   */
  int x = 0;

  /**
   * Topmost coordinate.
   */
  int y = 0;

  /**
   * Rightmost coordinate.
   */
  int x2 = 0;

  /**
   * Bottommost coordinate.
   */
  int y2 = 0;

  @Override
  public void visit(Line line) {
    updateBoundingBox(Math.min(line.getX(), line.getX2()), Math.min(line.getY(), line.getY2()),
        Math.max(line.getX(), line.getX2()), Math.max(line.getY(), line.getY2()));
  }

  @Override
  public void visit(Circle circle) {
    updateBoundingBox(circle.getX() - circle.getRadius(), circle.getY() - circle.getRadius(),
        circle.getX() + circle.getRadius(), circle.getY() + circle.getRadius());
  }

  @Override
  public void visit(FilledCircle filledCircle) {
    updateBoundingBox(filledCircle.getX() - filledCircle.getRadius(), filledCircle.getY() - filledCircle.getRadius(),
        filledCircle.getX() + filledCircle.getRadius(), filledCircle.getY() + filledCircle.getRadius());
  }

  @Override
  public void visit(FilledPolygon filledPolygon) {
    List<Coordinate> coos = filledPolygon.getCoordinates();
    boolean f = true;
    int l = 0;
    int t = 0;
    int r = 0;
    int b = 0;
    for (int i = 0; i < coos.size(); i++) {
      Coordinate co = coos.get(i);
      if(f) {
        f = false;
        l = co.getX();
        r = l;
        t = co.getY();
        b = t;
      } else {
        if(co.getX() < l) {
          l = co.getX();
        }
        if(co.getX() > r) {
          r = co.getX();
        }
        if(co.getY() < t) {
          t = co.getY();
        }
        if(co.getY() > b) {
          b = co.getY();
        }
      }
    }
    
    updateBoundingBox(l, t, r, b);
  }

  /**
   * Updates bounding box coordinates using new coordinates.
   * 
   * @param left possible new leftmost coordinate
   * @param top possible new topmost coordinate
   * @param right possible new rightmost coordinate
   * @param bottom possible new bottommost coordinate
   */
  private void updateBoundingBox(int left, int top, int right, int bottom) {
    if(firstObject) {
      x = left;
      y = top;
      x2 = right;
      y2 = bottom;
      firstObject = false;
      return;
    }
    x = Math.min(left, x);
    y = Math.min(y, top);
    x2 = Math.max(x2, right);
    y2 = Math.max(y2, bottom);
  }

  /**
   * Returns bounding box using stored values. Bouding box is {@link Rectangle}
   * object.
   * 
   * @return bounding box
   */
  public Rectangle getBoundingBox() {
    return new Rectangle(x, y, x2 - x, y2 - y);
  }

}
