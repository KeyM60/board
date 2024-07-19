package board.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC_conn {

  private static Connection conn = null;
  private static String url = "jdbc:mysql://localhost:3306/boardbase";
  private static String id = "root";
  private static String pwd = "thrhrl12@";

  public static final JDBC_conn instance = new JDBC_conn();

  private JDBC_conn() {
  }

  public static JDBC_conn getInstance() {
    return instance;
  }

  public Connection open() {
    try {
      conn = DriverManager.getConnection(url, id, pwd);
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return conn;
  }

  public void close() {
    try {
      conn.close();
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
  }
}
