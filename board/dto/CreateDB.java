package board.dto;

import board.dao.JDBC_conn;
import board.vo.Board;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDB {

  public static int boardNum() {
    // 제일 마지막 넘버 확인
    Connection conn = JDBC_conn.getInstance().open();
    String query = "SELECT bno FROM board";
    int result = 0;
    try {
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      pstmt = conn.prepareStatement(query);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        result = rs.getInt("bno");
      }
      conn.close();
    } catch (Exception e) {
      System.out.println(e);
    }
    return result;
  }

  public static int boardInsert(Board board) {
    Connection conn = JDBC_conn.getInstance().open();
    String query = "INSERT INTO board VALUES (?, ?, ?, ?, ?);";

    try {
      PreparedStatement pstmt = null;
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, boardNum() + 1);
      pstmt.setString(2, board.getBtitle());
      pstmt.setString(3, board.getBcontent());
      pstmt.setString(4, board.getBwriter());
      pstmt.setDate(5, Date.valueOf(java.time.LocalDate.now()));
      int result = pstmt.executeUpdate();
      if (result == 1) {
        System.out.println("게시글 저장");
      } else if (result == 0) {
        System.out.println("실패");
      }
      pstmt.close();
      conn.close();
      return 1;
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      return 0;
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }
}

