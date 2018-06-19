package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Class which provides {@link EntityManagerFactory} object on request.
 * {@link EntityManagerFactory} should not be created anywhere else. Instead,
 * object stored here should be the only one used in single web application.
 * 
 * @author tin
 *
 */
public class JPAEMFProvider {

  /**
   * {@link EntityManagerFactory} object which is readable and writable.
   */
  public static EntityManagerFactory emf;

  /**
   * Returns stored emf.
   * 
   * @return stored emf
   */
  public static EntityManagerFactory getEmf() {
    return emf;
  }

  /**
   * Sets emf to given emf.
   * 
   * @param emf {@link EntityManagerFactory} to which emf will be set
   */
  public static void setEmf(EntityManagerFactory emf) {
    JPAEMFProvider.emf = emf;
  }
}