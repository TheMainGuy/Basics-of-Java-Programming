package hr.fer.zemris.java.hw11.jnotepadpp.listeners;

import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;

public interface MultipleDocumentListener {
  void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

  void documentAdded(SingleDocumentModel model);

  void documentRemoved(SingleDocumentModel model);
}
