package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.listeners.IColorProvider;
import hr.fer.zemris.java.hw16.model.DrawingModel;

public abstract class ToolImpl implements Tool {
  private IColorProvider foregroundColor;
  private JDrawingCanvas drawingCanvas;
  private DrawingModel drawingModel;
  protected int startX;
  protected int startY;
  protected int endX;
  protected int endY;

  public ToolImpl(DrawingModel drawingModel, IColorProvider foregroundColor, JDrawingCanvas drawingCanvas) {
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
  }

  @Override
  public void mouseClicked(MouseEvent e) {
  }

  @Override
  public void mouseMoved(MouseEvent e) {
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    endX = e.getX();
    endY = e.getY();
    drawingCanvas.repaint();
  }

  /**
   * @return the startX
   */
  public int getStartX() {
    return startX;
  }

  /**
   * @return the startY
   */
  public int getStartY() {
    return startY;
  }

  /**
   * @return the endX
   */
  public int getEndX() {
    return endX;
  }

  /**
   * @return the endY
   */
  public int getEndY() {
    return endY;
  }

  /**
   * @return the foregroundColor
   */
  public IColorProvider getForegroundColor() {
    return foregroundColor;
  }

  /**
   * @return the drawingModel
   */
  public DrawingModel getDrawingModel() {
    return drawingModel;
  }

}
