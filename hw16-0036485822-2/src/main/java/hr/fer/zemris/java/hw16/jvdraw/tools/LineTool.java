package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.listeners.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

/**
 * Implements tool for drawing circles. When mouse is pressed, line drawing will
 * start. Line end will follow mouse pointer until the mosue is released. When
 * the mouse is released, line will be stored in {@link DrawingModel} object
 * given in constructor resulting in permanently drawing object on canvas.
 * 
 * @author tin
 *
 */
public class LineTool extends ToolImpl {
  
  /**
   * Constructor.
   * 
   * @param drawingModel drawing model
   * @param foregroundColor foreground color
   * @param drawingCanvas drawing canvas
   */
  public LineTool(DrawingModel drawingModel, IColorProvider foregroundColor, JDrawingCanvas drawingCanvas) {
    super(drawingModel, foregroundColor, drawingCanvas);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    super.mouseReleased(e);
    getDrawingModel()
        .add(new Line(getStartX(), getStartY(), getForegroundColor().getCurrentColor(), getEndX(), getEndY()));
    startX = -1;
  }

  @Override
  public void paint(Graphics2D g2d) {
    if(startX == -1) {
      return;
    }
    g2d.setColor(getForegroundColor().getCurrentColor());
    g2d.drawLine(getStartX(), getStartY(), getEndX(), getEndY());
  }

}
