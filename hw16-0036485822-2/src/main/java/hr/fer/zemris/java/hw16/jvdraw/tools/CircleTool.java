package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.listeners.IColorProvider;
import hr.fer.zemris.java.hw16.model.DrawingModel;
import hr.fer.zemris.java.hw16.objects.Circle;

public class CircleTool extends ToolImpl {

  public CircleTool(DrawingModel drawingModel, IColorProvider foregroundColor, JDrawingCanvas drawingCanvas) {
    super(drawingModel, foregroundColor, drawingCanvas);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    super.mouseReleased(e);
    getDrawingModel()
        .add(new Circle(getStartX(), getStartY(), getForegroundColor().getCurrentColor(), (int) getRadius()));
  }

  @Override
  public void paint(Graphics2D g2d) {
    double radius = getRadius();
    int x = getStartX() - (int) radius;
    int y = getStartY() - (int) radius;
    g2d.setColor(getForegroundColor().getCurrentColor());
    g2d.drawOval(x, y, 2 * (int) radius, 2 * (int) radius);
  }
  
  private double getRadius() {
    return Math.sqrt(Math.pow(getStartX() - getEndX(), 2) + Math.pow(getStartY() - getEndY(), 2));
  }

}
