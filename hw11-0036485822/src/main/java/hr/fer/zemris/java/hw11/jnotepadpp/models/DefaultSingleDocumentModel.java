package hr.fer.zemris.java.hw11.jnotepadpp.models;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadpp.listeners.SingleDocumentListener;

/**
 * Implements single document model which has reference to one text area in
 * which the document is stored. Has information about document's file path and
 * flag which indicates if the document has been modified. Also stores list of
 * listeners which will be notified when the document is modified.
 * 
 * @author tin
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

  /**
   * Path to file.
   */
  private Path filePath;

  /**
   * Reference to text area in which user can modify this document.
   */
  private JTextArea textArea;

  /**
   * Flag which indicates if document in text area is modified.
   */
  private boolean modified;

  /**
   * List of listeners which will be informed when document is modified.
   */
  private List<SingleDocumentListener> listeners;

  /**
   * Constructor.
   * 
   * @param filePath file path to be set
   * @param text document content
   */
  public DefaultSingleDocumentModel(Path filePath, String text) {
    this.filePath = filePath;
    textArea = new JTextArea(text);
    modified = false;
    listeners = new ArrayList<>();
  }

  @Override
  public JTextArea getTextComponent() {
    return textArea;
  }

  @Override
  public Path getFilePath() {
    return filePath;
  }

  @Override
  public void setFilePath(Path path) {
    if(path == null) {
      throw new NullPointerException("Path can not be null.");
    }
    if(!filePath.equals(path)) {
      filePath = path;
      notifyDocumentListeners(l -> {
        l.documentFilePathUpdated(this);
        return null;
      });
    } else {
      filePath = path;
    }
  }

  @Override
  public boolean isModified() {
    return modified;
  }

  @Override
  public void setModified(boolean modified) {
    if(this.modified != modified) {
      this.modified = modified;
      notifyDocumentListeners((l) -> {
        l.documentModifyStatusUpdated(this);
        return null;
      });
    } else {
      this.modified = modified;
    }

  }

  @Override
  public void addSingleDocumentListener(SingleDocumentListener l) {
    if(l == null) {
      throw new NullPointerException("Can not add null listener.");
    }
    listeners.add(l);
  }

  @Override
  public void removeSingleDocumentListener(SingleDocumentListener l) {
    listeners.remove(l);
  }

  /**
   * Notifies all listeners whose references are stored in listeners list about
   * specific change.
   * 
   * @param f parameter which determines about what the listeners will be notified
   */
  private void notifyDocumentListeners(Function<SingleDocumentListener, Void> f) {
    for (SingleDocumentListener listener : listeners) {
      f.apply(listener);
    }
  }

}
