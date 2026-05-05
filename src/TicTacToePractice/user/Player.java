package TicTacToePractice.user;

import TicTacToePractice.game.Board;
import TicTacToePractice.game.Cell;
import TicTacToePractice.game.Symbol;

public abstract class Player <K>{
    K id;
    Symbol symbol;

    abstract Cell makeMove(Board board);
}
