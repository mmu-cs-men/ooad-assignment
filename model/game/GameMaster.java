package model.game;

import model.board.Board;
import model.board.Cell;
import model.board.CellPosition;
import model.exceptions.NoPieceException;
import model.exceptions.NotYourPieceException;
import model.exceptions.PieceMoveException;
import model.listeners.CaptureListener;
import model.listeners.WinListener;
import model.pieces.Piece;
import utils.CircularLinkedList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The GameMaster class is an abstract class responsible for managing the core
 * logic and flow of the game. It handles player turns, piece movements, win
 * condition notifications, and interactions with the game board.
 * <p>
 * This class acts as the interface or intermediary between the low-level Model
 * code. In this way, this class implements the Facade pattern, and abstracts
 * the interaction with the Model and the outside world. If any GUI or
 * application wants to interact with the game, it must go through the
 * {@code GameMaster}.
 * <p>
 * One way to think about this class is to imagine a game of chess with players,
 * a referee, and a board. The player cannot touch the board or pieces directly.
 * They must ask the referee (GameMaster) to move it for them. The referee,
 * using his knowledge of the game, will do so if legal.
 * <p>
 * Specific games like Kwazam Chess must inherit this class, which is useful if
 * extra game-specific functionality/tracking is needed.
 *
 * @param <T> The type of the game board, which must extend the Board class.
 * @author Harris Majeed
 */
public abstract class GameMaster<T extends Board> implements CaptureListener
{
    /**
     * Represents the board used by the GameMaster to manage gameplay. It is
     * generic to support different types of boards for various games.
     */
    protected final T board;
    /**
     * Stores a circularly linked list of Player objects representing the
     * participants in the game. This ensures that iteration over players cycles
     * continuously, providing a loop mechanism.
     *
     */
    private final CircularLinkedList<Player> players;

    /**
     * A list of registered {@link WinListener} instances that are notified when
     * a player wins the game. Win listeners implementing the
     * {@link WinListener} interface can subscribe to be notified upon a win
     * event.
     */
    private final ArrayList<WinListener> winListeners = new ArrayList<>();

    /**
     * Tracks the count of turns that have occurred in the game. Starts off at
     * 0.
     */
    protected int turnCount = 0;
    /**
     * An iterator for traversing through the list of players participating in
     * the game.
     */
    private Iterator<Player> playerIterator;
    /**
     * Represents the player whose turn is currently active in the game. The
     * active player is updated during each turn of the gameplay.
     */
    private Player currentPlayer;

    /**
     * Creates a new GameMaster with the specified board and list of players.
     *
     * @param board   The specific game board to use.
     * @param players The circular list of players in the game.
     *
     * @author Harris Majeed
     */
    public GameMaster(T board, CircularLinkedList<Player> players)
    {
        this.board = board;
        this.players = players;
        this.playerIterator = players.circularIterator();
        this.currentPlayer = this.playerIterator.next();

        this.board.registerCaptureListener(this);
    }

    /**
     * Attempts to move a piece from one cell position to another.
     *
     * @param fromCellPos The starting cell position.
     * @param toCellPos   The target cell position.
     * @throws NoPieceException      If there is no piece at the starting cell.
     * @throws NotYourPieceException If the piece does not belong to the current
     *                               player.
     * @throws PieceMoveException    If the move path is obstructed or invalid.
     *
     * @author Harris Majeed
     */
    public void movePiece(CellPosition fromCellPos, CellPosition toCellPos)
    {
        Piece piece = this.board.getPieceAt(fromCellPos)
                .orElseThrow(NoPieceException::new);

        if (piece.getOwner() != this.currentPlayer)
        {
            throw new NotYourPieceException();
        }

        LinkedList<CellPosition> path = piece
                .getPotentialPath(fromCellPos, toCellPos)
                .orElseThrow(PieceMoveException::new);

        CellPosition lastPos = path.removeLast();

        if (!piece.canJump() && this.board.isPathObstructed(path)
                || this.board.hasFriendlyPieceAt(lastPos, currentPlayer))
        {
            throw new PieceMoveException();
        }

        this.board.movePiece(fromCellPos, toCellPos);
    }

    /**
     * Retrieves the player whose turn is currently active in the game.
     * <p>
     *
     * @return The player currently taking a turn in the game.
     *
     * @author Harris Majeed
     */
    public Player getCurrentPlayer()
    {
        return this.currentPlayer;
    }

    /**
     * Sets the specified player as the current player.
     *
     * @param currentPlayer The player who will become the active participant.
     *
     * @author Harris Majeed
     */
    public void setCurrentPlayer(Player currentPlayer)
    {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Retrieves an unmodifiable list of all players participating in the game.
     * <p>
     *
     * @return An unmodifiable list of the current players.
     *
     * @author Harris Majeed
     */
    public List<Player> getPlayers()
    {
        return List.copyOf(this.players);
    }

    /**
     * Sets the players participating in the game to a new collection of players.
     * <p>
     * This method clears the existing player list, adds the new players, and
     * reinitializes the circular iterator. If the turn count is even, the
     * currently active player is updated according to the new list.
     *
     * @param newPlayers The new circular list of players to cycle through.
     *
     * @author Harris Majeed
     */
    public void setPlayers(CircularLinkedList<Player> newPlayers)
    {
        this.players.clear();
        this.players.addAll(newPlayers);
        this.playerIterator = this.players.circularIterator();

        if (this.turnCount % 2 == 0)
        {
            this.currentPlayer = this.playerIterator.next();
        }
    }

    /**
     * Advances the turn to the next player and increments the turn count.
     *
     * @author Harris Majeed
     */
    public void advanceTurn()
    {
        this.currentPlayer = this.playerIterator.next();
        this.turnCount++;
    }

    /**
     * Registers a {@link WinListener} to be notified when a player wins.
     *
     * @param listener The win listener to register.
     *
     *  @author Harris Majeed
     */
    public void registerWinListener(WinListener listener)
    {
        this.winListeners.add(listener);
    }

    /**
     * Notifies all registered {@link WinListener} instances that a player has
     * won.
     *
     * @param player The winning player.
     *
     * @author Harris Majeed
     */
    public void notifyWinListeners(Player player)
    {
        for (WinListener listener : this.winListeners)
        {
            listener.onWin(player);
        }
    }

    /**
     * Handles the capture event for a piece. If the captured piece is a
     * critical piece, the owning player is removed. If only one player remains,
     * that player is declared the winner.
     * <p>
     * Note: This method looks at the amount of players remaining because there
     * might be game in the future that has more than 2 players.
     *
     * @param piece The piece that was captured.
     */
    @Override
    public void onCapture(Piece piece)
    {
        if (!piece.isCriticalPiece())
        {
            return;
        }

        for (Player player : this.players)
        {
            if (piece.getOwner() == player)
            {
                this.players.remove(player);
                break;
            }
        }

        if (this.players.size() == 1)
        {
            this.notifyWinListeners(this.players.getFirst());
        }
    }

    public int getTurnCount()
    {
        return this.turnCount;
    }

    public void setTurnCount(int turnCount)
    {
        this.turnCount = turnCount;
    }

    public List<List<String>> getCellsStringRepresentation()
    {
        List<List<String>> formattedCells = new ArrayList<>();

        for (List<Cell> row : this.board.getCells())
        {
            for (Cell cell : row)
            {
                formattedCells.add(List.of(cell.getStringRepresentation()));
            }
        }
        return formattedCells;
    }
}