package TicTacToePractice.game.strategy;

import TicTacToePractice.game.Board;
import TicTacToePractice.game.Cell;

public class ColWinStrategy implements WinStrategy{
    @Override
    public boolean checkWin(Board board, Cell cell) {
        int col = cell.getCol();
        char symbol = cell.getSymbol();

        int n = board.getSize();

        for(int row =0;row<n;row++){
            Cell cell1 = board.getCell(row,col);
            if(cell1.getSymbol() != symbol) return false;
        }

        return true;
    }
}
