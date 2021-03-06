package hr.fer.zemris.java.p12.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.model.PollData;
import hr.fer.zemris.java.model.PollData.PollOption;
import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;

/**
 * Implements {@link DAO} interface using SQL. This implementation expects
 * {@link SQLConnectionProvider} object to have already set connection towards
 * database.
 * 
 * @author tin
 */
public class SQLDAO implements DAO {

  @Override
  public List<PollData> getBasicPollList() throws DAOException {
    List<PollData> polls = new ArrayList<>();
    Connection con = SQLConnectionProvider.getConnection();
    PreparedStatement pst = null;
    try {
      pst = con.prepareStatement("select id, title, message from POLLS order by id");
      try {
        ResultSet rs = pst.executeQuery();
        try {
          while (rs != null && rs.next()) {
            long id = rs.getLong(1);
            String title = rs.getString(2);
            String message = rs.getString(3);
            PollData poll = new PollData(title, message, new ArrayList<>(), id);
            polls.add(poll);
          }
        } finally {
          try {
            rs.close();
          } catch (Exception ignorable) {
          }
        }
      } finally {
        try {
          pst.close();
        } catch (Exception ignorable) {
        }
      }
    } catch (Exception ex) {
      throw new DAOException("Error while getting basic poll list.", ex);
    }
    return polls;
  }

  @Override
  public PollData getPoll(long id) throws DAOException {
    PollData poll = null;
    Connection con = SQLConnectionProvider.getConnection();
    PreparedStatement pst = null;
    try {
      pst = con.prepareStatement("select id, title, message from POLLS where id=?");
      pst.setLong(1, Long.valueOf(id));
      try {
        ResultSet rs = pst.executeQuery();
        try {
          if(rs != null && rs.next()) {
            long pollId = rs.getLong(1);
            String title = rs.getString(2);
            String message = rs.getString(3);
            List<PollOption> options = new ArrayList<>();
            pst = con
                .prepareStatement("select optionTitle, optionLink, id, votesCount from POLLOPTIONS where pollId=?");
            pst.setLong(1, Long.valueOf(pollId));
            ResultSet pollOptions = pst.executeQuery();
            try {
              while (pollOptions != null && pollOptions.next()) {
                String optionTitle = pollOptions.getString(1);
                String optionLink = pollOptions.getString(2);
                long optionId = pollOptions.getLong(3);
                int votesCount = pollOptions.getInt(4);
                options.add(new PollOption(optionTitle, optionLink, optionId, votesCount));
              }
            } finally {
              try {
                pollOptions.close();
              } catch (Exception ignorable) {
              }
            }

            poll = new PollData(title, message, options, pollId);
          }
        } finally {
          try {
            rs.close();
          } catch (Exception ignorable) {
          }
        }
      } finally {
        try {
          pst.close();
        } catch (Exception ignorable) {
        }
      }
    } catch (Exception ex) {
      throw new DAOException("Pogreška prilikom dohvata korisnika.", ex);
    }
    return poll;
  }

  @Override
  public void updateVotesCount(int votesCount, long optionId) throws DAOException {
    Connection con = SQLConnectionProvider.getConnection();
    try {
      PreparedStatement updateVotes = con.prepareStatement("UPDATE POLLOPTIONS\nSET votesCount = ? WHERE id = ?");
      updateVotes.setInt(1, votesCount);
      updateVotes.setLong(2, optionId);
      updateVotes.execute();
    } catch (Exception e) {
      throw new DAOException("Problem with updating votesCount.");
    }
  }

}