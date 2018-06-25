package hr.fer.zemris.java.hw15.model;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.crypto.Crypto;
import hr.fer.zemris.java.hw15.dao.DAOProvider;

/**
 * Represents log in form used to help with managing user input in web
 * application.
 * 
 * @author tin
 *
 */
public class LoginForm extends Form {
  /**
   * User's nick.
   */
  private String nick;

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
    this.nick = prepare(request.getParameter("nick"));
    this.password = prepare(request.getParameter("password"));
  }

  /**
   * @return the nick
   */
  public String getNick() {
    return nick;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  @Override
  public void validate() {
    if(password.equals("")) {
      errors.put("password", "Password can not be empty.");
    }
    if(nick.equals("")) {
      errors.put("nick", "Nick can not be empty.");
    } else if(DAOProvider.getDAO().getUserFromNick(nick) == null) {
      errors.put("nick", "Nick does not exist.");
    } else if(!hasError("password")) {
      if(!DAOProvider.getDAO().getUserFromNick(nick).getPasswordHash().equals(Crypto.digestPassword(password))) {
        errors.put("password", "Wrong nick and password combination.");
      }
    }

  }

}
