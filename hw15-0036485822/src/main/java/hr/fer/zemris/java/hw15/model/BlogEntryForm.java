package hr.fer.zemris.java.hw15.model;

import javax.servlet.http.HttpServletRequest;

/**
 * Represents blog entry form used to help with managing user input in web
 * application.
 * 
 * @author tin
 *
 */
public class BlogEntryForm extends Form {
  /**
   * Title.
   */
  private String title;

  /**
   * Text.
   */
  private String text;

  /**
   * Unique identifier.
   */
  private String id;

  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * @return the text
   */
  public String getText() {
    return text;
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * Fills this object's data with parameters from given {@link BlogEntry} object.
   * 
   * @param entry blog entry from which the parameters will be extracted
   */
  public void fillFromBlogEntry(BlogEntry entry) {
    this.title = prepare(entry.getTitle());
    this.text = prepare(entry.getText());
    this.id = entry.getId() == null ? "0" : prepare(entry.getId().toString());
  }

  /**
   * Fills given {@link BlogEntry} object with data from this object.
   * 
   * @param blogEntry object which will be filled with data
   */
  public void fillToBlogEntry(BlogEntry entry) {
    entry.setTitle(title);
    entry.setText(text);
    entry.setId(id.equals("0") ? null : Long.parseLong(id));
  }

  /**
   * Fills this object's data with parameters from given
   * {@link HttpServletRequest} object.
   * 
   * @param request request from which parameters will be extracted
   */
  public void fillFromHttpRequest(HttpServletRequest request) {
    this.title = prepare(request.getParameter("title"));
    this.text = prepare(request.getParameter("text"));
    this.id = prepare(request.getParameter("id"));
  }

  @Override
  public void validate() {
    if(title.equals("")) {
      errors.put("title", "Title can not be empty.");
    }
    if(text.equals("")) {
      errors.put("text", "Text can not be empty.");
    }
  }
}
