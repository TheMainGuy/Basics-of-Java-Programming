package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.objects.visitors.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw16.jvdraw.objects.visitors.GeometricalObjectPainter;

/**
 * Defines export action. Action will crop drawing to smallest rectangle
 * containing all drawn elements
 * 
 * @author tin
 *
 */
public class ExportAction extends AbstractAction {

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
  private JFrame jVDraw;
  
  /**
   * Format which will be used to export drawing.
   */
  private String format;

  /**
   * Constructor.
   * 
   * @param drawingModel drawing model
   * @param jVDraw jVDraw
   * @param format format
   */
  public ExportAction(DrawingModel drawingModel, JFrame jVDraw, String format) {
    this.drawingModel = drawingModel;
    this.jVDraw = jVDraw;
    this.format = format;
  }

  @Override
  public void actionPerformed(ActionEvent arg0) {
    GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
    for (int i = 0, n = drawingModel.getSize(); i < n; i++) {
      drawingModel.getObject(i).accept(bbcalc);
    }
    Rectangle box = bbcalc.getBoundingBox();
    System.out.println(box.x + " " + box.y + " " + box.width + " " + box.height);
    BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);
    Graphics2D g = image.createGraphics();
    g.translate(-box.x, -box.y);
    g.fillRect(box.x, box.y, box.width, box.height);
    GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
    for (int i = 0, n = drawingModel.getSize(); i < n; i++) {
      drawingModel.getObject(i).accept(painter);
    }
    g.dispose();
    try {
      JFileChooser fc = new JFileChooser();
      fc.setDialogTitle("Export drawing");
      fc.setDialogType(JFileChooser.SAVE_DIALOG);
      if(fc.showSaveDialog(jVDraw) != JFileChooser.APPROVE_OPTION) {
        return;
      }
      File file = fc.getSelectedFile();
      if(!file.toString().endsWith("." + format)) {
        file = new File(file.toString() + "." + format);
      }
      ImageIO.write(image, format, file);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(jVDraw, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

  }

}
