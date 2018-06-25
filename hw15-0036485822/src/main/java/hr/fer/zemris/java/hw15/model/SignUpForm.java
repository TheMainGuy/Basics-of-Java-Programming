package hr.fer.zemris.java.hw15.model;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.crypto.Crypto;
import hr.fer.zemris.java.hw15.dao.DAOProvider;

/**
 * Represents sign up form used to help with managing user input in web
 * application.
 * 
 * @author tin
 *
 */
public class SignUpForm extends Form {
  /**
   * User's first name.
   */
  private String firstName;

  /**
   * User's last name.
   */
  private String lastName;

  /**
   * User's nick.
   */
  private String nick;

  /**
   * User's E-mail.
   */
  private String email;

  /**
   * User's password.
   */
  private String password;

  /**
   * Fills this object's data with parameters from given
   * {@link HttpServletRequest} object.
   * 
   * @param request request from which parameters will be extracted
   */
  public void fillFromHttpRequest(HttpServletRequest request) {
    this.firstName = prepare(request.getParameter("firstName"));
    this.lastName = prepare(request.getParameter("lastName"));
    this.nick = prepare(request.getParameter("nick"));
    this.email = prepare(request.getParameter("email"));
    this.password = prepare(request.getParameter("password"));
  }

  /**
   * Fills given {@link BlogUser} object with data from this object.
   * 
   * @param blogUser object which will be filled with data
   */
  public void fillToUser(BlogUser blogUser) {
    blogUser.setFirstName(firstName);
    blogUser.setLastName(lastName);
    blogUser.setEmail(email);
    blogUser.setNick(nick);
    blogUser.setPasswordHash(Crypto.digestPassword(password));
  }

  @Override
  public void validate() {
    if(firstName.equals("")) {
      errors.put("firstName", "First Name can not be empty.");
    }
    if(lastName.equals("")) {
      errors.put("lastName", "Last Name can not be empty.");
    }
    if(nick.equals("")) {
      errors.put("nick", "Nick can not be empty.");
    } else if(DAOProvider.getDAO().getUserFromNick(nick) != null) {
      errors.put("nick", "User with given Nick already exists.");
    }
    if(email.equals("")) {
      errors.put("email", "Email can not be empty.");
    } else if(email.length() < 3 || email.indexOf('@') == 0 || email.indexOf('@') == email.length() - 1) {
      errors.put("email", "Email is not valid.");
    }
    if(password.equals("")) {
      errors.put("password", "Password can not be empty.");
    }
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @return the nick
   */
  public String getNick() {
    return nick;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }
}
