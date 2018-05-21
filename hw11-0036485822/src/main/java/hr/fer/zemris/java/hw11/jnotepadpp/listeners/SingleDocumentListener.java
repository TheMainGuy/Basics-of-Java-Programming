package hr.fer.zemris.java.hw11.jnotepadpp.listeners;

import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;

public interface SingleDocumentListener {
  void documentModifyStatusUpdated(SingleDocumentModel model);

  void documentFilePathUpdated(SingleDocumentModel model);
}