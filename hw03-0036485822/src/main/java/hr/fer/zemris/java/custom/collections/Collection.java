package hr.fer.zemris.java.custom.collections;

/**
 * Class which represents collection and has all methods which collection should
 * have. Currently has only a few methods implemented and empty ones help
 * extending classes to know what to implement.
 * 
 * @author tin
 *
 */
public class Collection {
  protected Collection() {

  }

  /**
   * Method which returns true if size is 0 which means that the collection is
   * empty. Otherwise false.
   * 
   * @return true if collection is empty and false if it is not.
   */
  public boolean isEmpty() {
    if(size() > 0) {
      return false;
    }
    else {
      return true;
    }
  }

  /**
   * Method which returns the number of elements in the collection. Not
   * implemented yet.
   * 
   * @return 0
   */
  public int size() {
    return 0;
  }

  /**
   * Method which adds the given object into the collection. Not implemented yet.
   * 
   * @param value object to be added to collection
   */
  public void add(Object value) {

  }

  /**
   * Method which checks if the collection contains the given object. Not
   * implemented yet.
   * 
   * @param value object to be checked
   * @return false
   */
  public boolean contains(Object value) {
    return false;
  }

  /**
   * Method which removes first occurence of the given object from the collection.
   * Not implemented yet.
   * 
   * @param value object to be removed from collection
   * @return false
   */
  public boolean remove(Object value) {
    return false;
  }

  /**
   * Method which converts the collection to Object array. Not implemented yet.
   * Not implemented yet.
   * 
   * @throws UnsupportedOperationException always
   */
  public Object[] toArray() {
    throw new UnsupportedOperationException();
  }

  /**
   * Method calls processor.process(.) for each element of this collection Not
   * implemented yet.
   * 
   * @param processor processor in which the method process will be called
   */
  public void forEach(Processor processor) {

  }

  /**
   * Method adds into the current collection all elements from the given
   * collection. This other collection remains unchanged.
   * 
   * @param other collection from which the elements are added
   */
  public void addAll(Collection other) {
    class ElementAdder extends hr.fer.zemris.java.custom.collections.Processor {

      @Override
      public void process(Object value) {
        add(value);
      }
    }

    Processor processor = new ElementAdder();
    other.forEach(processor);

  }

  public void clear() {

  }
}
