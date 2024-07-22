package BoardSystem;

import BoardSystem.boardIO.JDBC_conn;
import BoardSystem.dto.BoardManager;


public class BoardMain {

  public static void main(String[] args) {

    BoardManager bm = BoardManager.getInstance();

    boolean start = false;
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      while (!start) {


        bm.menu_up(); // 상단 메뉴 처리

        bm.readALLBoard(); // 게시판 리스트

        bm.menu_down(); // 하단 메뉴 처리

        int num = bm.mainListNum();
        switch (num) {
          case 1:
            System.out.println("create&insert 메소드 실행");
            bm.createData();
            continue;
          case 2:
            System.out.println("read 메소드 실행");
            bm.readDB(); // **보조 메뉴 추가 필요
            continue;
          case 3:
            // Clear(); - 번호 선택 후 삭제 -- 리스트, 삭제, 글 번호 재정렬
            System.out.println("deleteAll 메소드 실행");
            bm.deleteALLBoard();
            continue;
          case 4:
            System.out.println("종료");
            JDBC_conn.getInstance().close();
            start = true;
            break;

        }
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
