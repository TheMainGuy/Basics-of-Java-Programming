package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.hw16.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.components.JColorInfo;
import hr.fer.zemris.java.hw16.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.listeners.CustomMouseListener;
import hr.fer.zemris.java.hw16.jvdraw.listeners.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.LineTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.Tool;
import hr.fer.zemris.java.hw16.model.DrawingModel;
import hr.fer.zemris.java.hw16.model.DrawingModelImpl;

public class JVDraw extends JFrame {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Tool currentState;

  private DrawingModel drawingModel;

  /**
   * Method which is called when program starts.
   * 
   * @param args command line arguments
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      new JVDraw();
    });
  }

  public JVDraw() {
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setLocation(0, 0);
    setSize(900, 600);
    setVisible(true);
    setTitle("JVDraw");

    initGUI();
  }

  /**
   * Initializes GUI elements.
   */
  private void initGUI() {
    this.getContentPane().setLayout(new BorderLayout());

    createMenu();

    createToolbar();
    JColorArea colorArea1 = new JColorArea(Color.BLACK);
    JColorArea colorArea2 = new JColorArea(Color.WHITE);
    JToolBar toolBar = new JToolBar("Tools");
    toolBar.setFloatable(true);
    toolBar.add(colorArea1);
    toolBar.addSeparator();
    toolBar.add(colorArea2);
    this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
    createStatusBar(colorArea1, colorArea2);
    
    
    createDrawingCanvas();
    drawingModel = new DrawingModelImpl();
    JDrawingCanvas drawingCanvas = new JDrawingCanvas(drawingModel);
    drawingModel.addDrawingModelListener(drawingCanvas);
    LineTool lineTool = new LineTool(drawingModel, (IColorProvider) colorArea1, drawingCanvas);
    CircleTool circleTool = new CircleTool(drawingModel, colorArea1, drawingCanvas);
    FilledCircleTool filledCircleTool = new FilledCircleTool(drawingModel, colorArea1, drawingCanvas, colorArea2);
    currentState = lineTool;
    drawingCanvas.setCurrentState(currentState);
    JButton lineButton = new JButton("Line");
    JButton circleButton = new JButton ("Circle");
    JButton filledCircleButton = new JButton("Filled Circle");
    ButtonGroup buttonGroup = new ButtonGroup();
    buttonGroup.add(lineButton);
    buttonGroup.add(circleButton);
    buttonGroup.add(filledCircleButton);
    toolBar.addSeparator();
    toolBar.add(lineButton);
    toolBar.add(circleButton);
    toolBar.add(filledCircleButton);
    CustomMouseListener mouseListener = new CustomMouseListener(currentState);
    lineButton.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent arg0) {
        mouseListener.setCurrentState(lineTool);
        drawingCanvas.setCurrentState(lineTool);
      }
    });
    circleButton.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent arg0) {
        mouseListener.setCurrentState(circleTool);
        drawingCanvas.setCurrentState(circleTool);
      }
    });
    filledCircleButton.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent arg0) {
        mouseListener.setCurrentState(filledCircleTool);
        drawingCanvas.setCurrentState(filledCircleTool);
      }
    });
    
    drawingCanvas.addMouseListener(mouseListener);
    drawingCanvas.addMouseMotionListener(mouseListener);
//    drawingCanvas.addMouseListener(new MouseListener() {
//      
//      @Override
//      public void mouseReleased(MouseEvent e) {
//        currentState.mouseReleased(e);
//        
//      }
//      
//      @Override
//      public void mousePressed(MouseEvent e) {
//        currentState.mousePressed(e);
//        
//      }
//      
//      @Override
//      public void mouseExited(MouseEvent e) {
//      }
//      
//      @Override
//      public void mouseEntered(MouseEvent e) {
//      }
//      
//      @Override
//      public void mouseClicked(MouseEvent e) {
//        currentState.mouseClicked(e);
//      }
//    });
//    
//    drawingCanvas.addMouseMotionListener(new MouseMotionListener() {
//      
//      @Override
//      public void mouseMoved(MouseEvent e) {
//        currentState.mouseMoved(e);
//        
//      }
//      
//      @Override
//      public void mouseDragged(MouseEvent e) {
//        currentState.mouseDragged(e);
//      }
//    });
    
    getContentPane().add(drawingCanvas);
  }

  private void createDrawingCanvas() {
    

  }

  /**
   * Creates toolbar and status bar.
   */
  private void createToolbar() {

  }

  /**
   * Creates status bar containing information about colors chosen in RGB format.
   * 
   * @param colorArea1 first color area
   * @param colorArea2 second color area
   */
  private void createStatusBar(JColorArea colorArea1, JColorArea colorArea2) {
    JLabel statusBar = new JColorInfo(colorArea1, colorArea2);
    this.getContentPane().add(statusBar, BorderLayout.SOUTH);
  }

  private void createMenu() {
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    menuBar.add(fileMenu);
    this.setJMenuBar(menuBar);
    // fileMenu.add(new JMenuItem("new", flp, newDocumentAction));
    // fileMenu.add(new JMenuItem("open", flp, openDocumentAction));
    // fileMenu.add(new JMenuItem("save", flp, saveDocumentAction));
    // fileMenu.add(new JMenuItem("saveAs", flp, saveDocumentAsAction));
    // fileMenu.add(new JMenuItem("charInfo", flp, statisticalInfoAction));

  }
}
