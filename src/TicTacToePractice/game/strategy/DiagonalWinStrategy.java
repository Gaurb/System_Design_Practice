package TicTacToePractice.game.strategy;

import TicTacToePractice.game.Board;
import TicTacToePractice.game.Cell;

public class DiagonalWinStrategy implements WinStrategy {

    @Override
    public boolean checkWin(Board board, Cell cell) {
        char symbol = cell.getSymbol();

        int row = 0, col = 0;
        int n = board.getSize();

        while (row < n && col < n) {
            Cell cell1 = board.getCell(row, col);
            if (cell1.getSymbol() != symbol) return false;
            row++;
            col++;
        }

        row = 0;
        col = n - 1;

        while (row < n && col >= 0) {
            Cell cell1 = board.getCell(row, col);
            if (cell1.getSymbol() != symbol) return false;
            row++;
            col--;
        }

        return true;
    }
}
