package hr.fer.zemris.java.p12.dao;

import hr.fer.zemris.java.p12.dao.sql.SQLDAO;

/**
 * Singleton class which stores one {@link DAO} object and can return it when
 * getDao method is called.
 * 
 * @author tin
 *
 */
public class DAOProvider {

  private static DAO dao = new SQLDAO();

  /**
   * Dohvat primjerka.
   * 
   * @return objekt koji enkapsulira pristup sloju za perzistenciju podataka.
   */
  public static DAO getDao() {
    return dao;
  }

}