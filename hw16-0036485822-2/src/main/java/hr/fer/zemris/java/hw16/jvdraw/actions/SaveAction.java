package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;

/**
 * Implements save action. Action will save current drawing to its file path. If
 * drawing does not have its file path set, action will invoke
 * {@link JFileChooser} to allow user to specify save path.
 * 
 * Drawings are saved with jvd extension.
 */
public class SaveAction extends AbstractAction {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * Reference to {@link DrawingModel} object.
   */
  private DrawingModel drawingModel;
  
  /**
   * Reference to save as action.
   */
  private SaveAsAction saveAsAction;
  
  /**
   * Reference to {@link JVDraw} object.
   */
  private JVDraw jVDraw;
  
  /**
   * Constructor.
   * 
   * @param jVDraw jVDraw
   * @param drawingModel drawing model
   * @param saveAsAction save as action
   */
  public SaveAction(JVDraw jVDraw, DrawingModel drawingModel, SaveAsAction saveAsAction) {
    this.jVDraw = jVDraw;
    this.drawingModel = drawingModel;
    this.saveAsAction = saveAsAction;
  }

  @Override
  public void actionPerformed(ActionEvent arg0) {
    try {
      if(drawingModel.getSize() == 0) {
        return;
      }
      if(Util.currentDrawingPath == null) {
        saveAsAction.actionPerformed(arg0);
        return;
      }
      Util.saveDrawingModel(drawingModel, Util.currentDrawingPath);
      jVDraw.setEdited(false);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } 

  }

}
