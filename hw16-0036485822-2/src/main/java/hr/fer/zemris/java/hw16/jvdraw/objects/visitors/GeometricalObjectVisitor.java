package hr.fer.zemris.java.hw16.jvdraw.objects.visitors;

import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

/**
 * Defines geometrical object visitor which does something based on objects it
 * visits.
 * 
 * @author tin
 *
 */
public interface GeometricalObjectVisitor {
  /**
   * Visits line object.
   * @param line line to be visited
   */
  public abstract void visit(Line line);

  /**
   * Visits circle object.
   * 
   * @param circle circle to be visited
   */
  public abstract void visit(Circle circle);

  /**
   * Visits filled circle object.
   * 
   * @param filledCircle filled circle to be visited
   */
  public abstract void visit(FilledCircle filledCircle);
}
