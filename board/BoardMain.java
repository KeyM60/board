package board;

import board.dao.JDBC_conn;
import board.dto.ClearDB;
import board.dto.ListMenu;
import board.dto.DataObject;
import board.dto.ReadALLBoard;
import board.dto.ReadDB;

public class BoardMain {

  public static void main(String[] args) {

    boolean start = false;

    ListMenu listMenu = new ListMenu();
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      while (!start) {

        listMenu.menu1(); // 상단 메뉴 처리
    /*
    제목 db데이터 출력부 *all read / content 제외
    */
        ReadALLBoard.boardAllList();

        listMenu.menu2(); // 하단 메뉴 처리

        int num = listMenu.select();
        switch (num) {
          case 1:
            System.out.println("create 메소드 실행");
            DataObject.create();
            continue;
          case 2:
            System.out.println("read 메소드 실행");
            ReadDB.boardRead(); // **보조 메뉴 추가 필요
            continue;
          case 3:
            // Clear(); - 번호 선택 후 삭제 -- 리스트, 삭제, 글 번호 재정렬
            System.out.println("clear 메소드 실행");
            ClearDB.clear();
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
