package BoardSystem.dto;

import BoardSystem.service.MenuFunction;
import lombok.Getter;

public class BoardManager extends MenuFunction {

@Getter
private static final BoardManager instance = new BoardManager();

private BoardManager() {}

}
