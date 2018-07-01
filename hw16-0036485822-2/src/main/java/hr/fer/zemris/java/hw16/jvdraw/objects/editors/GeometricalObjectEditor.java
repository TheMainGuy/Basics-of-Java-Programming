package hr.fer.zemris.java.hw16.jvdraw.objects.editors;

import javax.swing.JPanel;

import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * Defines object editor used to edit {@link GeometricalObject}s. Each object
 * editor must implement checkEditing method which checks if edited values are
 * valid and acceptEditing which stores edited values.
 * 
 * @author tin
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Checks if edited fields have invalid values. Throws
   * {@link IllegalArgumentException} if any of input parameters are invalid.
   * 
   * @throws IllegalArgumentException if any of input parameters are invalid
   */
  public abstract void checkEditing() throws IllegalArgumentException;

  /**
   * Accepts editing and stores edited data.
   */
  public abstract void acceptEditing();
}
