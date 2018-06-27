package hr.fer.zemris.java.hw16.jvdraw.listeners;

import hr.fer.zemris.java.hw16.model.DrawingModel;

/**
 * Defines listener which listens to {@link DrawingModel} object for changes in
 * objects. Each time object is added, removed or changed, appropriate method
 * will be called upon all listeners listening to that {@link DrawingModel}
 * object.
 * 
 * @author tin
 *
 */
public interface DrawingModelListener {
  /**
   * Method which is called when object or objects are added to the
   * {@link DrawingModel} object which is listened to. index0 and index1
   * parameters define range of objects from objects list added, both index0 and
   * index1 inclusive.
   * 
   * @param source {@link DrawingModel} which is listened to
   * @param index0 start index of objects added inclusive
   * @param index1 end index of objects added inclusive
   */
  public void objectsAdded(DrawingModel source, int index0, int index1);

  /**
   * Method which is called when object or objects are removed to the
   * {@link DrawingModel} object which is listened to. index0 and index1
   * parameters define range of objects from objects list removed, both index0 and
   * index1 inclusive.
   * 
   * @param source {@link DrawingModel} which is listened to
   * @param index0 start index of objects removed inclusive
   * @param index1 end index of objects removed inclusive
   */
  public void objectsRemoved(DrawingModel source, int index0, int index1);

  /**
   * Method which is called when object or objects are changed to the
   * {@link DrawingModel} object which is listened to. index0 and index1
   * parameters define range of objects from objects list which are changed, both
   * index0 and index1 inclusive.
   * 
   * @param source {@link DrawingModel} which is listened to
   * @param index0 start index of changed objects inclusive
   * @param index1 end index of changed objects inclusive
   */
  public void objectsChanged(DrawingModel source, int index0, int index1);
}
