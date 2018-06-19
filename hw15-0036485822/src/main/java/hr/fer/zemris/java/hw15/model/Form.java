package hr.fer.zemris.java.hw15.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines web application form with errors map. Each filled form entry can be
 * validated using method validate which fills errors map with form errors.
 * 
 * After validation, methods hasErrors, hasError and getError allow access to
 * errors.
 * 
 * @author tin
 *
 */
public abstract class Form {

  /**
   * Map of errors in input.
   */
  protected Map<String, String> errors = new HashMap<>();

  /**
   * Converts nulls to empty strings and trims empty characters.
   * 
   * @param parameter string to be prepared
   * @return prepared string
   */
  protected String prepare(String parameter) {
    if(parameter == null) {
      parameter = "";
    }
    return parameter.trim();
  }

  /**
   * Checks if there is an error for given parameter. Returns <code>true</code> if
   * there is, <code>false</code> if there is not.
   * 
   * @param parameter parameter upon which is tested
   * @return <code>true</code> if there is error with given parameter,
   *         <code>false</code> otherwise
   */
  public boolean hasError(String parameter) {
    return errors.containsKey(parameter);
  }

  /**
   * Returns found error for given parameter. Returns <code>null</code> if there
   * is no error for given parameter.
   * 
   * @param parameter parameter whose error will be returned
   * @return error
   */
  public String getError(String parameter) {
    return errors.get(parameter);
  }

  /**
   * Checks if there are any errors with form. Returns <code>true</code> if there
   * is at least one error, <code>false</code> if there are no errors.
   * 
   * @return <code>true</code> if there is error with form, <code>false</code>
   *         otherwise
   */
  public boolean hasErrors() {
    return !errors.isEmpty();
  }

  /**
   * Validates form and fills errors map.
   */
  public abstract void validate();
}
