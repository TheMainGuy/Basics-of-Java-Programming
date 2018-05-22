package hr.fer.zemris.java.hw11.jnotepadpp.listeners;

import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;

/**
 * Defines methods for listener for {@link SingleDocumentModel}. Each listener
 * will be informed when document changes ie. its modified flag changes and when
 * its file path changes.
 * 
 * @author tin
 *
 */
public interface SingleDocumentListener {
  /**
   * Method which is called when document's modified flag changes.
   * 
   * @param model model whose modified flag changed
   */
  void documentModifyStatusUpdated(SingleDocumentModel model);

  /**
   * Method which is called when document's file path is changed.
   * 
   * @param model model whose file path is changed
   */
  void documentFilePathUpdated(SingleDocumentModel model);
}