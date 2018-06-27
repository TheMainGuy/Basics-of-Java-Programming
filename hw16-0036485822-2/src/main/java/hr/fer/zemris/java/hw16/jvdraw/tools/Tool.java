package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * Defines one tool for drawing in JVDraw. Each class implementing this class
 * will draw one specific type of object.
 * 
 * @author tin
 *
 */
public interface Tool {
  /**
   * Method which is called when mouse is pressed.
   * 
   * @param e mouse event
   */
  public void mousePressed(MouseEvent e);

  /**
   * Method which is called when mouse is released.
   * 
   * @param e mouse event
   */
  public void mouseReleased(MouseEvent e);

  /**
   * Method which is called when mouse is clicked.
   * 
   * @param e mouse event
   */
  public void mouseClicked(MouseEvent e);

  /**
   * Method which is called when mouse is moved.
   * 
   * @param e mouse event
   */
  public void mouseMoved(MouseEvent e);

  /**
   * Method which is called when mouse is dragged.
   * 
   * @param e mouse event
   */
  public void mouseDragged(MouseEvent e);

  /**
   * Method which paints drawn object on given graphics.
   * 
   * @param g2d graphics
   */
  public void paint(Graphics2D g2d);
}
