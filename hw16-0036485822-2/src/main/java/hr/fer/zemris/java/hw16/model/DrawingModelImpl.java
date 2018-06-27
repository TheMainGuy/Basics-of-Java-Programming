package hr.fer.zemris.java.hw16.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import hr.fer.zemris.java.hw16.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.listeners.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.objects.GeometricalObject;

public class DrawingModelImpl implements DrawingModel, GeometricalObjectListener{
  List<GeometricalObject> objects = new ArrayList<>();

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
