package TicTacToePractice.user.strategy;

import TicTacToePractice.game.Board;
import TicTacToePractice.game.Cell;

public interface BotPlayingStrategy {
    Cell getNextMove(Board board);
}
