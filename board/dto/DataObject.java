package board.dto;

import board.vo.Board;
import java.util.Scanner;

public class DataObject {

  public static void create() throws Exception {
    Scanner sc = new Scanner(System.in);
    Board board = new Board();
    System.out.println("[새 게시물 입력]");
    System.out.print("제목 : ");
    board.setBtitle(sc.nextLine());
    System.out.print("내용 : ");
    board.setBcontent(sc.nextLine());
    System.out.print("작성자 : ");
    board.setBwriter(sc.nextLine());
    System.out.println("-----------------------------------------------------");
    System.out.println("보조 메뉴 : 1.OK | 2.Cancel");
    System.out.print("메뉴 선택 : ");
    int num = Integer.parseInt(sc.nextLine());

    if (num == 1) {
      int successNum = CreateDB.boardInsert(board);
      if (successNum == 1) {
        System.out.println("DB 입력 성공");
      } else {
        throw new Exception("DB 입력 실패");
      }
    } else if (num == 2) {
      System.out.println("입력 취소");

    } else {
      System.out.println("입력 범위 초과");
    }
  }
}
