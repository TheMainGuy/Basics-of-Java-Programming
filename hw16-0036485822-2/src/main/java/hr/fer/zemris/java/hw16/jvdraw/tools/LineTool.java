package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.listeners.IColorProvider;
import hr.fer.zemris.java.hw16.model.DrawingModel;
import hr.fer.zemris.java.hw16.objects.Line;

public class LineTool extends ToolImpl{
  public LineTool(DrawingModel drawingModel, IColorProvider foregroundColor, JDrawingCanvas drawingCanvas) {
    super(drawingModel, foregroundColor, drawingCanvas);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    super.mouseReleased(e);
    getDrawingModel().add(new Line(getStartX(), getStartY(), getForegroundColor().getCurrentColor(),
        getEndX(), getEndY()));
    startX = -1;
  }

  @Override
  public void paint(Graphics2D g2d) {
    if(startX == -1) {
      return;
    }
    g2d.setColor(getForegroundColor().getCurrentColor());
    g2d.drawLine(getStartX(), getStartY(),
        getEndX(), getEndY());
  }

}
