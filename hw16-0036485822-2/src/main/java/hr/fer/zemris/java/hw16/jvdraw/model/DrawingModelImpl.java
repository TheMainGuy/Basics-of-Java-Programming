package hr.fer.zemris.java.hw16.jvdraw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import hr.fer.zemris.java.hw16.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.listeners.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * Implements all methods defined in {@link DrawingModel}. Keeps list of
 * {@link DrawingModelListener}s and list of {@link GeometricalObject}s. When
 * change occurs in any object, object is added or removed, notifies all of its
 * listeners.
 * 
 * Also acts as an adapter towards list of {@link GeometricalObject}s, allowing
 * simple access to its size, adding, removing objects and changing objects
 * order.
 * 
 * @author tin
 *
 */
public class DrawingModelImpl implements DrawingModel, GeometricalObjectListener {
  /**
   * List of {@link GeometricalObject}s.
   */
  List<GeometricalObject> objects = new ArrayList<>();

  /**
   * List of listeners.
   */
  List<DrawingModelListener> listeners = new ArrayList<>();

  @Override
  public int getSize() {
    return objects.size();
  }

  @Override
  public GeometricalObject getObject(int index) {
    return objects.get(index);
  }

  @Override
  public void add(GeometricalObject object) {
    objects.add(object);
    object.addGeometricalObjectListener(this);
    notifyListeners(l -> {
      l.objectsAdded(this, objects.size() - 1, objects.size() - 1);
      return null;
    });
  }

  @Override
  public void addDrawingModelListener(DrawingModelListener l) {
    if(l == null) {
      throw new NullPointerException();
    }
    listeners.add(l);
  }

  @Override
  public void removeDrawingModelListener(DrawingModelListener l) {
    listeners.remove(l);
  }

  @Override
  public void remove(GeometricalObject object) {
    int index = objects.indexOf(object);
    objects.remove(index);
    notifyListeners(l -> {
      l.objectsRemoved(this, index, index);
      return null;
    });
    object.removeGeometricalObjectListener(this);
  }

  @Override
  public void changeOrder(GeometricalObject object, int offset) {
    int index = objects.indexOf(object);
    objects.remove(index);
    objects.add(index + offset, object);
    notifyListeners(l -> {
      l.objectsAdded(this, index, index + offset);
      return null;
    });
  }

  /**
   * Notifies all listeners listening to this object using given function.
   * 
   * @param f function used to notify each listener
   */
  private void notifyListeners(Function<DrawingModelListener, Void> f) {
    for (DrawingModelListener listener : listeners) {
      f.apply(listener);
    }
  }

  @Override
  public void geometricalObjectChanged(GeometricalObject o) {
    int index = objects.indexOf(o);
    notifyListeners(l -> {
      l.objectsChanged(this, index, index);
      return null;
    });
  }

}
