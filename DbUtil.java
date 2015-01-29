package tw.com.sevenocean.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DbUtil implements java.io.Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Execute the query and get the return data to hash map.
   * @param String sqlStatement
   * @param String[] columnType
   * @param String[] columnValue
   * @return HashMap hmRtnData
   * @author MikiSayaka 2013.09.24 
   * */
  public static HashMap<String, String> executeQueryHm(String sqlStatement, String[] columnType, String[] columnValue) throws Exception {

    Connection conn = DbConnector.getConnection();
    
    HashMap<String, String> hmRtnData = null;
    PreparedStatement stmt = null;
    ResultSetMetaData metaData = null;
    ResultSet result = null;

    try {
      stmt = conn.prepareStatement(sqlStatement);
      if (!CommonUtil.StrIsNull(columnType)) {
        for (int i = 0; i < columnType.length; i++) {
          //  FIXME need to handle the class cast exception.
          if ("String".equals(columnType[i])) {
            stmt.setString(i + 1, columnValue[i]);
          } else if ("int".equals(columnType[i])) {
            stmt.setInt(i + 1, Integer.parseInt(columnValue[i]));
          }
        }
      }
      result = stmt.executeQuery();
      metaData = result.getMetaData();
      if (metaData.getColumnCount() > 0) {
        hmRtnData = new HashMap<String, String>();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
          hmRtnData.put(metaData.getColumnName(i), result.getString(i));
        }
      }
    } catch (SQLException e) {
      // TODO Handle the SQL exception
      e.printStackTrace();
    }
    finally {
      DbConnector.clsoeResultSet(result);
      DbConnector.closeStatement(stmt);
      DbConnector.closeConnection(conn);
    }
    return hmRtnData;
  }
  
  /**
   * Execute the query and get the return data to array list.
   * @param String sqlStatement
   * @param String[] columnType
   * @param String[] columnValue
   * @return HashMap hmRtnData
   * @author MikiSayaka 2013.09.24 
   * */
  public static List<HashMap<String, String>> executeQueryLt(String sqlStatement, String[] columnType, String[] columnValue) throws Exception {

    Connection conn = DbConnector.getConnection();
    List<HashMap<String, String>> ltRtnData = null;
    HashMap<String, String> hmRtnData = null;
    PreparedStatement stmt = null;
    ResultSetMetaData metaData = null;
    ResultSet result = null;

    try {
      stmt = conn.prepareStatement(sqlStatement);

      if (!CommonUtil.StrIsNull(columnType)) {
        for (int i = 0; i < columnType.length; i++) {
          //  FIXME need to handle the class cast exception.
          if ("String".equals(columnType[i])) {
            stmt.setString(i + 1, columnValue[i]);
          } else if ("int".equals(columnType[i])) {
            stmt.setInt(i + 1, Integer.parseInt(columnValue[i]));
          }
        }
      }
      result = stmt.executeQuery();
      metaData = result.getMetaData();
      if (metaData.getColumnCount() > 0) {
        ltRtnData = new ArrayList<HashMap<String, String>>();
        while(result.next()) {
          hmRtnData = new HashMap<String, String>();
          for (int i = 1; i <= metaData.getColumnCount(); i++) {
            hmRtnData.put(metaData.getColumnName(i), result.getString(i));
          }
          ltRtnData.add(hmRtnData);
        }
      }
    } catch (SQLException e) {
      // TODO Handle the SQL exception
      e.printStackTrace();
    }
    finally {
      DbConnector.clsoeResultSet(result);
      DbConnector.closeStatement(stmt);
      DbConnector.closeConnection(conn);
    }
    return ltRtnData;
  }

  /**
   * Insert data into database.
   * @param String sqlStatement
   * @param String[] columnType
   * @param String[] columnValue
   * @return int result
   * @author MikiSayaka 2013.09.24 
   * */
  public static int executeInsertData(String sqlStatement, String[] columnType, String[] columnValue) throws Exception {
    int result = 0;
    Connection conn = DbConnector.getConnection();
    PreparedStatement stmt = null;
    
    try {
      stmt = conn.prepareStatement(sqlStatement);
      if (!CommonUtil.StrIsNull(columnType)) {
        for (int i = 0; i < columnType.length; i++) {
          //  FIXME need to handle the class cast exception.
          if ("String".equals(columnType[i])) {
            stmt.setString(i + 1, columnValue[i]);
          } else if ("int".equals(columnType[i])) {
            stmt.setInt(i + 1, Integer.parseInt(columnValue[i]));
          }
        }
      }
      result = stmt.executeUpdate();
    } catch (SQLException e) {
      // TODO Handle the SQL exception
      e.printStackTrace();
    }
    finally {
      DbConnector.closeStatement(stmt);
      DbConnector.closeConnection(conn);
    }
    return result;
  }
}
