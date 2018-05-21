package hr.fer.zemris.java.hw11.jnotepadpp.models;

import java.nio.file.Path;

import hr.fer.zemris.java.hw11.jnotepadpp.listeners.MultipleDocumentListener;

/**
 *
 * @author tin
 *
 */

public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
  SingleDocumentModel createNewDocument();

  SingleDocumentModel getCurrentDocument();

  SingleDocumentModel loadDocument(Path path);

  void saveDocument(SingleDocumentModel model, Path newPath);

  void closeDocument(SingleDocumentModel model);

  void addMultipleDocumentListener(MultipleDocumentListener l);

  void removeMultipleDocumentListener(MultipleDocumentListener l);

  int getNumberOfDocuments();

  SingleDocumentModel getDocument(int index);
}
