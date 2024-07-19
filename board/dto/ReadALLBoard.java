package board.dto;

import board.dao.JDBC_conn;
import board.vo.Board;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ReadALLBoard {


  public static void boardAllList() {
    Connection conn = JDBC_conn.getInstance().open();
    ResultSet rs = null;
    ArrayList<Board> boardlist = new ArrayList<Board>();
    String query = "SELECT bno, bwriter, bdate, btitle FROM board";

    try {
      PreparedStatement pstmt = null;
      pstmt = conn.prepareStatement(query);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        Board board = new Board();
        board.setBno(rs.getInt("bno"));
        board.setBwriter(rs.getString("bwriter"));
        board.setBdate(rs.getDate("bdate"));
        board.setBtitle(rs.getString("btitle"));
        boardlist.add(board);
      }

      for (Board board : boardlist) {
        System.out.println(board.getBno() + "\t\t\t" + board.getBwriter() + "\t\t\t\t\t"
            + board.getBdate() + "\t\t" + board.getBtitle());
      }
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
