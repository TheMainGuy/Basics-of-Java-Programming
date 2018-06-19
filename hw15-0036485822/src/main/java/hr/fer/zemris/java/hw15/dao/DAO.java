package hr.fer.zemris.java.hw15.dao;

import java.util.List;

import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Interface which defines communication methods that allow user to communicate
 * with database. User can create, read, update and delete data from database
 * through methods provided in this interface.
 * 
 * @author tin
 *
 */
public interface DAO {

  /**
   * Returns blog entry from database using given id. If there is no entry with
   * given id, returns <code>null</code>.
   * 
   * @param id blog entry key
   * @return blog entry with given key, <code>null</code> if there is no entry
   *         with given key
   * @throws DAOException if there is problem with getting data
   */
  public BlogEntry getBlogEntry(Long id) throws DAOException;

  /**
   * Returns blog user from database using given nick. If there is no user with
   * given nick, returns <code>null</code>.
   * 
   * @param nick blog user nick
   * @return blog user with given nick, <code>null</code> if there is no user with
   *         given nick
   * @throws DAOException if there is problem with getting data
   */
  public BlogUser getUserFromNick(String nick) throws DAOException;

  /**
   * Adds new user to database. Given {@link BlogUser} object must have non null
   * parameters except for id. Id will be automatically generated.
   * 
   * @param blogUser user which will be added to database
   * @throws DAOException if there is problem with inserting data
   */
  public void addNewUser(BlogUser blogUser) throws DAOException;

  /**
   * Method returns list of all user nicks stored as strings.
   * 
   * @return list of user nicks
   * @throws DAOException if there is problem with getting data
   */
  public List<String> getListOfNicks() throws DAOException;

  /**
   * Method returns list of blog entries posted by user with given id. Each blog
   * entry is filled with data from database and is stored as {@link BlogEntry}
   * object.
   * 
   * @param id user id
   * @return list of blog entries posted by user with given id
   * @throws DAOException if there is problem with getting data
   */
  public List<BlogEntry> getListOfEntries(Long id) throws DAOException;

  /**
   * Adds new blog entry to database. Given {@link BlogEntry} object must have non
   * null parameters except for id and lastModifiedAt. Also, blog entry is mapped
   * to user whose reference is stored in given {@link BlogEntry}.
   * 
   * @param blogEntry blog entry which will be added to database
   * @throws DAOException if there is problem with inserting data
   */
  public void addNewEntry(BlogEntry blogEntry) throws DAOException;

  /**
   * Changes given entry using its id to identify which entry from database should
   * be changed. Database entry will be changed to match given blog entry.
   * 
   * @param blogEntry blog entry to which database entry will be changed to
   * @throws DAOException if there is problem with inserting data
   */
  public void editEntry(BlogEntry blogEntry) throws DAOException;

  /**
   * Adds new blog comment to database. Given {@link BlogComment} object must have
   * non null parameters except for id. Also, blog comment is mapped to entry whose
   * reference is stored in given {@link BlogComment}.
   * 
   * @param blogComment blog comment which will be added to database
   * @throws DAOException if there is problem with inserting data
   */
  public void addNewComment(BlogComment blogComment) throws DAOException;
}