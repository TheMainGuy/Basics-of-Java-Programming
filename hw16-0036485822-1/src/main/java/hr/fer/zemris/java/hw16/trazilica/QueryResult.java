package hr.fer.zemris.java.hw16.trazilica;

/**
 * Represents query result which stores information from comparing query vector
 * with one of document vectors.
 * 
 * @author tin
 *
 */
public class QueryResult {
  /**
   * Path do document.
   */
  private String document;
  
  /**
   * Tfidf similarity between this document and last performed query.
   */
  private double similarity;

  /**
   * Constructor.
   * 
   * @param document document path
   * @param similarity similarity
   */
  public QueryResult(String document, double similarity) {
    this.document = document;
    this.similarity = similarity;
  }

  /**
   * @return the document
   */
  public String getDocument() {
    return document;
  }

  /**
   * @return the similarity
   */
  public double getSimilarity() {
    return similarity;
  }

}
