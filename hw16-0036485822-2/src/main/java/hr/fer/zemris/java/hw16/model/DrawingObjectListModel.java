package hr.fer.zemris.java.hw16.model;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import hr.fer.zemris.java.hw16.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw16.objects.GeometricalObject;

public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private DrawingModel drawingModel;

  public DrawingObjectListModel(DrawingModel drawingModel) {
    this.drawingModel = drawingModel;
    drawingModel.addDrawingModelListener(this);
  }

  @Override
  public GeometricalObject getElementAt(int index) {
    return drawingModel.getObject(index);
  }

  @Override
  public int getSize() {
    return drawingModel.getSize();
  }

  @Override
  public void objectsAdded(DrawingModel source, int index0, int index1) {
    ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index0, index1);
    for (ListDataListener listener : getListDataListeners()) {
      listener.intervalAdded(event);
    }
  }

  @Override
  public void objectsRemoved(DrawingModel source, int index0, int index1) {
    ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index0, index1);
    for (ListDataListener listener : getListDataListeners()) {
      listener.intervalRemoved(event);
    }
  }

  @Override
  public void objectsChanged(DrawingModel source, int index0, int index1) {
    ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index0, index1);
    for (ListDataListener listener : getListDataListeners()) {
      listener.contentsChanged(event);
    }
  }

}
