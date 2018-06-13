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
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova konkretna
 * implementacija očekuje da joj veza stoji na raspolaganju preko
 * {@link SQLConnectionProvider} razreda, što znači da bi netko prije no što
 * izvođenje dođe do ove točke to trebao tamo postaviti. U web-aplikacijama
 * tipično rješenje je konfigurirati jedan filter koji će presresti pozive
 * servleta i prije toga ovdje ubaciti jednu vezu iz connection-poola, a po
 * zavrsetku obrade je maknuti.
 * 
 * @author marcupic
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
      pst = con.prepareStatement("select id, title, message from Poruke where id=?");
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
                .prepareStatement("select optionTitle, optionLink, pollId, votesCount from POLLOPTIONS where pollId=?");
            pst.setLong(1, Long.valueOf(pollId));
            ResultSet pollOptions = pst.executeQuery();
            try {
              while (pollOptions != null && pollOptions.next()) {
                String optionTitle = pollOptions.getString(1);
                String optionLink = pollOptions.getString(2);
                long belongsTo = pollOptions.getLong(3);
                int votesCount = pollOptions.getInt(4);
                options.add(new PollOption(optionTitle, optionLink, belongsTo, votesCount));
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

}