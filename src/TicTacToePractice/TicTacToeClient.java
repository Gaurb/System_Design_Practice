package TicTacToePractice;

import TicTacToePractice.game.Board;
import TicTacToePractice.game.Symbol;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.ConsoleHandler;
import java.util.logging.SimpleFormatter;

public class TicTacToeClient {
    private static final Logger LOGGER = Logger.getLogger(TicTacToeClient.class.getName());

    public static void main(String[] args) {
        // Configure logging
        setupLogging();

        LOGGER.info("========== TIC TAC TOE GAME STARTED ==========");

        // Create and display empty board
        LOGGER.info("Creating a 3x3 board...");
        Board board = new Board(3);

        LOGGER.info("Displaying initial empty board:");
        board.displayBoard();

        // Simulate some game moves
        LOGGER.info("Player X makes first move at (0, 0)");
        board.setCell(0, 0, Symbol.X);
        board.displayBoard();

        LOGGER.info("Player O makes move at (1, 1)");
        board.setCell(1, 1, Symbol.O);
        board.displayBoard();

        LOGGER.info("Player X makes move at (0, 1)");
        board.setCell(0, 1, Symbol.X);
        board.displayBoard();

        LOGGER.info("Player O makes move at (2, 2)");
        board.setCell(2, 2, Symbol.O);
        board.displayBoard();

        LOGGER.info("Player X makes move at (0, 2)");
        board.setCell(0, 2, Symbol.X);
        board.displayBoard();

        // Try invalid move
        LOGGER.warning("Attempting to place symbol at invalid position (5, 5)");
        board.setCell(5, 5, Symbol.X);

        // Try to place on already occupied cell
        LOGGER.warning("Attempting to place symbol on already occupied cell (0, 0)");
        board.setCell(0, 0, Symbol.O);
        board.displayBoard();

        LOGGER.info("========== GAME DEMONSTRATION COMPLETED ==========");
    }

    private static void setupLogging() {
        LOGGER.setLevel(Level.ALL);

        // Create console handler
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);

        // Create formatter
        SimpleFormatter formatter = new SimpleFormatter();
        handler.setFormatter(formatter);

        // Add handler to logger
        LOGGER.addHandler(handler);

        // Prevent logs from going to parent handlers
        LOGGER.setUseParentHandlers(false);
    }
}

