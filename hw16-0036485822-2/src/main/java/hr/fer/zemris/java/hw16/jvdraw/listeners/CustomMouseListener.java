package hr.fer.zemris.java.hw16.jvdraw.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import hr.fer.zemris.java.hw16.jvdraw.tools.Tool;

/**
 * Implements simple mouse listener which combines {@link MouseListener} and
 * {@link MouseMotionListener} interfaces in order to simplify and organize
 * code.
 * 
 * @author tin
 *
 */
public class CustomMouseListener implements MouseListener, MouseMotionListener {
  /**
   * Current drawing tool.
   */
  private Tool currentState;

  /**
   * Constructor.
   * 
   * @param currentState current state
   */
  public CustomMouseListener(Tool currentState) {
    this.currentState = currentState;
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    currentState.mouseDragged(e);
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    currentState.mouseMoved(e);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    currentState.mouseClicked(e);
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
    currentState.mousePressed(e);

  }

  @Override
  public void mouseReleased(MouseEvent e) {
    currentState.mouseReleased(e);
  }

  /**
   * @param currentState the currentState to set
   */
  public void setCurrentState(Tool currentState) {
    this.currentState = currentState;
  }

}
