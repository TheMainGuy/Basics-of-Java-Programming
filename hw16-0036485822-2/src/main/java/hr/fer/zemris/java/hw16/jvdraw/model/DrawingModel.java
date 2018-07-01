package hr.fer.zemris.java.hw16.jvdraw.model;

import hr.fer.zemris.java.hw16.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * Defines drawing model. Drawing model should keep list of
 * {@link DrawingModelListener}s and list of {@link GeometricalObject}s. When
 * change occurs in any object, object is added or removed, it must notify all
 * of its listeners.
 * 
 * Also acts as an adapter towards list of {@link GeometricalObject}s, allowing
 * simple access to its size, adding, removing objects and changing objects
 * order.
 * 
 * @author tin
 *
 */
public interface DrawingModel {
  /**
   * Returns size of objects list. Size is defined as number of elements in that
   * list.
   * 
   * @return size
   */
  public int getSize();

  /**
   * Returns object from objects list located at given index.
   * 
   * @param index index used to get object
   * @return object at given index in objects list
   */
  public GeometricalObject getObject(int index);

  /**
   * Adds given object to objects list.
   * 
   * @param object object to be added to objects list
   */
  public void add(GeometricalObject object);

  /**
   * Adds new listener to list of listeners. Each listener should be notified when
   * anything changes within the model.
   * 
   * @param l listener to be added
   */
  public void addDrawingModelListener(DrawingModelListener l);

  /**
   * Removes listener from list of listeners.
   * 
   * @param l listener to be removed
   */
  public void removeDrawingModelListener(DrawingModelListener l);

  /**
   * Removes given object from objects list if it exists. If it does not, does
   * nothing.
   * 
   * @param object object to be removed
   */
  public void remove(GeometricalObject object);

  /**
   * Changes order in objects list by finding given object and shifting it by
   * given offset. Offset can have negative value which will effectively shift
   * object towards smaller index.
   * 
   * @param object object to be shifted
   * @param offset offset by which object should be shifted
   */
  public void changeOrder(GeometricalObject object, int offset);
}
