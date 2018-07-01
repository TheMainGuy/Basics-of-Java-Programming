package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;

/**
 * Defines open action. Action will invoke {@link JFileChooser} to allow user to
 * specify file path. If file has jvd extenstion, drawing will be opened, if it
 * does not, dialog will pop up. Previous drawing is lost if it was not saved.
 */
public class OpenAction extends AbstractAction {

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
  public OpenAction(DrawingModel drawingModel, JVDraw jVDraw) {
    this.drawingModel = drawingModel;
    this.jVDraw = jVDraw;
  }

  @Override public void actionPerformed(ActionEvent arg0) {
    try {
      JFileChooser fc = new JFileChooser();
      fc.setDialogTitle("Open drawing");
      fc.setDialogType(JFileChooser.OPEN_DIALOG);
      if(fc.showOpenDialog(jVDraw) != JFileChooser.APPROVE_OPTION) {
        return;
      }
      Path path = fc.getSelectedFile().toPath();
      
      Util.openDrawingModel(drawingModel, path);
      Util.currentDrawingPath = path;
      jVDraw.setEdited(false);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(jVDraw, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

  }

}
