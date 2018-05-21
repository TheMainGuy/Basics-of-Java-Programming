package hr.fer.zemris.java.hw11.jnotepadpp.models;

import java.nio.file.Path;

import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadpp.listeners.SingleDocumentListener;

/**
 * Defines method for single document model which represents one document.
 * Document model must store file path, document content, modified flag and list
 * of listeners which must be informed about change when modified flag is set to
 * true.
 * 
 * @author tin
 *
 */
public interface SingleDocumentModel {
  /**
   * Returns reference to {@link JTextArea} object in which the document content
   * is stored.
   * 
   * @return {@link JTextArea} object in which the document content is stored
   */
  JTextArea getTextComponent();

  /**
   * Returns document's file path.
   * 
   * @return file path
   */
  Path getFilePath();

  /**
   * Sets new file path to document.
   * 
   * @param path file path to be set
   */
  void setFilePath(Path path);

  /**
   * Checks if the document was modified. Returns <code>true</code> if it was,
   * <code>false</code> if it was not.
   * 
   * @return <code>true</code> if the document was modified, <code>false</code> if
   *         it was not.
   */
  boolean isModified();

  /**
   * Sets the modified flag.
   * 
   * @param modified value to which the modified flag will be set
   */
  void setModified(boolean modified);

  /**
   * Adds {@link SingleDocumentListener} to list of listeners.
   * 
   * @param l listener which will be added
   */
  void addSingleDocumentListener(SingleDocumentListener l);

  /**
   * Removes {@link SingleDocumentListener} from list of listeners.
   * 
   * @param l listener which will be removed
   */
  void removeSingleDocumentListener(SingleDocumentListener l);
}
