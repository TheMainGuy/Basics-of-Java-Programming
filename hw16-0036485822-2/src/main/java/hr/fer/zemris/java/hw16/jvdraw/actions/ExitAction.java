package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;

/**
 * Defines exit action. Action will try to close JVDraw program. If the file has
 * been edited since last save or open, it will ask user if the drawing should
 * be saved, not saved or the exit action should be canceled.
 */
public class ExitAction extends AbstractAction {
  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * Reference to {@link JVDraw} object.
   */
  private JVDraw jVDraw;
  
  /**
   * Reference to save action.
   */
  private SaveAction saveAction;

  /**
   * Constructor.
   * 
   * @param jVDraw main program
   * @param saveAction save action
   */
  public ExitAction(JVDraw jVDraw, SaveAction saveAction) {
    this.jVDraw = jVDraw;
    this.saveAction = saveAction;
  }

  @Override
  public void actionPerformed(ActionEvent arg0) {
    if(jVDraw.isEdited()) {
      int option = JOptionPane.showConfirmDialog(jVDraw, "Would you like to save changes?", "Exit?",
          JOptionPane.YES_NO_CANCEL_OPTION);
      if(option == JOptionPane.NO_OPTION) {
        System.exit(0);
      } else if(option == JOptionPane.YES_OPTION) {
        saveAction.actionPerformed(arg0);
        System.exit(0);
      }
    } else {
      System.exit(0);
    }
  }

}
