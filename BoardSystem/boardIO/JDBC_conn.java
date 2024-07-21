package BoardSystem.boardIO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import lombok.Getter;

public class JDBC_conn {

  private static Connection conn = null;
  private static String url = "jdbc:mysql://localhost:3306/boardbase?serverTimezone=Asia/Seoul";
  private static String id = "root";
  private static String pwd = "thrhrl12@";

  /*
  @Getter
  @Setter
  private PreparedStatement pstmt = null;
  // CRUD 입력 대기중에 버퍼 인터럽트 발생 가능

  @Getter
  @Setter
  private ResultSet rs = null;
*/

  @Getter
  private static final JDBC_conn instance = new JDBC_conn();

  private JDBC_conn() {
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
