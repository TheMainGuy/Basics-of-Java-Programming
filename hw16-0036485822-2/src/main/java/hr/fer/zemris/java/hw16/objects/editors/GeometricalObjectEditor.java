package hr.fer.zemris.java.hw16.objects.editors;

import javax.swing.JPanel;

public abstract class GeometricalObjectEditor extends JPanel {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Checks if edited fields have invalid values.
   */
  public abstract void checkEditing();

  /**
   * Accepts editing and saves edited data.
   */
  public abstract void acceptEditing();
}
