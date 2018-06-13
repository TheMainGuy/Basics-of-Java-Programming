package hr.fer.zemris.java.p12.dao;

import java.util.List;

import hr.fer.zemris.java.model.PollData;

/**
 * Interface towards database system.
 * 
 * @author tin
 *
 */
public interface DAO {

  /**
   * Gets basic data from Polls table.
   * 
   * @return list of polls in Polls table
   * @throws DAOException if any error occurs
   */
  public List<PollData> getBasicPollList() throws DAOException;

  /**
   * Gets poll from Polls table and its options from PollOptions table using given
   * id. If there is no poll with given id, returns <code>null</code>.
   * 
   * Poll and its options is returned in form of {@link PollData} object.
   * 
   * @param id id which will be used to get poll
   * @return poll with given id and its options
   * @throws DAOException if any error occurs
   */
  public PollData getPoll(long id) throws DAOException;

  /**
   * Updates votesCount parameter of of an given option to given parameter
   * votesCount.
   * 
   * @param votesCount number to which votes count will be set in database
   * @param optionId id of an option whose id will be set
   */
  public void updateVotesCount(int votesCount, long optionId) throws DAOException;
}