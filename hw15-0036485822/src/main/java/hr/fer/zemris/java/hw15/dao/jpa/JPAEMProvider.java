package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.hw15.dao.DAOException;

/**
 * Class which provides {@link EntityManager} objects. For each thread,
 * different {@link EntityManager} can be stored in locals map. Map locals
 * stores {@link EntityManager} objects for each thread. Those object can be
 * modified and read from within the thread.
 * 
 * @author tin
 *
 */
public class JPAEMProvider {

  /**
   * Map of {@link EntityManager} objects.
   */
  private static ThreadLocal<EntityManager> locals = new ThreadLocal<>();

  /**
   * Returns {@link EntityManager} object from locals map.
   * 
   * @return {@link EntityManager} object from locals map
   */
  public static EntityManager getEntityManager() {
    EntityManager em = locals.get();
    if(em == null) {
      em = JPAEMFProvider.getEmf().createEntityManager();
      em.getTransaction().begin();
      locals.set(em);
    }
    return em;
  }

  /**
   * Closes {@link EntityManager} object after commiting transaction. Catches any
   * exceptions which might occur during that process and throws
   * {@link DAOException} if they do.
   * 
   * @throws DAOException if exception occur during closing {@link EntityManager} object.
   */
  public static void close() throws DAOException {
    EntityManager em = locals.get();
    if(em == null) {
      return;
    }
    DAOException dex = null;
    try {
      em.getTransaction().commit();
    } catch (Exception ex) {
      dex = new DAOException("Unable to commit transaction.", ex);
    }
    try {
      em.close();
    } catch (Exception ex) {
      if(dex != null) {
        dex = new DAOException("Unable to close entity manager.", ex);
      }
    }
    locals.remove();
    if(dex != null)
      throw dex;
  }

}