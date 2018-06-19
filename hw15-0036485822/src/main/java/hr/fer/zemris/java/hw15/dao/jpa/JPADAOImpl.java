package hr.fer.zemris.java.hw15.dao.jpa;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Class which implements DAO interface in order to create, read, update and
 * delete database data.
 * 
 * @author tin
 *
 */
public class JPADAOImpl implements DAO {

  @Override
  public BlogEntry getBlogEntry(Long id) throws DAOException {
    BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
    return blogEntry;
  }

  @Override
  public BlogUser getUserFromNick(String nick) throws DAOException {
    @SuppressWarnings("unchecked")
    List<BlogUser> blogUsers = JPAEMProvider.getEntityManager()
        .createQuery("select b from BlogUser as b where b.nick IS '" + nick + "'").getResultList();
    if(blogUsers.isEmpty()) {
      return null;
    }
    return blogUsers.get(0);
  }

  @Override
  public void addNewUser(BlogUser blogUser) throws DAOException {
    EntityManager em = JPAEMProvider.getEntityManager();
    em.persist(blogUser);
  }

  @Override
  public List<String> getListOfNicks() throws DAOException {
    @SuppressWarnings("unchecked")
    List<String> nicks = JPAEMProvider.getEntityManager().createQuery("select b.nick from BlogUser as b")
        .getResultList();
    return nicks;
  }

  @Override
  public List<BlogEntry> getListOfEntries(Long id) throws DAOException {
    @SuppressWarnings("unchecked")
    List<BlogEntry> blogEntries = JPAEMProvider.getEntityManager()
        .createQuery("select b from BlogEntry as b where b.creator.id IS " + id).getResultList();
    return blogEntries;
  }

  @Override
  public void addNewEntry(BlogEntry blogEntry) throws DAOException {
    blogEntry.setCreatedAt(Calendar.getInstance().getTime());
    EntityManager em = JPAEMProvider.getEntityManager();
    em.persist(blogEntry);
    BlogUser user = em.find(BlogUser.class, blogEntry.getCreator().getId());
    user.getEntries().add(blogEntry);
  }

  @Override
  public void editEntry(BlogEntry blogEntry) throws DAOException {
    EntityManager em = JPAEMProvider.getEntityManager();
    BlogEntry entry = em.find(BlogEntry.class, blogEntry.getId());
    entry.setText(blogEntry.getText());
    entry.setTitle(blogEntry.getTitle());
    entry.setLastModifiedAt(Calendar.getInstance().getTime());

  }

  @Override
  public void addNewComment(BlogComment blogComment) throws DAOException {
    EntityManager em = JPAEMProvider.getEntityManager();
    em.persist(blogComment);
    BlogEntry entry = em.find(BlogEntry.class, blogComment.getBlogEntry().getId());
    entry.getComments().add(blogComment);
  }

}