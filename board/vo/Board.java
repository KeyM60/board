package board.vo;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Board {

  private int bno;
  private String btitle;
  private String bcontent;
  private String bwriter;
  private Date bdate;

  public Board() {
  }
}
