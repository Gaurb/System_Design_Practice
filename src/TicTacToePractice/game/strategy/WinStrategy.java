package TicTacToePractice.game.strategy;

import TicTacToePractice.game.Board;
import TicTacToePractice.game.Cell;

public interface WinStrategy {
    boolean checkWin(Board board, Cell cell);
}
