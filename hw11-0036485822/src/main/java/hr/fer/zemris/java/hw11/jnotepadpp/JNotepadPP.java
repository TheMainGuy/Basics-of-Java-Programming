package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
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
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.listeners.MultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ljelements.LJButton;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ljelements.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ljelements.LJMenuItem;
import hr.fer.zemris.java.hw11.jnotepadpp.models.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;

/**
 * Implements text editor JNotepad++. It supports working with multiple tabs,
 * copy, cut, paste, switching letter casing, creating new documents, opening
 * existing documents, save action, save as action, character info, line sorting
 * and multiple language support.
 * 
 * @author tin
 *
 */
public class JNotepadPP extends JFrame {
  /**
   * Reference to model which works with tabs.
   */
  private DefaultMultipleDocumentModel model;

  /**
   * Clock object.
   */
  private final Clock clock = new Clock();

  /**
   * Localization provider object.
   */
  private FormLocalizationProvider flp;
  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Method which is called when program starts.
   * 
   * @param args command line arguments
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      LocalizationProvider.getInstance().setLanguage("en");
      new JNotepadPP();
    });
  }

  /**
   * Constructor.
   */
  public JNotepadPP() {
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    setLocation(0, 0);
    setSize(800, 600);
    setVisible(true);
    setTitle("JNotepad++");
    flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);

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

  /**
   * Creates status bar. Status bar shows length of current doocument, caret
   * coordinate and selection length and clock showing current date and time.
   */
  private void createStatusBar() {
    JPanel statusBar = new JPanel();
    this.getContentPane().add(statusBar, BorderLayout.SOUTH);
    statusBar.setLayout(new GridLayout(1, 2));
    JPanel leftSide = new JPanel();
    JPanel rightSide = new JPanel();
    leftSide.setLayout(new BorderLayout());
    rightSide.setLayout(new BorderLayout());
    statusBar.add(leftSide);
    statusBar.add(rightSide);
    JLabel lengthLabel = new JLabel("length :");
    leftSide.add(lengthLabel, BorderLayout.WEST);
    JLabel caretInfoLabel = new JLabel("Ln: Col: Sel:");
    rightSide.add(clock);
    rightSide.add(caretInfoLabel, BorderLayout.WEST);

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
          previousModel.getTextComponent().removeCaretListener(caretListener);
        }
        if(currentModel != null) {
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

    toolBar.add(new LJButton("new", flp, newDocumentAction));
    toolBar.add(new LJButton("open", flp, openDocumentAction));
    toolBar.add(new LJButton("save", flp, saveDocumentAction));
    toolBar.add(new LJButton("saveAs", flp, saveDocumentAsAction));
    toolBar.add(new LJButton("charInfo", flp, statisticalInfoAction));
    toolBar.addSeparator();

    toolBar.add(new LJButton("copy", flp, copyAction));
    toolBar.add(new LJButton("cut", flp, cutAction));
    toolBar.add(new LJButton("paste", flp, pasteAction));
    toolBar.addSeparator();

    toolBar.add(new LJButton("close", flp, closeDocumentAction));
    toolBar.add(new LJButton("exit", flp, exitAction));

    this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
  }

  /**
   * Creates menus and adds menu items to them.
   */
  private void createMenus() {
    JMenuBar menuBar = new JMenuBar();

    JMenu fileMenu = new LJMenu("file", flp);
    menuBar.add(fileMenu);
    fileMenu.add(new LJMenuItem("new", flp, newDocumentAction));
    fileMenu.add(new LJMenuItem("open", flp, openDocumentAction));
    fileMenu.add(new LJMenuItem("save", flp, saveDocumentAction));
    fileMenu.add(new LJMenuItem("saveAs", flp, saveDocumentAsAction));
    fileMenu.add(new LJMenuItem("charInfo", flp, statisticalInfoAction));
    fileMenu.addSeparator();
    fileMenu.add(new LJMenuItem("close", flp, closeDocumentAction));
    fileMenu.add(new LJMenuItem("exit", flp, exitAction));

    JMenu editMenu = new LJMenu("edit", flp);
    menuBar.add(editMenu);
    editMenu.add(new LJMenuItem("copy", flp, copyAction));
    editMenu.add(new LJMenuItem("cut", flp, cutAction));
    editMenu.add(new LJMenuItem("paste", flp, pasteAction));
    editMenu.addSeparator();

    JMenu changeCaseMenu = new LJMenu("changeCase", flp);
    menuBar.add(changeCaseMenu);
    LJMenuItem toUppercase = new LJMenuItem("toUppercase", flp, toUppercaseAction);
    changeCaseMenu.add(toUppercase);
    LJMenuItem toLowercase = new LJMenuItem("toLowercase", flp, toLowercaseAction);
    changeCaseMenu.add(toLowercase);
    LJMenuItem invertCase = new LJMenuItem("invertCase", flp, invertCaseAction);
    changeCaseMenu.add(invertCase);
    toUppercase.setEnabled(false);
    toLowercase.setEnabled(false);
    invertCase.setEnabled(false);
    CaretListener caretListener = new CaretListener() {

      @Override
      public void caretUpdate(CaretEvent caret) {
        if(Math.abs(caret.getDot() - caret.getMark()) == 0) {
          toUppercase.setEnabled(false);
          toLowercase.setEnabled(false);
          invertCase.setEnabled(false);
        } else {
          toUppercase.setEnabled(true);
          toLowercase.setEnabled(true);
          invertCase.setEnabled(true);
        }
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
          previousModel.getTextComponent().removeCaretListener(caretListener);
        }
        if(currentModel != null) {
          currentModel.getTextComponent().addCaretListener(caretListener);
        }
      }
    });

    JMenu sortMenu = new LJMenu("sort", flp);
    menuBar.add(sortMenu);
    sortMenu.add(new LJMenuItem("ascending", flp, sortAscendingAction));
    sortMenu.add(new LJMenuItem("descending", flp, sortDescendingAction));
    sortMenu.add(new LJMenuItem("unique", flp, uniqueAction));

    JMenu languageMenu = new LJMenu("language", flp);
    menuBar.add(languageMenu);
    languageMenu.add(new JMenuItem(enLanguageAction));
    languageMenu.add(new JMenuItem(hrLanguageAction));
    languageMenu.add(new JMenuItem(deLanguageAction));

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

    enLanguageAction.putValue(Action.NAME, "en");
    enLanguageAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
    enLanguageAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
    enLanguageAction.putValue(Action.SHORT_DESCRIPTION, "Used to change language to English.");

    deLanguageAction.putValue(Action.NAME, "de");
    deLanguageAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control R"));
    deLanguageAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
    deLanguageAction.putValue(Action.SHORT_DESCRIPTION, "Used to change language to German.");

    hrLanguageAction.putValue(Action.NAME, "hr");
    hrLanguageAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
    hrLanguageAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
    hrLanguageAction.putValue(Action.SHORT_DESCRIPTION, "Used to change language to Croatian.");

    copyAction.putValue(Action.NAME, "Copy");
    copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
    copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
    copyAction.putValue(Action.SHORT_DESCRIPTION, "Used to copy text to clipboard.");

    cutAction.putValue(Action.NAME, "Cut");
    cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
    cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
    cutAction.putValue(Action.SHORT_DESCRIPTION, "Used to cut text to clipboard.");

    pasteAction.putValue(Action.NAME, "Paste");
    pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
    pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
    pasteAction.putValue(Action.SHORT_DESCRIPTION, "Used to paste text from clipboard.");

    sortAscendingAction.putValue(Action.NAME, "Ascending");
    sortAscendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control J"));
    sortAscendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_J);
    sortAscendingAction.putValue(Action.SHORT_DESCRIPTION, "Used to sort lines in ascending order.");

    sortDescendingAction.putValue(Action.NAME, "Descending");
    sortDescendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control K"));
    sortDescendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_K);
    sortDescendingAction.putValue(Action.SHORT_DESCRIPTION, "Used to sort lines in descending order.");

    uniqueAction.putValue(Action.NAME, "Unique");
    uniqueAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
    uniqueAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
    uniqueAction.putValue(Action.SHORT_DESCRIPTION, "Used to remove duplicate lines from selection.");
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
   * Copies currently selected text.
   */
  private final Action copyAction = new AbstractAction() {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent arg0) {
      model.getCurrentDocument().getTextComponent().copy();
    }
  };

  /**
   * Cuts currently selected text.
   */
  private final Action cutAction = new AbstractAction() {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent arg0) {
      model.getCurrentDocument().getTextComponent().cut();
    }
  };

  /**
   * Pastes text from clipboard to where curet is currently located.
   */
  private final Action pasteAction = new AbstractAction() {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent arg0) {
      model.getCurrentDocument().getTextComponent().paste();
      ;
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
      if(model.getNumberOfDocuments() == 0) {
        return;
      }
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
   * Defines change language to English action. Changes language to English.
   */
  private final Action enLanguageAction = new AbstractAction() {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent arg0) {
      LocalizationProvider.getInstance().setLanguage("en");
    }
  };

  /**
   * Defines change language to German action. Changes language to German.
   */
  private final Action deLanguageAction = new AbstractAction() {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent arg0) {
      LocalizationProvider.getInstance().setLanguage("de");
    }
  };

  /**
   * Defines change language to Croatian action. Changes language to Croatian.
   */
  private final Action hrLanguageAction = new AbstractAction() {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent arg0) {
      LocalizationProvider.getInstance().setLanguage("hr");
    }
  };

  /**
   * Sets all marked letter to uppercase letters. Disabled if nothing is marked.
   */
  private Action toUppercaseAction = new AbstractAction() {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent arg0) {
      JTextArea editor = model.getCurrentDocument().getTextComponent();
      Document doc = editor.getDocument();
      int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
      int offset = 0;
      if(len != 0) {
        offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
      } else {
        len = doc.getLength();
      }

      try {
        String text = doc.getText(offset, len);
        text = changeCase(text);
        doc.remove(offset, len);
        doc.insertString(offset, text, null);
      } catch (BadLocationException e) {
        e.printStackTrace();
      }

    }

    private String changeCase(String text) {
      char[] znakovi = text.toCharArray();
      for (int i = 0, n = text.length(); i < n; i++) {
        char c = znakovi[i];
        if(Character.isLowerCase(c)) {
          znakovi[i] = Character.toUpperCase(c);
        }
      }
      return new String(znakovi);
    }
  };

  /**
   * Sets all marked letter to lowercase letters. Disabled if nothing is marked.
   */
  private Action toLowercaseAction = new AbstractAction() {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent arg0) {
      JTextArea editor = model.getCurrentDocument().getTextComponent();
      Document doc = editor.getDocument();
      int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
      int offset = 0;
      if(len != 0) {
        offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
      } else {
        len = doc.getLength();
      }

      try {
        String text = doc.getText(offset, len);
        text = changeCase(text);
        doc.remove(offset, len);
        doc.insertString(offset, text, null);
      } catch (BadLocationException e) {
        e.printStackTrace();
      }

    }

    private String changeCase(String text) {
      char[] znakovi = text.toCharArray();
      for (int i = 0, n = text.length(); i < n; i++) {
        char c = znakovi[i];
        if(Character.isUpperCase(c)) {
          znakovi[i] = Character.toLowerCase(c);
        }
      }
      return new String(znakovi);
    }
  };

  /**
   * Changes casing to all marked letters. Disabled if nothing is marked.
   */
  private Action invertCaseAction = new AbstractAction() {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent arg0) {
      JTextArea textArea = model.getCurrentDocument().getTextComponent();
      Document doc = textArea.getDocument();
      int len = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
      int offset = 0;
      if(len != 0) {
        offset = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
      } else {
        len = doc.getLength();
      }

      try {
        String text = doc.getText(offset, len);
        text = changeCase(text);
        doc.remove(offset, len);
        doc.insertString(offset, text, null);
      } catch (BadLocationException e) {
        e.printStackTrace();
      }

    }

    private String changeCase(String text) {
      char[] znakovi = text.toCharArray();
      for (int i = 0, n = text.length(); i < n; i++) {
        char c = znakovi[i];
        if(Character.isLowerCase(c)) {
          znakovi[i] = Character.toUpperCase(c);
        } else if(Character.isUpperCase(c)) {
          znakovi[i] = Character.toLowerCase(c);
        }
      }
      return new String(znakovi);
    }
  };

  /**
   * Sorts selected lines in ascending order. If any character is selected from
   * line, that line will be sorted.
   */
  private final Action sortAscendingAction = new AbstractAction() {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent arg0) {
      if(model.getNumberOfDocuments() == 0) {
        return;
      }
      JTextArea textArea = model.getCurrentDocument().getTextComponent();
      String text = textArea.getText();
      Caret caret = textArea.getCaret();
      int startPosition = Math.min(caret.getDot(), caret.getMark());
      int endPosition = Math.max(caret.getDot(), caret.getMark());
      int startLine = 0;
      int endLine = 0;
      for (int i = 0; i < endPosition; i++) {
        if(text.charAt(i) == '\n') {
          if(i < startPosition) {
            startLine++;
          }
          endLine++;
        }
      }

      String sortedText = sortLines(text, startLine, endLine, true);
      textArea.setText(sortedText);

    }
  };

  /**
   * Sorts selected lines in descending order. If any character is selected from
   * line, that line will be sorted.
   */
  private final Action sortDescendingAction = new AbstractAction() {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent arg0) {
      if(model.getNumberOfDocuments() == 0) {
        return;
      }
      JTextArea textArea = model.getCurrentDocument().getTextComponent();
      String text = textArea.getText();
      Caret caret = textArea.getCaret();
      int startPosition = Math.min(caret.getDot(), caret.getMark());
      int endPosition = Math.max(caret.getDot(), caret.getMark());
      int startLine = 0;
      int endLine = 0;
      for (int i = 0; i < endPosition; i++) {
        if(text.charAt(i) == '\n') {
          if(i < startPosition) {
            startLine++;
          }
          endLine++;
        }
      }

      String sortedText = sortLines(text, startLine, endLine, false);
      textArea.setText(sortedText);

    }
  };

  /**
   * Removes unique lines from selected lines. If any character is selected from
   * line, that line will be included in selection.
   */
  private Action uniqueAction = new AbstractAction() {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent arg0) {
      if(model.getNumberOfDocuments() == 0) {
        return;
      }
      JTextArea textArea = model.getCurrentDocument().getTextComponent();
      String text = textArea.getText();
      Caret caret = textArea.getCaret();
      int startPosition = Math.min(caret.getDot(), caret.getMark());
      int endPosition = Math.max(caret.getDot(), caret.getMark());
      int startLine = 0;
      int endLine = 0;
      for (int i = 0; i < endPosition; i++) {
        if(text.charAt(i) == '\n') {
          if(i < startPosition) {
            startLine++;
          }
          endLine++;
        }
      }
      String uniquedText = uniqueLines(text, startLine, endLine);
      textArea.setText(uniquedText);

    }

    private String uniqueLines(String text, int startLine, int endLine) {
      List<String> lines = new ArrayList<>();
      if(text.indexOf('\n') != -1) {
        lines.add(text.substring(0, text.indexOf('\n')));
      }
      for (int i = 0; i < text.length(); i++) {
        if(text.charAt(i) == '\n') {
          String textFrom = text.substring(i + 1);
          if(textFrom.indexOf('\n') == -1) {
            lines.add(textFrom);
          } else {
            lines.add(textFrom.substring(0, textFrom.indexOf('\n')));
          }
        }
      }

      Set<String> linesToUnique = new LinkedHashSet<>();
      for (int i = startLine; i <= endLine; i++) {
        linesToUnique.add(lines.get(i));
      }
      List<String> uniqueLines = new ArrayList<>();
      for (String line : linesToUnique) {
        uniqueLines.add(line);
      }

      List<String> finalLines = new ArrayList<>();
      int counter = 0;
      for (int i = 0, n = lines.size(); i < n; i++) {
        if(i >= startLine && i <= endLine) {
          if(counter >= uniqueLines.size()) {
            continue;
          }
          finalLines.add(uniqueLines.get(counter++));

        } else {
          finalLines.add(lines.get(i));
        }
      }

      StringBuilder stringBuilder = new StringBuilder();
      for (int i = 0, n = finalLines.size(); i < n; i++) {
        stringBuilder.append(finalLines.get(i)).append("\n");
      }
      return stringBuilder.toString().substring(0, stringBuilder.length() - 1);

    }
  };

  /**
   * Sorts lines in given text in ascending or descending order depending on
   * parameter ascending. Sorts line from startLine pararmeter to endLine
   * parameter.
   * 
   * @param text text to be sorted
   * @param startLine line from which sort starts
   * @param endLine line on which sort ends
   * @param ascending ascending sort is performed if <code>true</code>, descending
   *          otherwise
   * @return
   */
  private String sortLines(String text, int startLine, int endLine, boolean ascending) {
    List<String> lines = new ArrayList<>();
    if(text.indexOf('\n') != -1) {
      lines.add(text.substring(0, text.indexOf('\n')));
    }
    for (int i = 0; i < text.length(); i++) {
      if(text.charAt(i) == '\n') {
        String textFrom = text.substring(i + 1);
        if(textFrom.indexOf('\n') == -1) {
          lines.add(textFrom);
        } else {
          lines.add(textFrom.substring(0, textFrom.indexOf('\n')));
        }
      }
    }
    List<String> linesToSort = new ArrayList<>();
    for (int i = startLine; i <= endLine; i++) {
      linesToSort.add(lines.get(i));
    }

    Locale locale = new Locale(LocalizationProvider.getInstance().getString("locale"));
    Collator localeCollator = Collator.getInstance(locale);
    Collections.sort(linesToSort, localeCollator);
    for (int i = startLine; i <= endLine; i++) {
      int setIndex = ascending ? i : endLine - i + startLine;
      lines.set(setIndex, linesToSort.get(i - startLine));
    }

    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0, n = lines.size(); i < n; i++) {
      stringBuilder.append(lines.get(i)).append("\n");
    }
    return stringBuilder.toString().substring(0, stringBuilder.length() - 1);
  }

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
