package board.dto;

import java.util.Scanner;

public class ListMenu {

  Scanner sc = new Scanner(System.in);

  public void menu1() {
    System.out.println("[게시물 목록]");
    System.out.println("----------------------------------------------");
    System.out.println("no" + "\t\twriter" + "\t\t\tdate" + "\t\t\t\t\ttitle");
  }

  public void menu2() {
    System.out.println("----------------------------------------------");
    System.out.println("메인 메뉴 : 1.Create | 2.Read | 3.Clear | 4.Exit");
    System.out.print("메뉴 선택 : ");
  }

  public int select() {
    int num = sc.nextInt(); // throw 추가 변경
    while (true) {
      if ((num != 1) && (num != 2) && (num != 3) && (num != 4)) {
        System.out.println("숫자 재입력");
        num = sc.nextInt();
      } else {
        break;
      }
    }
    return num;
  }

}
