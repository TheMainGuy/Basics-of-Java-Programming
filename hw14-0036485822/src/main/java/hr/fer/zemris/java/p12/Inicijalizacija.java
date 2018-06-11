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
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

@WebListener
public class Inicijalizacija implements ServletContextListener {
  private final String pollsTable = "POLLS";
  private final String pollsTableCreate = "CREATE TABLE Polls\n" + 
      " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" + 
      " title VARCHAR(150) NOT NULL,\n" + 
      " message CLOB(2048) NOT NULL\n" + 
      ")";
  private final String pollOptionsTable = "POLLOPTIONS";
  private final String pollOptinosTableCreate = "CREATE TABLE PollOptions\n" + 
      " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" + 
      " optionTitle VARCHAR(100) NOT NULL,\n" + 
      " optionLink VARCHAR(150) NOT NULL,\n" + 
      " pollID BIGINT,\n" + 
      " votesCount BIGINT,\n" + 
      " FOREIGN KEY (pollID) REFERENCES Polls(id)\n" + 
      ")";

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

  private void basicPolls(ComboPooledDataSource cpds) throws SQLException {
    Connection connection = cpds.getConnection();
    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM POLLS");
    ResultSet resultSet = preparedStatement.executeQuery();
    if(!resultSet.next()) {
      String sql = "";
      createBasicPolls(connection, sql);
    }
  }

  private void createBasicPolls(Connection connection, String sql) throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.execute();
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
    checkAndCreateTable(connection, pollsTable, pollsTableCreate);
    checkAndCreateTable(connection, pollOptionsTable, pollOptinosTableCreate);
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

}