package BoardSystem.service;

import BoardSystem.boardIO.JDBC_conn;
import BoardSystem.dao.Data_dao;
import BoardSystem.dto.Board;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public abstract class MenuFunction extends Data_dao {

  Scanner sc = new Scanner(System.in);
  static Connection conn = JDBC_conn.getInstance().open();
  // static PreparedStatement pstmt = null;
  // static ResultSet rs = null;

  // 입력 번호 체크 기능
  public int checkNum(int num) {
    String checkNum = sc.nextLine();
    int count = 0;
    int returnNum=0;
    for (int i = 1; i < num + 1; i++) {
      if (Objects.equals(checkNum, "" + i)) {
        returnNum = i;
      } else {
        count++;
      }
    }
    if (count == num) {
      System.out.println("다시 입력해주세요.");
      returnNum = checkNum(num);
    }
    return returnNum;
  }

  // 숫자인지 확인
  public static boolean isNumberic(String str) {
    return str.matches("[+-]?\\d*(\\.\\d+)?");
  }

  // 리스트 상단 내용
  public void menu_up() {
    System.out.println("[게시물 목록]");
    System.out.println("----------------------------------------------");
    System.out.println("no" + "\t\twriter" + "\t\t\tdate" + "\t\t\t\t\ttitle");
  }

  // 리스트 하단 내용
  public void menu_down() {
    System.out.println("----------------------------------------------");
    System.out.println("메인 메뉴 : 1.Create | 2.Read | 3.Clear | 4.Exit");
    System.out.print("메뉴 선택 : ");
  }

  // 리스트 처음메뉴 번호 선택
  public int mainListNum() {

    //String checkNum = sc.nextLine();
    /*while(true) {
      if (Objects.equals(checkNum, "1") || Objects.equals(checkNum, "2") || Objects.equals(
          checkNum, "3") || Objects.equals(checkNum, "4")) {
        selectNum = Integer.parseInt(checkNum);
        break;
      }
      else  {
        System.out.print("다시 입력해주세요(1, 2, 3, 4) :  ");
        checkNum = sc.nextLine();
      }
    }*/
    return checkNum(4);
  }

  // 새 게시글 작성 기능
  public void createData() {
    Board board = new Board();
    String checkContent;
    System.out.println("[새 게시물 입력]");
    System.out.print("제목 : ");
    while (true) { // 제목 필수
      checkContent = sc.nextLine();
      if (!checkContent.isEmpty()) {
        break;
      } else {
        System.out.println("제목은 필수 입니다");
      }
    }
    board.setBtitle(checkContent);
    System.out.print("내용 : ");
    board.setBcontent(sc.nextLine());
    System.out.print("작성자 : ");
    while (true) { // 작성자 필수
      checkContent = sc.nextLine();
      if (!checkContent.isEmpty()) {
        break;
      } else {
        System.out.println("작성자명 필수 입니다");
      }
    }
    board.setBwriter(checkContent);
    System.out.println("-----------------------------------------------------");
    System.out.println("보조 메뉴 : 1.OK | 2.Cancel");
    System.out.print("메뉴 선택 : ");
    int num = checkNum(2);

    if (num == 1) {
      if (insertDB(board)) {
        System.out.println("DB 입력 성공");
      } else {
        System.out.println("DB 입력 실패");
      }
    } else if (num == 2) {
      System.out.println("입력 취소");
    } else {
      System.out.println("입력 범위 초과");
    }
  }

  // 게시글 마지막 넘버 확인
  public int boardLastNum() {
    String query = "SELECT bno FROM board";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int result = 0;
    try {
      pstmt = conn.prepareStatement(query);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        result = rs.getInt("bno");
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return result;
  }

  // 데이터 삽입
  @Override
  public boolean insertDB(Board board) {
    String query = "INSERT INTO board VALUES (?, ?, ?, ?, ?);";
    PreparedStatement pstmt = null;
    boolean bl = false;

    try {
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, boardLastNum() + 1);
      pstmt.setString(2, board.getBtitle());
      pstmt.setString(3, board.getBcontent());
      pstmt.setString(4, board.getBwriter());
      pstmt.setDate(5, Date.valueOf(LocalDate.now()));
      int result = pstmt.executeUpdate();
      if (result == 1) {
        System.out.println("새 게시글 등록");
        bl = true;
      } else if (result == 0) {
        System.out.println("등록 실패");
      }
      pstmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      return bl;
    }
  }

  // 게시물 모두 보기
  @Override
  public ArrayList<Board> readALLBoard() {
    ArrayList<Board> boardlist = new ArrayList<Board>();
    String query = "SELECT bno, bwriter, bdate, btitle FROM board";
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
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
        System.out.printf("%-6d%-12s%tY-%tm-%-6td%-10s%n",board.getBno(), board.getBwriter(),board.getBdate(),board.getBdate(),board.getBdate(),board.getBtitle());
      }
      rs.close();
      pstmt.close();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return boardlist;
  }

  // 단일 게시글 읽기
  @Override
  public void readDB() {
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    System.out.println("[게시물 읽기]");
    System.out.print("bno : ");
    String bnoS = sc.nextLine();
    int num = Integer.parseInt(bnoS);
    while(true) {
      if (isNumberic(bnoS) && num<= boardLastNum()) {
        System.out.println(boardLastNum()); // 수정 필요
        break;
      } else {
        System.out.println("다시 입력해주세요.");
        System.out.println(boardLastNum());
        bnoS = sc.nextLine();
      }
    }
    System.out.println("##############");

    String query = "SELECT * FROM board WHERE bno=?";

    try {
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

      System.out.println("보조 메뉴: 1.Update | 2.Delete | 3.List");
      System.out.print("메뉴 선택 : ");

      int selectNum = checkNum(3);

      if (selectNum == 1) {
        updateDB(num, board);
      } else if (selectNum == 2) {
        deleteDB(num);
      } else if (selectNum == 3) {
    /*menu_up();
    readALLBoard();
    menu_down();
    mainListNum();*/
      } else {
        System.out.println("입력 오류");
      }
      pstmt.close();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  // 게시글 수정
  @Override
  public void updateDB(int no, Board board) {
    PreparedStatement pstmt = null;
    System.out.println("update 선택");
    System.out.print("내용 변경 :  ");
    String content = sc.nextLine();
    String updateQuery = "UPDATE board set bcontent = ? WHERE bno = ?"; // 내용만 일단 변경
    try {
      pstmt = conn.prepareStatement(updateQuery);
      pstmt.setString(1, content);
      pstmt.setInt(2, no);
      pstmt.executeQuery();
      pstmt.close();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  // 게시글 전체 삭제
  @Override
  public void deleteALLBoard() {
    PreparedStatement pstmt = null;
    System.out.println("[게시물 전체 삭제]");
    System.out.println("----------------------------------------------");
    System.out.println("보조 메뉴 1.OK | 2.Cancel");
    System.out.print("메뉴선택 : ");
    int num = checkNum(2);

    if (num == 2) {
      System.out.println("게시글 전체 삭제 취소");
    } else if (num == 1) {
      String query = "DELETE FROM board";

      try {
        pstmt = conn.prepareStatement(query);
        pstmt.executeUpdate();
        System.out.println("모두 삭제 완료");
        pstmt.close();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    } else {
      System.out.println("잘못 입력되었습니다.");
      deleteALLBoard();
    }
  }

  // 게시글 삭제
  @Override
  public void deleteDB(int no) {
    PreparedStatement pstmt = null;
    System.out.println("delete 선택");
    String query = "DELETE * from board WHERE bno = ?";
    System.out.print("삭제 성공");
    try {
      pstmt = conn.prepareStatement(query);
      pstmt.executeQuery();
      pstmt.close();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
}
