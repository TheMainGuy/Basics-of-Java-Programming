package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.listeners.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;

/**
 * Implements tool for drawing filled circles. When mouse is pressed, circle
 * will begin to grow as distance between mouse pointer and point where mouse
 * was pressed increases. When mouse is released, circle will be stored in
 * {@link DrawingModel} object given in constructor resulting in permanently
 * drawing object on canvas. Circle is filled with background color.
 * 
 * @author tin
 *
 */
public class FilledCircleTool extends ToolImpl {

  /**
   * Color used to fill the circle.
   */
  private IColorProvider backgroundColor;

  /**
   * Constructor.
   * 
   * @param drawingModel drawing model
   * @param foregroundColor foreground color
   * @param drawingCanvas drawing canvas
   * @param backgroundColor background color
   */
  public FilledCircleTool(DrawingModel drawingModel, IColorProvider foregroundColor, JDrawingCanvas drawingCanvas,
      IColorProvider backgroundColor) {
    super(drawingModel, foregroundColor, drawingCanvas);
    this.backgroundColor = backgroundColor;
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    super.mouseReleased(e);
    getDrawingModel().add(new FilledCircle(getStartX(), getStartY(), getForegroundColor().getCurrentColor(),
        (int) getRadius(), backgroundColor.getCurrentColor()));
    startX = -1;
  }

  @Override
  public void paint(Graphics2D g2d) {
    if(startX == -1) {
      return;
    }
    double radius = getRadius();
    int x = getStartX() - (int) radius;
    int y = getStartY() - (int) radius;
    g2d.setColor(backgroundColor.getCurrentColor());
    g2d.fillOval(x, y, 2 * (int) radius, 2 * (int) radius);
    g2d.setColor(getForegroundColor().getCurrentColor());
    g2d.drawOval(x, y, 2 * (int) radius, 2 * (int) radius);
  }

  /**
   * Returns circle radius.
   * 
   * @return circle radius
   */
  private double getRadius() {
    return Math.sqrt(Math.pow(getStartX() - getEndX(), 2) + Math.pow(getStartY() - getEndY(), 2));
  }

}
