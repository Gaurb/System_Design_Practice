package TicTacToePractice.game;

public class Cell {
    int row;
    int col;
    Symbol symbol;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.symbol = Symbol.EMPTY; // Initialize as empty
    }

    public char getSymbol() {
        return symbol.getSymbol();
    }

    public int getRow(){
        return this.row;
    }
    public int getCol(){
        return this.col;
    }
}
