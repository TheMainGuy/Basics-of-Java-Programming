package hr.fer.zemris.java.hw11.jnotepadpp.models;

import java.nio.file.Path;

import hr.fer.zemris.java.hw11.jnotepadpp.listeners.MultipleDocumentListener;

/**
 * Defines methods for managing tabs. Classes implementing this interface should
 * extend JTabbedPane in order to fully benefit from implementing this
 * interface.
 *
 * @author tin
 *
 */

public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
  /**
   * Creates new empty document, opens new tab for it and returns refernce to
   * created document model.
   * 
   * @return reference to created document model
   */
  SingleDocumentModel createNewDocument();

  /**
   * Returns current document model. Current Document is the one which is
   * displayed to user.
   * 
   * @return current document model
   */
  SingleDocumentModel getCurrentDocument();

  /**
   * Loads existing document, opens new tab for it and returns reference to
   * created document model.
   * 
   * @param path path from document is loaded.
   * @return reference to created document model
   */
  SingleDocumentModel loadDocument(Path path);

  /**
   * Saves document content to given path. If a file already exists with given
   * path, asks user to overwrite it.
   * 
   * @param model document model whose content will be saved
   * @param newPath path to which content will be saved
   */
  void saveDocument(SingleDocumentModel model, Path newPath);

  /**
   * Closes target document by removing its tab and reference to it from list of
   * documents.
   * 
   * @param model model which will be removed
   */
  void closeDocument(SingleDocumentModel model);

  /**
   * Adds {@link MultipleDocumentListener} to list of listeners.
   * 
   * @param l listener which will be added
   */
  void addMultipleDocumentListener(MultipleDocumentListener l);

  /**
   * Removes {@link MultipleDocumentListener} from list of listeners.
   * 
   * @param l listener which will be removed
   */
  void removeMultipleDocumentListener(MultipleDocumentListener l);

  /**
   * Returns number of documents currently stored in this model.
   * 
   * @return number of documents currently stored in this model.
   */
  int getNumberOfDocuments();

  /**
   * Returns {@link SingleDocumentModel} from list of documents at given index.
   * 
   * @param index index which will be used to get document model 
   * @return document model at given index
   */
  SingleDocumentModel getDocument(int index);
}
