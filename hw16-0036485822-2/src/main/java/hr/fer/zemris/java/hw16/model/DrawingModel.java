package hr.fer.zemris.java.hw16.model;

import hr.fer.zemris.java.hw16.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw16.objects.GeometricalObject;

public interface DrawingModel {
  public int getSize();

  public GeometricalObject getObject(int index);

  public void add(GeometricalObject object);

  public void addDrawingModelListener(DrawingModelListener l);

  public void removeDrawingModelListener(DrawingModelListener l);

  public void remove(GeometricalObject object);

  public void changeOrder(GeometricalObject object, int offset);
}
