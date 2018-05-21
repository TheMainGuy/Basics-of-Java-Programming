package hr.fer.zemris.java.hw11.jnotepadpp.models;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import javax.swing.JTabbedPane;

import hr.fer.zemris.java.hw11.jnotepadpp.listeners.MultipleDocumentListener;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * List of all opened documents. Each document is stored in one
   * {@link SingleDocumentModel} object.
   */
  List<SingleDocumentModel> documents;
  
  /**
   * Reference to currently opened document.
   */
  SingleDocumentModel currentDocument;
  
  /**
   * List of listeners which will be informed when documents are added or removed.
   */
  List<MultipleDocumentListener> listeners;

  public DefaultMultipleDocumentModel() {
    documents = new ArrayList<>();
    listeners = new ArrayList<>();
  }

  @Override
  public Iterator<SingleDocumentModel> iterator() {
    return documents.iterator();
  }

  @Override
  public SingleDocumentModel createNewDocument() {
    currentDocument = new DefaultSingleDocumentModel(null, "");
    documents.add(currentDocument); 
    this.addTab("Untitled", currentDocument.getTextComponent());
    setSelectedIndex(documents.indexOf(currentDocument));
    
    notifyAllDocumentListeners(l ->{
      l.documentAdded(currentDocument);
      return null;
    });
    return currentDocument;
    
  }

  @Override
  public SingleDocumentModel getCurrentDocument() {
    return currentDocument;
  }

  @Override
  public SingleDocumentModel loadDocument(Path path) {
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
    currentDocument = new DefaultSingleDocumentModel(path, text);
    documents.add(currentDocument); 
    this.addTab(path.getFileName().toString(), currentDocument.getTextComponent());
    setSelectedIndex(documents.indexOf(currentDocument));
    
    notifyAllDocumentListeners(l ->{
      l.documentAdded(currentDocument);
      return null;
    });
    return currentDocument;
  }

  @Override
  public void saveDocument(SingleDocumentModel model, Path newPath) {
    if(!model.isModified()) {
      return;
    }
    
    byte[] data = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
    
    try {
      Files.write(newPath, data, StandardOpenOption.WRITE);
    } catch (Exception ex) {
      throw new IllegalArgumentException("Problem with writing to file " + newPath.toString());
    }
  }

  @Override
  public void closeDocument(SingleDocumentModel model) {
    this.removeTabAt(documents.indexOf(model));
    documents.remove(model);
    if(model.equals(currentDocument)) {
      if(documents.size() > 0) {
        currentDocument = documents.get(0);
      }
    }
    setSelectedIndex(documents.indexOf(currentDocument));
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
  
  private void notifyAllDocumentListeners(Function<MultipleDocumentListener, Void> f) {
    for(MultipleDocumentListener listener : listeners) {
      f.apply(listener);
    }
  }
  

}
