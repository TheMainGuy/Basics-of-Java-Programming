package hr.fer.zemris.java.hw15.dao;

import hr.fer.zemris.java.hw15.dao.jpa.JPADAOImpl;

/**
 * Singleton class which stores one {@link DAO} object and can return it when
 * getDao method is called.
 * 
 * @author tin
 *
 */
public class DAOProvider {

  /**
   * DAO object which is created when program starts and stored until program
   * ends.
   */
  private static DAO dao = new JPADAOImpl();

  /**
   * Returns stored DAO object.
   * 
   * @return stored DAO object
   */
  public static DAO getDAO() {
    return dao;
  }

}