package hr.fer.zemris.java.p12.dao.sql;

import java.sql.Connection;

/**
 * Class which stores connections towards database in {@link ThreadLocal}
 * object. It can be accessed to by using setConnection method to add new
 * connection to stored connections and by using getConnection method to get
 * connection.
 * 
 * @author tin
 *
 */
public class SQLConnectionProvider {

  /**
   * Collection of connections. Similar to map with thread ids being keys and
   * {@link Connection} object being values.
   */
  private static ThreadLocal<Connection> connections = new ThreadLocal<>();

  /**
   * Sets given connection for current thread. Con can be <code>null</code> which
   * effectively removes connection if it exists.
   * 
   * @param con connection towards database
   */
  public static void setConnection(Connection con) {
    if(con == null) {
      connections.remove();
    } else {
      connections.set(con);
    }
  }

  /**
   * Gets connection for current thread.
   * 
   * @return connection toward database
   */
  public static Connection getConnection() {
    return connections.get();
  }

}