package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.model.BasicPolls;
import hr.fer.zemris.java.model.PollData;
import hr.fer.zemris.java.model.PollData.PollOption;

/**
 * Implements servlet context listener which, when context is initialized,
 * creates {@link ComboPooledDataSource} objectt which will be used to provide
 * connections to database. After that, checks database for Polls and
 * PollOptions tables and creates them if needed. After that, checks if Polls
 * table is empty and if it is, initializes 2 basic polls.
 * 
 * When context is destroyed, destroys {@link ComboPooledDataSource} object.
 * 
 * @author tin
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {
  private final String POLLS_TABLE = "POLLS";
  private final String POLLS_TABLE_CREATE = "CREATE TABLE Polls\n"
      + " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" + " title VARCHAR(150) NOT NULL,\n"
      + " message CLOB(2048) NOT NULL\n" + ")";
  private final String POLL_OPTIONS_TABLE = "POLLOPTIONS";
  private final String POLL_OPTIONS_TABLE_CREATE = "CREATE TABLE PollOptions\n"
      + " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" + " optionTitle VARCHAR(100) NOT NULL,\n"
      + " optionLink VARCHAR(150) NOT NULL,\n" + " pollID BIGINT,\n" + " votesCount BIGINT,\n"
      + " FOREIGN KEY (pollID) REFERENCES Polls(id)\n" + ")";

  @Override
  public void contextInitialized(ServletContextEvent sce) {

    Properties databaseProperties = new Properties();
    try {
      databaseProperties
          .load(Files.newInputStream(Paths.get(sce.getServletContext().getRealPath("WEB-INF/dbsettings.properties"))));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    String host = databaseProperties.getProperty("host");
    String port = databaseProperties.getProperty("port");
    String dbName = databaseProperties.getProperty("name");
    String user = databaseProperties.getProperty("user");
    String password = databaseProperties.getProperty("password");
    if(host == null || port == null || dbName == null || user == null || password == null) {
      throw new RuntimeException(
          "One or more properties missing. Expected properties: host, port, dbName, user and password");
    }
    String connectionURL = "jdbc:derby://" + host + ":" + port + "/" + dbName + ";user=" + user + ";password="
        + password;

    ComboPooledDataSource cpds = new ComboPooledDataSource();
    try {
      cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
    } catch (PropertyVetoException e1) {
      throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
    }
    cpds.setJdbcUrl(connectionURL);
    try {
      createTablesIfNotExist(cpds);
      basicPolls(cpds);
    } catch (SQLException e) {
      throw new IllegalArgumentException(e);
    }
    sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
  }

  /**
   * Checks if Polls table is empty and if it is creates two basic polls. Method
   * creates two basic polls with data stored in constants BASIC_POLLS and
   * BASIC_POLL_OPTIONS. It will populate tables Polls and PollOptions with two
   * basic polls.
   * 
   * @param cpds combo pooled data source used to provide connection
   * @throws SQLException
   */
  private void basicPolls(ComboPooledDataSource cpds) throws SQLException {
    Connection connection = cpds.getConnection();
    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM POLLS");
    ResultSet resultSet = preparedStatement.executeQuery();
    if(!resultSet.next()) {
      createBasicPolls(connection, BasicPolls.getBasicPoll());
    }
  }

  /**
   * Method which uses given connection to instantiate poll. Also takes care of
   * linking poll inserted to Polls table with its options in PollOptions.
   * 
   * @param connection connection used to communicate with database
   * @param createPoll sql used to insert poll in Poll table
   * @param createPollOptions sql used to insert poll options in PollOptions table
   * @throws SQLException
   */
  private void createBasicPolls(Connection connection, PollData poll) throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement(createPollInsert(poll),
        java.sql.Statement.RETURN_GENERATED_KEYS);
    int affectedRows = preparedStatement.executeUpdate();
    if(affectedRows == 0) {
      throw new SQLException("Failed inserting row in Polls table.");
    }

    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
    generatedKeys.next();
    long id = generatedKeys.getLong(1);
    PreparedStatement pollOptionsStatement = connection.prepareStatement(createPollOptionsInsert(poll, id));
    affectedRows = pollOptionsStatement.executeUpdate();

    if(affectedRows != poll.getOptions().size()) {
      throw new SQLException("Couldn't insert poll options.");
    }

  }

  /**
   * Helper method which creates insert sql statement for inserting poll options
   * in table PollOptions. Creates statement based on parameters poll and id.
   * 
   * @param poll poll which will be used to get options
   * @param id id which will be used to reference poll option to specific poll in
   *          Polls table
   * @return sql statement which inserts poll options in PollOptions table
   */
  private String createPollOptionsInsert(PollData poll, long id) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("INSERT INTO POLLOPTIONS (optiontitle, optionlink, pollid, votescount) VALUES\n");
    List<PollOption> options = poll.getOptions();
    for (int i = 0, n = poll.getOptions().size(); i < n; i++) {
      PollOption option = options.get(i);
      stringBuilder.append("('");
      stringBuilder.append(option.getOptionTitle()).append("', '");
      stringBuilder.append(option.getOptionLink()).append("', ");
      stringBuilder.append(id).append(", ");
      stringBuilder.append(option.getVotesCount()).append(")");
      if(i != n - 1) {
        stringBuilder.append(",\n");
      }
    }

    return stringBuilder.toString();
  }

  /**
   * Helper method which creates insert sql statement for inserting poll in table
   * Polls. Creates statement based on information given in parameter poll.
   * 
   * @param poll
   * @return sql statement which inserts given poll in Polls table
   */
  private String createPollInsert(PollData poll) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("INSERT INTO POLLS (title, message) VALUES\n ('");
    stringBuilder.append(poll.getTitle()).append("', '").append(poll.getMessage()).append("')");
    return stringBuilder.toString();
  }

  /**
   * Checks if tables Polls and PollsOptions exist in database. Calls
   * checkAndCreateTable method with each one of those tables in order to create
   * them if they do not exist.
   * 
   * @param cpds combo pooled data source used to provide connection
   * @throws SQLException
   */
  private void createTablesIfNotExist(ComboPooledDataSource cpds) throws SQLException {
    Connection connection = cpds.getConnection();
    checkAndCreateTable(connection, POLLS_TABLE, POLLS_TABLE_CREATE);
    checkAndCreateTable(connection, POLL_OPTIONS_TABLE, POLL_OPTIONS_TABLE_CREATE);
  }

  /**
   * Method which checks if given table exists and creates it if it does not. If
   * it does exist, does nothing.
   * 
   * @param connection connection which will be used to check and create table
   * @param tableName name of table to be checked and created
   * @param tableCreate SQL statement which will be used to create table if needed
   * @throws SQLException if there is problem with acquiring
   *           {@link DatabaseMetaData} object or executing statement
   */
  private void checkAndCreateTable(Connection connection, String tableName, String tableCreate) throws SQLException {
    DatabaseMetaData databaseMetaData = connection.getMetaData();
    ResultSet rs = databaseMetaData.getTables(null, null, tableName, null);
    if(!rs.next()) {
      PreparedStatement preparedStatement = connection.prepareStatement(tableCreate);
      preparedStatement.execute();
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
    if(cpds != null) {
      try {
        DataSources.destroy(cpds);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

}