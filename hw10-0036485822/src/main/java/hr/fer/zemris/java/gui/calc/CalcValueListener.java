package hr.fer.zemris.java.gui.calc;

/**
 * Defines value listener method which calculator value listeners should
 * implement.
 * 
 * @author tin
 *
 */
public interface CalcValueListener {
  /**
   * Called each time value is changed on model this listener is listening to.
   * 
   * @param model model which is listened on by this object
   */
  void valueChanged(CalcModel model);
}