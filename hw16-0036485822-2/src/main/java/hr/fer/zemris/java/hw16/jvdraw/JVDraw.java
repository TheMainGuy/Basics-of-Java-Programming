package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.hw16.jvdraw.actions.ExitAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.ExportAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.OpenAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.SaveAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.SaveAsAction;
import hr.fer.zemris.java.hw16.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.components.JColorInfo;
import hr.fer.zemris.java.hw16.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.listeners.CustomMouseListener;
import hr.fer.zemris.java.hw16.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.listeners.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelImpl;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingObjectListModel;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.objects.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.LineTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.Tool;

/**
 * Creates simple painting program which allows user to draw lines, circles and
 * filled circles. Also renders list of all drawn objects, which can be used to
 * delete objects using DEL button or change the order of drawing them by using
 * + and - buttons. Double-clicking any object on the list opens editor for that
 * object which allows changing object's coordinates, color/s and radius if the
 * object is a circle.
 * 
 * User can choose color for drawing and filling objects.
 * 
 * Under File menu, user can save, save as and open drawings made in JVDraw
 * using .jvd files. Also, Export action allows user to save drawing in png, jpg
 * or gif format.
 * 
 * @author tin
 *
 */
public class JVDraw extends JFrame {
  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Currently selected tool for drawing. LineTool is selected by default.
   */
  private Tool currentState;

  /**
   * Reference to {@link DrawingModel} object.
   */
  private DrawingModel drawingModel;

  /**
   * Flag which keeps track if the drawing is edited since last save or open.
   */
  boolean isEdited = false;

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

  /**
   * Constructor.
   */
  public JVDraw() {
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setLocation(0, 0);
    setSize(900, 600);
    setVisible(true);
    setTitle("JVDraw");

    initGUI();
  }

  /**
   * @return the isEdited
   */
  public boolean isEdited() {
    return isEdited;
  }

  /**
   * @param isEdited the isEdited to set
   */
  public void setEdited(boolean isEdited) {
    this.isEdited = isEdited;
  }

  /**
   * Initializes GUI elements.
   */
  private void initGUI() {
    this.getContentPane().setLayout(new BorderLayout());

    JColorArea colorArea1 = new JColorArea(Color.BLACK);
    JColorArea colorArea2 = new JColorArea(Color.WHITE);
    JToolBar toolBar = new JToolBar("Tools");
    toolBar.setFloatable(true);
    toolBar.add(colorArea1);
    toolBar.addSeparator();
    toolBar.add(colorArea2);
    this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
    createStatusBar(colorArea1, colorArea2);

    drawingModel = new DrawingModelImpl();
    JDrawingCanvas drawingCanvas = new JDrawingCanvas(drawingModel);
    drawingModel.addDrawingModelListener(drawingCanvas);
    LineTool lineTool = new LineTool(drawingModel, (IColorProvider) colorArea1, drawingCanvas);
    CircleTool circleTool = new CircleTool(drawingModel, colorArea1, drawingCanvas);
    FilledCircleTool filledCircleTool = new FilledCircleTool(drawingModel, colorArea1, drawingCanvas, colorArea2);
    currentState = lineTool;
    drawingCanvas.setCurrentState(currentState);
    JButton lineButton = new JButton("Line");
    JButton circleButton = new JButton("Circle");
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

    getContentPane().add(drawingCanvas);

    DrawingObjectListModel listModel = new DrawingObjectListModel(drawingModel);
    JList<GeometricalObject> list = new JList<>(listModel);

    list.addKeyListener(new KeyListener() {

      @Override
      public void keyTyped(KeyEvent e) {
      }

      @Override
      public void keyReleased(KeyEvent e) {
      }

      @Override
      public void keyPressed(KeyEvent e) {
        int index = list.getSelectedIndex();
        if(index < 0 || index > drawingModel.getSize()) {
          return;
        }
        if(e.getKeyCode() == KeyEvent.VK_DELETE) {
          drawingModel.remove(drawingModel.getObject(index));
        } else if(e.getKeyCode() == KeyEvent.VK_PLUS) {
          if(index == 0) {
            return;
          }
          drawingModel.changeOrder(drawingModel.getObject(index), -1);
          list.setSelectedIndex(index - 1);
        } else if(e.getKeyCode() == KeyEvent.VK_MINUS) {
          if(index == drawingModel.getSize() - 1) {
            return;
          }
          drawingModel.changeOrder(drawingModel.getObject(index), 1);
          list.setSelectedIndex(index + 1);
        }

      }
    });

    list.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() == 2) {
          GeometricalObject clicked = drawingModel.getObject(list.locationToIndex(e.getPoint()));
          GeometricalObjectEditor editor = clicked.createGeometricalObjectEditor();
          if(JOptionPane.showConfirmDialog(null, editor, "Edit",
              JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
              editor.checkEditing();
              editor.acceptEditing();
            } catch (Exception ex) {
              JOptionPane.showMessageDialog(null, ex.getMessage());
            }
          }
        }
      }
    });
    getContentPane().add(new JScrollPane(list), BorderLayout.EAST);

    drawingModel.addDrawingModelListener(new DrawingModelListener() {

      @Override
      public void objectsRemoved(DrawingModel source, int index0, int index1) {
        isEdited = true;
      }

      @Override
      public void objectsChanged(DrawingModel source, int index0, int index1) {
        isEdited = true;
      }

      @Override
      public void objectsAdded(DrawingModel source, int index0, int index1) {
        isEdited = true;
      }
    });
    createMenu();
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

  /**
   * Creates menu and its content. Also initializes actions which the menu will
   * provide and adds them to menu.
   */
  private void createMenu() {
    SaveAsAction saveAsAction = new SaveAsAction(drawingModel, this);
    saveAsAction.putValue(Action.NAME, "Save As");
    SaveAction saveAction = new SaveAction(this, drawingModel, saveAsAction);
    saveAction.putValue(Action.NAME, "Save");
    OpenAction openAction = new OpenAction(drawingModel, this);
    openAction.putValue(Action.NAME, "Open");
    ExportAction exportPngAction = new ExportAction(drawingModel, this, "png");
    exportPngAction.putValue(Action.NAME, "Export png");
    ExportAction exportJpgAction = new ExportAction(drawingModel, this, "jpg");
    exportJpgAction.putValue(Action.NAME, "Export jpg");
    ExportAction exportGifAction = new ExportAction(drawingModel, this, "gif");
    exportGifAction.putValue(Action.NAME, "Export gif");
    ExitAction exitAction = new ExitAction(this, saveAction);
    exitAction.putValue(Action.NAME, "Exit");

    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    menuBar.add(fileMenu);
    this.setJMenuBar(menuBar);
    fileMenu.add(new JMenuItem(saveAction));
    fileMenu.add(new JMenuItem(saveAsAction));
    fileMenu.add(new JMenuItem(openAction));
    fileMenu.add(new JMenuItem(exportPngAction));
    fileMenu.add(new JMenuItem(exportJpgAction));
    fileMenu.add(new JMenuItem(exportGifAction));
    fileMenu.add(new JMenuItem(exitAction));
  }
}
