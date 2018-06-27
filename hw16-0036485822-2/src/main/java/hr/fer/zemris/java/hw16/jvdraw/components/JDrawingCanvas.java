package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.tools.Tool;
import hr.fer.zemris.java.hw16.model.DrawingModel;
import hr.fer.zemris.java.hw16.objects.visitors.GeometricalObjectPainter;

/**
 * 
 * @author tin
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;
  private Tool currentState;
  /**
   * {@link DrawingModel} object which is used to store objects.
   */
  private DrawingModel drawingModel;

  public JDrawingCanvas(DrawingModel drawingModel) {
    this.drawingModel = drawingModel;
  }

  @Override
  public void objectsAdded(DrawingModel source, int index0, int index1) {
    repaint();
  }

  @Override
  public void objectsRemoved(DrawingModel source, int index0, int index1) {
    repaint();
  }

  @Override
  public void objectsChanged(DrawingModel source, int index0, int index1) {
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    GeometricalObjectPainter painter = new GeometricalObjectPainter((Graphics2D) g);
    for(int i = 0, n = drawingModel.getSize(); i < n; i++) {
      drawingModel.getObject(i).accept(painter);
    }
    currentState.paint((Graphics2D) g);
  }
  
  public void setCurrentState(Tool currentState) {
    this.currentState = currentState;
  }
}
