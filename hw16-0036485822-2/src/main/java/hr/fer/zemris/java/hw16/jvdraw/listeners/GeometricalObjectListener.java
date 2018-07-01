package hr.fer.zemris.java.hw16.jvdraw.listeners;

import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * Defines listener which listens to {@link GeometricalObject}. It will be
 * notified upon every change made in listened object.
 * 
 * @author tin
 *
 */
public interface GeometricalObjectListener {
  /**
   * Method which is called when any change occurs in listened object.
   * 
   * @param o listened object
   */
  public void geometricalObjectChanged(GeometricalObject o);
}
