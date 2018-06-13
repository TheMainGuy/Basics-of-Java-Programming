package hr.fer.zemris.java.p12.dao;

import java.util.List;

import hr.fer.zemris.java.model.PollData;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author marcupic
 *
 */
public interface DAO {

  /**
   * Dohvaća sve postojeće unose u bazi, ali puni samo dva podatka: id i title.
   * 
   * @return listu unosa
   * @throws DAOException u slučaju pogreške
   */
  public List<PollData> getBasicPollList() throws DAOException;

  /**
   * Dohvaća Unos za zadani id. Ako unos ne postoji, vraća <code>null</code>.
   * 
   * @param id
   * @return
   * @throws DAOException
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