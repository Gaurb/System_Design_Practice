package TicTacToePractice.user;

import TicTacToePractice.game.Board;
import TicTacToePractice.game.Cell;
import TicTacToePractice.user.strategy.BotPlayingStrategy;

public class BotPlayer <K> extends Player<K>{

    BotPlayingStrategy botPlayingStrategy;
    BotPlayer(BotPlayingStrategy playingStrategy){
        this.botPlayingStrategy = playingStrategy;
    }
    @Override
    Cell makeMove(Board board) {
        return botPlayingStrategy.getNextMove(board);
    }
}
