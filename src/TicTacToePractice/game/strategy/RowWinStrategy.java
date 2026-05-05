package TicTacToePractice.game.strategy;

import TicTacToePractice.game.Board;
import TicTacToePractice.game.Cell;

public class RowWinStrategy implements WinStrategy {

    @Override
    public boolean checkWin(Board board, Cell cell) {
        int row = cell.getRow();
        char symbol = cell.getSymbol();

        int n = board.getSize();

        for (int col = 0; col < n; col++) {
            Cell cell1 = board.getCell(row, col);
            if(cell1.getSymbol() != symbol) return false;
        }

        return true;
    }
}
