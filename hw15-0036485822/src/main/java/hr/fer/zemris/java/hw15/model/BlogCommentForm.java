package hr.fer.zemris.java.hw15.model;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.dao.DAOProvider;

/**
 * Represents blog comment form used to help with managing user input in web
 * application.
 * 
 * @author tin
 *
 */
public class BlogCommentForm extends Form {
  /**
   * Comment message.
   */
  private String message;

  /**
   * Id of entry to which this comment was posted.
   */
  private String entryId;

  /**
   * Nick of user which posted this comment.
   */
  private String userNick;

  /**
   * Map of errors in input.
   */
  private Map<String, String> errors = new HashMap<>();

  /**
   * @return the comment
   */
  public String getMessage() {
    return message;
  }

  /**
   * @return the id
   */
  public String getEntryId() {
    return entryId;
  }

  /**
   * Fills given {@link BlogComment} object with data from this object.
   * 
   * @param blogComment object which will be filled with data
   */
  public void fillToBlogComment(BlogComment blogComment) {
    blogComment.setMessage(message);
    blogComment.setBlogEntry(DAOProvider.getDAO().getBlogEntry(Long.parseLong(entryId)));
    blogComment.setUsersEMail(DAOProvider.getDAO().getUserFromNick(userNick).getEmail());
  }

  /**
   * Fills this object's data with parameters from given
   * {@link HttpServletRequest} object.
   * 
   * @param request request from which parameters will be extracted from
   */
  public void fillFromHttpRequest(HttpServletRequest request) {
    this.message = prepare(request.getParameter("comment"));
    this.entryId = prepare(request.getParameter("entryId"));
    this.userNick = prepare(request.getParameter("userNick"));
  }

  @Override
  public void validate() {
    if(message.equals("")) {
      errors.put("comment", "Comment can not be empty.");
    }
  }
}
