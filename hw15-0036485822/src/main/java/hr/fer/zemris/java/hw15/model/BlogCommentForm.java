package hr.fer.zemris.java.hw15.model;

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
   * User's E-mail.
   */
  private String email;
  
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
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Fills given {@link BlogComment} object with data from this object.
   * 
   * @param blogComment object which will be filled with data
   */
  public void fillToBlogComment(BlogComment blogComment) {
    blogComment.setMessage(message);
    blogComment.setBlogEntry(DAOProvider.getDAO().getBlogEntry(Long.parseLong(entryId)));
    blogComment.setUsersEMail(email);
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
    this.email = prepare(request.getParameter("email"));
    String userNick = request.getParameter("userNick");
    BlogUser user = DAOProvider.getDAO().getUserFromNick(userNick);
    if(email.equals("") && user != null) {
      this.email = user.getEmail();
    }
  }

  @Override
  public void validate() {
    if(message.equals("")) {
      errors.put("comment", "Comment can not be empty.");
    }
    if(email.equals("")) {
      errors.put("email", "Email can not be empty.");
    } else if(email.length() < 3 || email.indexOf('@') == 0 || email.indexOf('@') == email.length() - 1 || email.indexOf('@') == -1) {
      errors.put("email", "Email is not valid.");
    }
  }
}
