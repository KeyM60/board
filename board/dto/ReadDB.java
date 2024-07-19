package board.dto;

import board.dao.JDBC_conn;
import board.vo.Board;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class ReadDB {

  public void readMenu(int num) throws SQLException {
    Connection conn = JDBC_conn.getInstance().open();
    PreparedStatement pstmt = null;
    Scanner sc = new Scanner(System.in);

    System.out.println("보조 메뉴: 1.Update | 2.Delete | 3.List");
    System.out.print("메뉴 선택 : ");
    int selectNum = Integer.parseInt(sc.nextLine());

    if (selectNum == 1) {
      System.out.println("update 선택");
      System.out.print("내용 변경 :  ");
      String content = sc.nextLine();
      String query = "UPDATE board set bcontent = ? WHERE bno = ?"; // 내용만 일단 변경

      pstmt = conn.prepareStatement(query);
      pstmt.setString(1, content);
      pstmt.setInt(2, num);
      pstmt.executeQuery();
      pstmt.close();

    } else if (num == 2) {
      System.out.println("delete 선택");
      String query = "DELETE * from board WHERE bno = ?";
      System.out.print("삭제 성공");
      pstmt = conn.prepareStatement(query);
      pstmt.executeQuery();
      pstmt.close();

    } else if (num == 3) {
      boardRead();
    } else {
      System.out.println("입력 오류");
    }
  }

  public static void boardRead() {
    Connection conn = JDBC_conn.getInstance().open();
    Scanner sc = new Scanner(System.in);

    System.out.println("[게시물 읽기]");
    System.out.print("bno : ");
    int num = Integer.parseInt(sc.nextLine());
    System.out.println("##############");

    String query = "SELECT FROM board WHERE bno=?";

    try {
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, num);
      rs = pstmt.executeQuery();

      System.out.println(rs.next());
      Board board = new Board(rs.getInt("bno"), rs.getString("btitle"),
          rs.getString("bcontent"), rs.getString("bwriter")
          , (rs.getDate("bdate")));

      System.out.println("번호 : " + board.getBno());
      System.out.println("제목 : " + board.getBtitle());
      System.out.println("내용 : " + board.getBcontent());
      System.out.println("번호 : " + board.getBno());
      System.out.println("날짜 : " + board.getBdate());
      System.out.println("----------------------------------");
/*
      ReadDB rDB = new ReadDB();
      rDB.readMenu(num);
*/
      pstmt.close();
      conn.close();

    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
