package hr.fer.zemris.java.hw11.jnotepadpp.models;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.zemris.java.hw11.jnotepadpp.listeners.MultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.listeners.SingleDocumentListener;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * List of all opened documents. Each document is stored in one
   * {@link SingleDocumentModel} object.
   */
  private List<SingleDocumentModel> documents;

  /**
   * Reference to currently opened document.
   */
  private SingleDocumentModel currentDocument;

  /**
   * List of listeners which will be informed when documents are added or removed.
   */
  private List<MultipleDocumentListener> listeners;

  ImageIcon notSavedIcon;
  ImageIcon savedIcon;

  /**
   * Constructor.
   * 
   * @param notSavedIcon icon which will be displayed next to modified files
   * @param savedIcon icon which will be displayed next to unmodified files
   */
  public DefaultMultipleDocumentModel(ImageIcon notSavedIcon, ImageIcon savedIcon) {
    documents = new ArrayList<>();
    listeners = new ArrayList<>();
    this.notSavedIcon = notSavedIcon;
    this.savedIcon = savedIcon;
    this.addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent arg0) {
        SingleDocumentModel previousModel = currentDocument;
        if(getSelectedIndex() == -1) {
          currentDocument = null;
          return;
        }
        currentDocument = documents.get(getSelectedIndex());
        notifyAllDocumentListeners(l -> {
          l.currentDocumentChanged(previousModel, currentDocument);
          return null;
        });
      }
    });
  }

  @Override
  public Iterator<SingleDocumentModel> iterator() {
    return documents.iterator();
  }

  @Override
  public SingleDocumentModel createNewDocument() {
    return addNewTab(new DefaultSingleDocumentModel(null, ""));
  }

  @Override
  public SingleDocumentModel getCurrentDocument() {
    return currentDocument;
  }

  @Override
  public SingleDocumentModel loadDocument(Path path) {
    int i = getDocumentIndex(path);
    if(i != -1) {
      setSelectedIndex(i);
      return documents.get(i);
    }

    if(!Files.isReadable(path)) {
      throw new IllegalArgumentException("File " + path.toString() + " does not exist.");
    }

    byte[] content;
    try {
      content = Files.readAllBytes(path);
    } catch (Exception ex) {
      throw new IllegalArgumentException("Error while reading file " + path.toString());
    }

    String text = new String(content, StandardCharsets.UTF_8);
    
    return addNewTab(new DefaultSingleDocumentModel(path, text));
  }

  @Override
  public void saveDocument(SingleDocumentModel model, Path newPath) {
    if(!model.isModified()) {
      return;
    }

    byte[] data = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);

    try {
      Files.write(newPath, data, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
    } catch (Exception ex) {
      throw new IllegalArgumentException("Problem with writing to file " + newPath.toString());
    }
    
    model.setModified(false);
  }

  @Override
  public void closeDocument(SingleDocumentModel model) {
    int i = documents.indexOf(model);
    if(i != -1) {
      documents.remove(model);
      this.remove(i);
      if(documents.size() > 0 && getSelectedIndex() != -1) {
        currentDocument = documents.get(getSelectedIndex());
      } else {
        currentDocument = null;
      }
    }

    notifyAllDocumentListeners(l -> {
      l.documentRemoved(model);
      return null;
    });
  }

  @Override
  public void addMultipleDocumentListener(MultipleDocumentListener l) {
    if(l == null) {
      throw new NullPointerException("Can not add null listener.");
    }
    listeners.add(l);
  }

  @Override
  public void removeMultipleDocumentListener(MultipleDocumentListener l) {
    listeners.remove(l);
  }

  @Override
  public int getNumberOfDocuments() {
    return documents.size();
  }

  @Override
  public SingleDocumentModel getDocument(int index) {
    return documents.get(index);
  }

  /**
   * Notifies all listeners whose references are stored in listeners list about
   * specific change.
   * 
   * @param f parameter which determines about what the listeners will be notified
   */
  private void notifyAllDocumentListeners(Function<MultipleDocumentListener, Void> f) {
    for (MultipleDocumentListener listener : listeners) {
      f.apply(listener);
    }
  }

  /**
   * Checks if the list documents already contains given document model. Check is
   * performed by comparing document path.
   * 
   * @param path path which will be used to find existing document
   * @return index of model in documents list, -1 if it does not exist
   */
  private int getDocumentIndex(Path path) {
    for (int i = 0, n = documents.size(); i < n; i++) {
      if(documents.get(i).getFilePath() != null && documents.get(i).getFilePath().equals(path)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Creates new tab in this tabbed pane and returns {@link SingleDocumentModel}
   * object which was assigned to created tab. Default name for tab is
   * Untitled.txt, but is changed to file name if it exists. Adds listener to
   * added model.
   * 
   * @param model model which will be added
   * @return reference to model assigned to tab
   */
  private SingleDocumentModel addNewTab(SingleDocumentModel model) {
    SingleDocumentModel previousModel = currentDocument;
    currentDocument = model;
    documents.add(currentDocument);
    String tabTitle = "Untitled.txt";
    if(model.getFilePath() != null) {
      tabTitle = model.getFilePath().getFileName().toString();
    }
    if(currentDocument.getFilePath() != null) {
      this.addTab(tabTitle, savedIcon, new JScrollPane(currentDocument.getTextComponent()), currentDocument.getFilePath().toString());
    } else {
      this.addTab(tabTitle, savedIcon, new JScrollPane(currentDocument.getTextComponent()));
    }
    currentDocument.addSingleDocumentListener(new SingleDocumentListener() {
      
      @Override
      public void documentModifyStatusUpdated(SingleDocumentModel model) {
        if(model.isModified()) {
          setIconAt(documents.indexOf(model), notSavedIcon);
        } else {
          setIconAt(documents.indexOf(model), savedIcon);
        }
      }
      
      @Override
      public void documentFilePathUpdated(SingleDocumentModel model) {
        setTitleAt(documents.indexOf(model), model.getFilePath().getFileName().toString());
        if(model.getFilePath() != null) {
          setToolTipTextAt(documents.indexOf(model), model.getFilePath().toString());
        }        
      }
    });
    setSelectedIndex(documents.size() - 1);
    notifyAllDocumentListeners(l -> {
      l.documentAdded(currentDocument);
      l.currentDocumentChanged(previousModel, currentDocument);
      return null;
    });
    return currentDocument;
  }

}
