package board.dto;

import board.dao.JDBC_conn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ClearDB {


  public static void clear() {
    Scanner sc = new Scanner(System.in);
    Connection conn = JDBC_conn.getInstance().open();

    System.out.println("[게시물 전체 삭제]");
    System.out.println("----------------------------------------------");
    System.out.println("보조 메뉴 1.OK | 2.Cancel");
    System.out.print("메뉴선택 : ");
    int num = Integer.parseInt(sc.nextLine());

    if (num == 2) {
      System.out.println("취소");
    } else if (num == 1) {
      String query = "DELETE FROM board";

      try {
        PreparedStatement pstmt = null;
        pstmt = conn.prepareStatement(query);
        pstmt.executeUpdate();
        System.out.println("모두 삭제 완료");
        pstmt.close();
        conn.close();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    } else {
      System.out.println("잘못 입력");
      clear();
    }
  }
}