package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;

/**
 * Defines drawing save as action. Action will invoke {@link JFileChooser} to
 * allow user to specify save path and save drawing to specified save path.
 * 
 * Drawings are saved with jvd extension.
 */
public class SaveAsAction extends AbstractAction {
  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * Reference to {@link DrawingModel} object.
   */
  private DrawingModel drawingModel;
  
  /**
   * Reference to {@link JVDraw} object.
   */
  private JVDraw jVDraw;
  
  /**
   * Constructor.
   * 
   * @param drawingModel drawing model
   * @param jVDraw jVDraw
   */
  public SaveAsAction(DrawingModel drawingModel, JVDraw jVDraw) {
    this.drawingModel = drawingModel;
    this.jVDraw = jVDraw;
  }

  @Override
  public void actionPerformed(ActionEvent arg0) {
    if(drawingModel.getSize() == 0) {
      return;
    }
    try {
      JFileChooser fc = new JFileChooser();
      fc.setDialogTitle("Save drawing");
      fc.setDialogType(JFileChooser.SAVE_DIALOG);
      if(fc.showSaveDialog(jVDraw) != JFileChooser.APPROVE_OPTION) {
        return;
      }
      Util.currentDrawingPath = fc.getSelectedFile().toPath();
      Util.saveDrawingModel(drawingModel, Util.currentDrawingPath);
      jVDraw.setEdited(false);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(jVDraw, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    
  }

}
