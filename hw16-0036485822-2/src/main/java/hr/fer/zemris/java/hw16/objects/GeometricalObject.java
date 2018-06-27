package hr.fer.zemris.java.hw16.objects;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import hr.fer.zemris.java.hw16.jvdraw.listeners.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.objects.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.objects.visitors.GeometricalObjectVisitor;

/**
 * Represents simple geometrical object for painting. Has coordinates and color.
 * All classes extending this class must implement accept method which allows
 * visitor objects to visit.
 * 
 * Implements listener support which allows listeners to register and unregister
 * to listen to any changes made in this object.
 * 
 * @author tin
 *
 */
public abstract class GeometricalObject {
  /**
   * x coordinate.
   */
  private int x;

  /**
   * y coordinate.
   */
  private int y;

  /**
   * Color.
   */
  private Color color;

  /**
   * List of listeners listening to this object.
   */
  private List<GeometricalObjectListener> listeners = new ArrayList<>();

  /**
   * Constructor.
   * 
   * @param x x coordinate
   * @param y y coordinate
   * @param color color
   */
  public GeometricalObject(int x, int y, Color color) {
    this.x = x;
    this.y = y;
    this.color = color;
  }

  /**
   * @return the x
   */
  public int getX() {
    return x;
  }

  /**
   * @param x the x to set
   */
  public void setX(int x) {
    this.x = x;
    notifyAllListeners(l -> {
      l.geometricalObjectChanged(this);
      return null;
    });
  }

  /**
   * @return the y
   */
  public int getY() {
    return y;
  }

  /**
   * @param y the y to set
   */
  public void setY(int y) {
    this.y = y;
    notifyAllListeners(l -> {
      l.geometricalObjectChanged(this);
      return null;
    });
  }

  /**
   * Accepts given {@link GeometricalObjectVisitor} object and calls its visit
   * method.
   * 
   * @param v visitor
   */
  public abstract void accept(GeometricalObjectVisitor v);

  /**
   * @return the color
   */
  public Color getColor() {
    return color;
  }

  /**
   * @param color the color to set
   */
  public void setColor(Color color) {
    this.color = color;
    notifyAllListeners(l -> {
      l.geometricalObjectChanged(this);
      return null;
    });
  }

  /**
   * Creates new {@link GeometricalObjectEditor} object giving it this object as
   * parameter. That editor can only edit this object.
   * 
   * @return created editor for this object
   */
  public abstract GeometricalObjectEditor createGeometricalObjectEditor();

  /**
   * Adds listener to list of listeners.
   * 
   * @param l listener to be added
   * @throws NullPointerException if given listener is null
   */
  public void addGeometricalObjectListener(GeometricalObjectListener l) {
    if(l == null) {
      throw new NullPointerException();
    }
    listeners.add(l);
  }

  /**
   * Removes listener from list of listeners. Does nothing if given listener does
   * not exist in listeners list.
   * 
   * @param l listener to be removed
   */
  public void removeGeometricalObjectListener(GeometricalObjectListener l) {
    listeners.remove(l);
  }

  /**
   * When called, notifies all listeners listening to this object using given
   * function.
   * 
   * @param f function used to notify each listener
   */
  protected void notifyAllListeners(Function<GeometricalObjectListener, Void> f) {
    for (GeometricalObjectListener listener : listeners) {
      f.apply(listener);
    }
  }

}
