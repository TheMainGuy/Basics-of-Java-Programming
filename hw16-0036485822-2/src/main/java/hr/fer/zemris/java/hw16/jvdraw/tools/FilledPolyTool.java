package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw16.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.listeners.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.objects.Coordinate;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledPolygon;

public class FilledPolyTool extends ToolImpl {

  List<Coordinate> coos = new ArrayList<>();

  /**
   * Color used to fill the circle.
   */
  private IColorProvider backgroundColor;

  public FilledPolyTool(DrawingModel drawingModel, IColorProvider foregroundColor, JDrawingCanvas drawingCanvas,
      IColorProvider backgroundColor) {
    super(drawingModel, foregroundColor, drawingCanvas);
    this.backgroundColor = backgroundColor;
  }

  @Override
  public void paint(Graphics2D g2d) {
    if(coos.size() < 2) {
      if(coos.size() == 1) {
        g2d.setColor(getForegroundColor().getCurrentColor());
        g2d.drawLine(getStartX(), getStartY(), getEndX(), getEndY());
      }
      return;
    }
    int n = coos.size();
    int[] x = new int[n + 1];
    int[] y = new int[n + 1];
    for (int i = 0; i < coos.size(); i++) {
      x[i] = coos.get(i).getX();
      y[i] = coos.get(i).getY();
    }
    x[coos.size()] = endX;
    y[coos.size()] = endY;
    g2d.setColor(backgroundColor.getCurrentColor());
    g2d.fillPolygon(x, y, n + 1);
    g2d.setColor(getForegroundColor().getCurrentColor());

    g2d.drawPolygon(x, y, n + 1);
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    endX = e.getX();
    endY = e.getY();
    drawingCanvas.repaint();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if(SwingUtilities.isRightMouseButton(e)) {
      coos = new ArrayList<>();
      drawingCanvas.repaint();
    } else if(SwingUtilities.isLeftMouseButton(e)) {
      if(coos.size() == 0) {
        coos.add(new Coordinate(e.getX(), e.getY()));
      }

      boolean lt3 = false;
      for (int i = 0; i < coos.size(); i++) {
        int endX = e.getX();
        int endY = e.getY();
        int x = coos.get(i).getX();
        int y = coos.get(i).getY();

        if(Math.sqrt(Math.pow(x - endX, 2) + Math.pow(y - endY, 2)) < 3) {
          lt3 = true;
        }
      }

      if(lt3 && coos.size() < 3) {
        return;
      } else if(lt3) {
        getDrawingModel().add(new FilledPolygon(coos.get(0).getX(), coos.get(0).getY(),
            getForegroundColor().getCurrentColor(), coos, backgroundColor.getCurrentColor()));
        coos = new ArrayList<>();
      } else if(!lt3) {
        if(coos.size() < 3) {
          coos.add(new Coordinate(e.getX(), e.getY()));
        } else {
          List<Coordinate> coos2 = new ArrayList<>(coos);
          coos2.add(new Coordinate(e.getX(), e.getY()));
          FilledPolygon fp = new FilledPolygon(coos2.get(0).getX(), coos2.get(0).getY(),
              getForegroundColor().getCurrentColor(), coos2, backgroundColor.getCurrentColor());
          if(fp.isConvex()) {
            coos.add(new Coordinate(e.getX(), e.getY()));
          } else {
            JOptionPane.showMessageDialog(drawingCanvas, "Poligon nije konveksan", "Error", JOptionPane.ERROR_MESSAGE);
          }

        }
      }
    }

  }

}
