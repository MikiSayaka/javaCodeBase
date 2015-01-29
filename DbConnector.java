package tw.com.sevenocean.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class DbConnector implements java.io.Serializable {

  private static final long serialVersionUID = 1L;

  private static String driver = "com.mysql.jdbc.Driver";
  private static String url = "jdbc:mysql://localhost:3306/sevenocean";
  private static String user = "sevenocean";
  private static String password = "12105486";

  /**
   * Get the connection to database.
   * @return Connection conn
   * @author MikiSayaka 2013.09.24
   * */
  public static Connection getConnection() throws Exception {
    Connection conn = null;
    Class.forName(driver);
    conn = DriverManager.getConnection(url, user, password);
    return conn;
  }

  /**
   * Close the connection to database
   * @param Connection conn
   * @author MikiSayaka 2013.09.24
   * */
  public static void closeConnection(Connection conn) throws Exception {
    conn.close();
  }

  /**
   * Close the statement
   * @param Statement stmt
   * @author MikiSayaka 2013.09.24
   * */
  public static void closeStatement(Statement stmt) throws Exception {
    stmt.close();
  }

  /**
   * Close the Prepared Statement
   * @param PreparedStatement stmt
   * @author MikiSayaka 2013.09.24
   * */
  public static void closeStatement(PreparedStatement stmt) throws Exception {
    stmt.close(); 
  }

  /**
   * Close the result set
   * @param ResultSet result
   * @author MikiSayaka 2013.09.23
   * */
  public static void clsoeResultSet(ResultSet result) throws Exception {
    result.close();
  }
}
