package hr.fer.zemris.java.hw11.jnotepadpp.listeners;

import hr.fer.zemris.java.hw11.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;

/**
 * Defines methods for listener for {@link MultipleDocumentModel}. Each listener
 * will be informed when document changes, when new document is added or when a
 * document is removed.
 * 
 * @author tin
 *
 */
public interface MultipleDocumentListener {
  /**
   * Method which is called when currend document is changed.
   * 
   * @param previousModel current document before change
   * @param currentModel current document after change
   */
  void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

  /**
   * Method which is called when a new document is added.
   * 
   * @param model document model added
   */
  void documentAdded(SingleDocumentModel model);

  /**
   * Method which is called when a document is removed.
   * 
   * @param model document which is removed
   */
  void documentRemoved(SingleDocumentModel model);
}
