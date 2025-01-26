package model.serialization;

import model.board.Board;
import model.game.GameMaster;
import model.game.Player;
import utils.CircularLinkedList;

public class SaveLoadManager
{
    private final GameMaster<?> gameMaster;
    private final Board board;

    public SaveLoadManager(GameMaster<?> gameMaster, Board board)
    {
        this.gameMaster = gameMaster;
        this.board = board;
    }

    public GameState saveGame()
    {
        return new GameState(
                gameMaster.getTurnCount(),
                gameMaster.getPlayers(),
                gameMaster.getCurrentPlayer(),
                board.getCells()
        );
    }

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
