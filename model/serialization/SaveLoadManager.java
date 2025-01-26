package model.serialization;

import model.board.Board;
import model.game.GameMaster;
import model.game.Player;
import utils.CircularLinkedList;

/**
 * Manages saving and loading the state of a game.
 * <p>
 * This class provides methods to save the current game state to a {@link GameState}
 * and to load a previously saved state back into the current game.
 * @author Abdullah Hawash
 */
public class SaveLoadManager
{
    private final GameMaster<?> gameMaster;
    private final Board board;

    /**
     * Constructs a new manager with the specified game master and board.
     *
     * @param gameMaster the game master overseeing the current game session
     * @param board      the board on which the game is played
     *
     * @author Abdullah Hawash
     */
    public SaveLoadManager(GameMaster<?> gameMaster, Board board)
    {
        this.gameMaster = gameMaster;
        this.board = board;
    }

    /**
     * Saves the current state of the game.
     * <p>
     * This method captures the current turn count, all players,
     * the current player, and the state of the board’s cells.
     *
     * @return a {@link GameState} representing the current game configuration
     * @author Abdullah Hawash
     */
    public GameState saveGame()
    {
        return new GameState(
                gameMaster.getTurnCount(),
                gameMaster.getPlayers(),
                gameMaster.getCurrentPlayer(),
                board.getCells()
        );
    }

    /**
     * Loads a previously saved state into the current game.
     * <p>
     * This method updates the turn count, reinitializes the list of players
     * in the game master, sets the current player, and restores the board's cells
     * based on the provided {@link GameState}.
     *
     * @param gameState the saved state to load
     * @author Abdullah Hawash
     */
    public void loadGame(GameState gameState)
    {
        gameMaster.setTurnCount(gameState.turnCount());

        CircularLinkedList<Player> newPlayers = new CircularLinkedList<>();
        newPlayers.addAll(gameState.players());
        gameMaster.setPlayers(newPlayers);

        gameMaster.setCurrentPlayer(gameState.currentPlayer());

        board.setCells(gameState.cells());
    }
}
