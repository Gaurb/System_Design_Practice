package TicTacToePractice.game;

import java.util.logging.Logger;

public class Board {
    private static final Logger LOGGER = Logger.getLogger(Board.class.getName());
    Cell[][] cells;
    int size;

    public Board(int size){
        this.size = size;
        cells = new Cell[size][size];
        initializeBoard();
        LOGGER.info("Board initialized with size: " + size);
    }

    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public void displayBoard() {
        LOGGER.info("Displaying board:");
        System.out.println("\n  0 1 2");
        for (int i = 0; i < size; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < size; j++) {
                System.out.print(cells[i][j].getSymbol());
                if (j < size - 1) System.out.print("|");
            }
            System.out.println();
            if (i < size - 1) System.out.println("  -----");
        }
        System.out.println();
    }

    public Cell getCell(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            LOGGER.warning("Invalid cell position: (" + row + ", " + col + ")");
            return null;
        }
        return cells[row][col];
    }

    public void setCell(int row, int col, Symbol symbol) {
        Cell cell = getCell(row, col);
        if (cell != null) {
            cell.symbol = symbol;
            LOGGER.info("Cell (" + row + ", " + col + ") set to " + symbol);
        }
    }

    public int getSize(){
        return size;
    }
}
