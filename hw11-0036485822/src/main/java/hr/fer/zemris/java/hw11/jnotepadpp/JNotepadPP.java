package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.zemris.java.hw11.jnotepadpp.listeners.MultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.listeners.SingleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.models.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;

/**
 * Implements text editor JNotepad++.
 * 
 * @author tin
 *
 */
public class JNotepadPP extends JFrame {
  private DefaultMultipleDocumentModel model;
  private final Clock clock = new Clock();
  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      new JNotepadPP();
    });
  }

  /**
   * Constructor.
   */
  public JNotepadPP() {
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    setLocation(0, 0);
    setSize(600, 600);
    setVisible(true);
    setTitle("JNotepad++");
    this.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent arg0) {
        clock.stop();
        exitAction.actionPerformed(new ActionEvent(this, 1, "dummy"));
      }
    });

    initGUI();
  }

  /**
   * Initializes GUI elements.
   */
  private void initGUI() {
    this.getContentPane().setLayout(new BorderLayout());
    try {
      model = new DefaultMultipleDocumentModel(getImageIcon("icons/icon_red.png"),
          getImageIcon("icons/icon_green.png"));
    } catch (IOException e) {
      e.getMessage();
      System.out.println("Unable to initialize GUI. Exiting...");
      System.exit(1);
    }

    this.getContentPane().add(model, BorderLayout.CENTER);
    model.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        if(model.getSelectedIndex() == -1) {
          setTitle("JNotepad++");
          return;
        }
        Path path = model.getDocument(model.getSelectedIndex()).getFilePath();
        if(path == null) {
          return;
        }
        setTitle(path.toString() + "- JNotepad++");

      }
    });

    createStatusBar();
    createActions();
    createMenus();
    createToolbars();

  }

  private void createStatusBar() {
    JPanel statusBar = new JPanel();
    this.getContentPane().add(statusBar, BorderLayout.SOUTH);
    statusBar.setLayout(new BorderLayout());
    StatusBarInfo statusBarInfo = new StatusBarInfo();
    JLabel lengthLabel = new JLabel("length :");
    statusBar.add(lengthLabel, BorderLayout.WEST);
    JLabel caretInfoLabel = new JLabel("Ln: Col: Sel:");
    statusBar.add(clock);
    statusBar.add(caretInfoLabel, BorderLayout.EAST);
    SingleDocumentListener singleDocumentListener = new SingleDocumentListener() {

      @Override
      public void documentModifyStatusUpdated(SingleDocumentModel model) {
        statusBarInfo.setLength(model.getTextComponent().getText().length());
      }

      @Override
      public void documentFilePathUpdated(SingleDocumentModel model) {
      }
    };

    CaretListener caretListener = new CaretListener() {

      @Override
      public void caretUpdate(CaretEvent caret) {
        String text = ((JTextArea) caret.getSource()).getText();
        int length = text.length();
        int dotPosition = caret.getDot();
        int markPosition = caret.getMark();
        lengthLabel.setText("length: " + length);
        int lineCounter = 1;
        int columnCounter = 0;
        for (int i = 0; i < dotPosition; i++) {
          columnCounter++;
          if(text.charAt(i) == '\n') {
            lineCounter++;
            columnCounter = 0;
          }
        }
        caretInfoLabel
            .setText("Ln:" + lineCounter + " Col:" + columnCounter + " Sel:" + Math.abs(dotPosition - markPosition));
      }
    };

    model.addMultipleDocumentListener(new MultipleDocumentListener() {

      @Override
      public void documentRemoved(SingleDocumentModel model) {

      }

      @Override
      public void documentAdded(SingleDocumentModel model) {

      }

      @Override
      public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
        if(previousModel != null) {
          previousModel.removeSingleDocumentListener(singleDocumentListener);
          previousModel.getTextComponent().removeCaretListener(caretListener);
        }
        if(currentModel != null) {
          currentModel.addSingleDocumentListener(singleDocumentListener);
          currentModel.getTextComponent().addCaretListener(caretListener);
          lengthLabel.setText("length: " + currentModel.getTextComponent().getText().length());
        }
      }
    });

  }

  /**
   * Creates tool bar and adds buttons to it.
   */
  private void createToolbars() {
    JToolBar toolBar = new JToolBar("Tools");
    toolBar.setFloatable(true);

    toolBar.add(new JButton(newDocumentAction));
    toolBar.add(new JButton(openDocumentAction));
    toolBar.add(new JButton(saveDocumentAction));
    toolBar.add(new JButton(saveDocumentAsAction));
    toolBar.add(new JButton(statisticalInfoAction));
    toolBar.addSeparator();
    toolBar.add(new JButton(closeDocumentAction));
    toolBar.add(new JButton(exitAction));

    this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
  }

  /**
   * Creates menus and adds menu item for to them.
   */
  private void createMenus() {
    JMenuBar menuBar = new JMenuBar();

    JMenu fileMenu = new JMenu("File");
    menuBar.add(fileMenu);

    fileMenu.add(new JMenuItem(newDocumentAction));
    fileMenu.add(new JMenuItem(openDocumentAction));
    fileMenu.add(new JMenuItem(saveDocumentAction));
    fileMenu.add(new JMenuItem(saveDocumentAsAction));
    fileMenu.add(new JMenuItem(statisticalInfoAction));
    fileMenu.addSeparator();
    fileMenu.add(new JMenuItem(closeDocumentAction));
    fileMenu.add(new JMenuItem(exitAction));

    JMenu editMenu = new JMenu("Edit");
    menuBar.add(editMenu);

    this.setJMenuBar(menuBar);

  }

  /**
   * Defines actions by defining their name, accelerator key, mnemonic key and
   * short description.
   */
  private void createActions() {
    openDocumentAction.putValue(Action.NAME, "Open");
    openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
    openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
    openDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to open existing file from disk.");

    saveDocumentAction.putValue(Action.NAME, "Save");
    saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
    saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
    saveDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to save existing file to disk.");

    newDocumentAction.putValue(Action.NAME, "New");
    newDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
    newDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
    newDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to create new file.");

    saveDocumentAsAction.putValue(Action.NAME, "Save As");
    saveDocumentAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control D"));
    saveDocumentAsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
    saveDocumentAsAction.putValue(Action.SHORT_DESCRIPTION, "Used to save existing file to disk as new file.");

    closeDocumentAction.putValue(Action.NAME, "Close");
    closeDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
    closeDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
    closeDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to close current document.");

    statisticalInfoAction.putValue(Action.NAME, "Char Info");
    statisticalInfoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
    statisticalInfoAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
    statisticalInfoAction.putValue(Action.SHORT_DESCRIPTION, "Used to print statistical info.");

    exitAction.putValue(Action.NAME, "Exit");
    exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ESCAPE"));
    exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_ESCAPE);
    exitAction.putValue(Action.SHORT_DESCRIPTION, "Used to exit JNotepad++.");
  }

  /**
   * Defines open document action. Action will invoke {@link JFileChooser} to
   * allow user to specify file path. If file is already opened, view will be
   * switched to that file instead of opening file.
   */
  private final Action openDocumentAction = new AbstractAction() {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent arg0) {
      JFileChooser fc = new JFileChooser();
      fc.setDialogTitle("Open file");
      if(fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
        return;
      }

      Path filePath = fc.getSelectedFile().toPath();
      try {
        model.loadDocument(filePath);
      } catch (IllegalArgumentException e) {
        JOptionPane.showMessageDialog(JNotepadPP.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }
    }
  };

  /**
   * Defines new document action. Creates new document model and switches view to
   * it.
   */
  private final Action newDocumentAction = new AbstractAction() {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent arg0) {
      model.createNewDocument();
    }
  };

  /**
   * Defines save document action. Action will save document to its file path. If
   * document does not have file path set, action will invoke {@link JFileChooser}
   * to allow user to specify save path.
   * 
   */
  private final Action saveDocumentAction = new AbstractAction() {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent arg0) {
      try {
        if(model.getNumberOfDocuments() == 0) {
          return;
        }
        if(model.getCurrentDocument().getFilePath() == null) {
          saveDocumentAsAction.actionPerformed(arg0);
          return;
        }
        model.saveDocument(model.getCurrentDocument(), model.getCurrentDocument().getFilePath());
      } catch (IllegalArgumentException e) {
        JOptionPane.showMessageDialog(JNotepadPP.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }

      model.getCurrentDocument().setModified(false);
    }
  };

  /**
   * Defines save document as action. Action will invoke {@link JFileChooser} to
   * allow user to specify save path and save document to specified save path.
   */
  private final Action saveDocumentAsAction = new AbstractAction() {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent arg0) {
      try {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Save file");
        fc.setDialogType(JFileChooser.SAVE_DIALOG);
        if(fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
          return;
        }
        model.getCurrentDocument().setFilePath(fc.getSelectedFile().toPath());

        model.saveDocument(model.getCurrentDocument(), model.getCurrentDocument().getFilePath());
      } catch (IllegalArgumentException e) {
        JOptionPane.showMessageDialog(JNotepadPP.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }

      model.getCurrentDocument().setModified(false);
    }
  };

  /**
   * Defines close document action. Closes currently viewed document.
   */
  private final Action closeDocumentAction = new AbstractAction() {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent arg0) {
      if(model.getNumberOfDocuments() == 0) {
        return;
      }
      if(model.getCurrentDocument().isModified()) {
        int optionChosen = JOptionPane.showConfirmDialog(JNotepadPP.this,
            "File has been modified. Do you want to save file before closing?", "File modified",
            JOptionPane.YES_NO_OPTION);
        if(optionChosen == JOptionPane.YES_OPTION) {
          saveDocumentAction.actionPerformed(arg0);
        }
      }
      model.closeDocument(model.getCurrentDocument());
    }
  };

  /**
   * Defines statistical information action. Counts the number of charasters found
   * in document, number of non-blank characters and number of lines that the
   * document contains.
   */
  private final Action statisticalInfoAction = new AbstractAction() {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent arg0) {
      int numberOfCharacters = 0;
      int numberOfNonBlankCharacters = 0;
      int numberOfLines = 1;
      String text = model.getCurrentDocument().getTextComponent().getText();
      numberOfCharacters = text.length();
      for (int i = 0; i < numberOfCharacters; i++) {
        char c = text.charAt(i);
        if(c == '\n') {
          numberOfLines++;
        } else {
          if(!Character.isSpaceChar(c)) {
            numberOfNonBlankCharacters++;
          }
        }
      }

      JOptionPane
          .showMessageDialog(JNotepadPP.this,
              "Your document has " + numberOfCharacters + " characters, " + numberOfNonBlankCharacters
                  + " non-blank characters and " + numberOfLines + " lines.",
              "Statistical info", JOptionPane.OK_OPTION);
    }
  };

  /**
   * Defines exit action. Closes all JNotepad++ tabs one by one and then exits.
   */
  private final Action exitAction = new AbstractAction() {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent arg0) {
      for (int i = 0, n = model.getNumberOfDocuments(); i < n; i++) {
        closeDocumentAction.actionPerformed(arg0);
      }
      System.exit(0);

    }
  };

  /**
   * Helper method that reads icon from specified relative path and returns it in
   * form of {@link ImageIcon} object.
   * 
   * @param relativePath relative path to icon
   * @return icon stored in {@link ImageIcon} object
   * @throws IllegalArgumentException if specified file does not exist
   * @throws IOException if there is problem with reading from specified file
   */
  private ImageIcon getImageIcon(String relativePath) throws IOException {
    InputStream is = this.getClass().getResourceAsStream(relativePath);
    if(is == null) {
      throw new IllegalArgumentException("Specified file does not exist");
    }
    byte[] bytes = is.readAllBytes();
    is.close();
    return new ImageIcon(bytes);
  }

}
