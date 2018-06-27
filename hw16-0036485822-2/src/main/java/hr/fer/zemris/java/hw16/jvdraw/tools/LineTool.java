package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.listeners.IColorProvider;
import hr.fer.zemris.java.hw16.model.DrawingModel;
import hr.fer.zemris.java.hw16.objects.Line;

public class LineTool implements Tool {
  private DrawingModel drawingModel;
  private IColorProvider foregroundColor;
  private int startX;
  private int startY;
  private int endX;
  private int endY;
  
  private JDrawingCanvas drawingCanvas;

  public LineTool(DrawingModel drawingModel, IColorProvider foregroundColor, JDrawingCanvas drawingCanvas) {
    this.drawingModel = drawingModel;
    this.foregroundColor = foregroundColor;
    this.drawingCanvas = drawingCanvas;
  }

  @Override
  public void mousePressed(MouseEvent e) {
    startX = e.getX();
    startY = e.getY();
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    endX = e.getX();
    endY = e.getY();
    drawingCanvas.repaint();
    drawingModel.add(new Line(startX, startY, foregroundColor.getCurrentColor(),
        endX, endY));
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseMoved(MouseEvent e) {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseDragged(MouseEvent e) {
    endX = e.getX();
    endY = e.getY();
    drawingCanvas.repaint();
  }

  @Override
  public void paint(Graphics2D g2d) {
    g2d.setColor(foregroundColor.getCurrentColor());
    g2d.drawLine(startX, startY,
        endX, endY);
  }

}
