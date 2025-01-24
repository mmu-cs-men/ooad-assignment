package model.board;

import model.exceptions.PieceMoveException;
import model.game.Player;
import model.listeners.BoardVerticalEdgeListener;
import model.listeners.CaptureListener;
import model.pieces.Piece;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * This abstract class represents a game board composed of {@code Cell} objects.
 * It provides methods for managing the states and interactions of pieces on the
 * board, such as movement, capturing, and boundary checks. It also supports
 * events for actions like captures and reaching the edges of the board.
 * <p>
 * In the game, this class represents the central authority on piece positions
 * and movement. This class and only this class is responsible for keeping track
 * of piece positions on the board, which ensures that there is a single source
 * of truth in regard to board state. Indeed, this means that a {@code Cell}
 * does not know its own position.
 * <p>
 * This class heavily utilizes the Observer pattern to notify subscribers of
 * events that occur in the game, such as piece capture. The use of this pattern
 * came up naturally as it maintains encapsulation among pieces. Without the use
 * of this pattern, pieces may have to take in instances of the {@code Board}
 * class to gain information which isn't very logical and violates good
 * separation of concerns.
 *
 * @author Harris Majeed
 * @see Cell
 * @see KwazamBoard
 */
public abstract class Board
{


    /**
     * A list containing the players participating in the game. This field is
     * initialized via the constructor; however, is not used in the
     * {@code Board} class itself. Instead, it is used in its subclasses
     * (usually {@code populateCells()} to associate pieces with players).
     */
    protected final List<Player> players;

    /**
     * A two-dimensional matrix of cells representing the game board.
     */
    protected final ArrayList<ArrayList<Cell>> cells;

    /**
     * A list that holds all registered listeners which respond when a piece
     * reaches one of the vertical edges of the board.
     */
    private final ArrayList<BoardVerticalEdgeListener> verticalEdgeListeners = new ArrayList<>();

    /**
     * A list that holds all registered listeners which respond when a piece is
     * captured.
     */
    private final ArrayList<CaptureListener> captureListeners = new ArrayList<>();

    /**
     * Constructs a Board with the specified list of players. The board's cells
     * are initialized by calling the {@code populateCells} method.
     *
     * @param players the list of players participating in the game
     */
    public Board(List<Player> players)
    {
        this.players = players;
        this.cells = this.populateCells();
    }

    /**
     * Retrieves the piece located at the specified cell position on the board.
     * If no piece is present at the given position, an empty {@code Optional}
     * is returned.
     *
     * @param cellPos the position of the cell being queried; must be within
     *                bounds of the board
     * @return an Optional containing the piece located at the specified cell
     * position, or an empty Optional if the cell is unoccupied
     * @throws IllegalArgumentException if {@code cellPos} is out of bounds.
     */
    public Optional<Piece> getPieceAt(CellPosition cellPos)
    {
        Cell cell = this.getCell(cellPos);
        return cell.getPiece();
    }

    /**
     * Moves a piece from one cell position to another on the board. If there is
     * an existing piece at the destination position, a capture event is
     * triggered. If the moved piece reaches the topmost or bottommost row, a
     * vertical edge event is triggered.
     *
     * @param fromPos the starting cell position of the piece; must be within
     *                the bounds of the board and contain a piece.
     * @param toPos   the target cell position where the piece is to be moved;
     *                must be within the bounds of the board.
     * @throws PieceMoveException       if there is no piece at the starting
     *                                  position or if {@code toPos} has a
     *                                  friendly piece.
     * @throws IllegalArgumentException if either {@code fromPos} or
     *                                  {@code toPos} is out of bounds.
     */
    public void movePiece(CellPosition fromPos, CellPosition toPos)
    {
        Cell fromCell = this.getCell(fromPos);
        Piece piece = fromCell.getPiece().orElseThrow(PieceMoveException::new);

        Player currentPlayer = piece.getOwner();
        if (this.hasFriendlyPieceAt(toPos, currentPlayer))
        {
            throw new PieceMoveException();
        }

        Optional<Piece> existingPiece = this.getPieceAt(toPos);
        existingPiece.ifPresent(this::notifyCaptureListeners);
        this.removePiece(fromPos);
        Cell toCell = this.getCell(toPos);
        toCell.setPiece(piece);

        if (toPos.row() == 0 || toPos.row() == this.getBoardRows() - 1)
        {
            notifyVerticalEdgeListeners(piece);
        }
    }

    /**
     * Notifies all registered vertical edge listeners that a specific piece has
     * reached the topmost or bottommost row of the board.
     *
     * @param piece the piece that has reached the vertical edge of the board
     */
    private void notifyVerticalEdgeListeners(Piece piece)
    {
        for (BoardVerticalEdgeListener listener : this.verticalEdgeListeners)
        {
            listener.onBoardVerticalEdgeReached(piece);
        }
    }

    /**
     * Notifies all registered capture listeners that a specific piece has been
     * captured.
     *
     * @param piece the piece that has was captured
     */
    private void notifyCaptureListeners(Piece piece)
    {
        for (CaptureListener listener : this.captureListeners)
        {
            listener.onCapture(piece);
        }
    }

    /**
     * Removes the piece located at the specified cell position on the board.
     * The piece is set to {@code null}, effectively clearing the cell.
     * <p>
     * This method will not throw if {@code cellPos} is empty; simply nothing
     * will happen.
     *
     * @param cellPos the position of the cell from which the piece is to be
     *                removed; must be within the bounds of the board
     * @throws IllegalArgumentException if {@code cellPos} is out of bounds
     */
    public void removePiece(CellPosition cellPos)
    {
        Cell cell = this.getCell(cellPos);
        cell.setPiece(null);
    }

    /**
     * Determines whether the specified cell on the board is occupied by a
     * piece.
     *
     * @param cellPos the position of the cell to be checked; must be within
     *                bounds of the board
     * @return {@code true} if a piece is present in the specified cell,
     * {@code false} otherwise
     */
    public boolean isCellOccupied(CellPosition cellPos)
    {
        return this.getPieceAt(cellPos).isPresent();
    }

    /**
     * Determines if a specified path on the board is obstructed by any pieces.
     *
     * @param path a linked list of cell positions that represent the path to be
     *             checked; each position must be within the bounds of the
     *             board.
     * @return {@code true} if at least one cell in the path is occupied,
     * {@code false} otherwise.
     * @throws IllegalArgumentException if any cell position in the linked list
     *                                  is out of bounds.
     */
    public boolean isPathObstructed(LinkedList<CellPosition> path)
    {
        for (CellPosition cellPos : path)
        {
            if (this.isCellOccupied(cellPos))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the specified cell is occupied by a piece belonging to the same
     * player ("friendly").
     *
     * @param cellPos the position of the cell to be checked; must be within the
     *                bounds of the board
     * @param player  the player whose ownership of the piece is being verified
     * @return {@code true} if the cell contains a piece owned by the given
     * player, {@code false} otherwise
     * @throws IllegalArgumentException if {@code cellPos} is out of bounds
     */
    public boolean hasFriendlyPieceAt(CellPosition cellPos, Player player)
    {
        Optional<Piece> piece = this.getPieceAt(cellPos);
        return piece.isPresent() && piece.get().getOwner() == player;
    }

    /**
     * Registers a new vertical edge listener to the board. A vertical edge
     * listener is notified when a piece reaches the topmost or bottommost row
     * of the board.
     *
     * @param listener the listener to be registered for vertical edge events
     */
    public void registerVerticalEdgeListener(BoardVerticalEdgeListener listener)
    {
        this.verticalEdgeListeners.add(listener);
    }

    /**
     * Registers a new capture listener to the board. A capture listener is
     * notified when a piece has been captured during the game.
     *
     * @param listener the listener to be registered for capture events
     */
    public void registerCaptureListener(CaptureListener listener)
    {
        this.captureListeners.add(listener);
    }

    /**
     * Populates the board with cells. The specific arrangement and
     * initialization of cells are determined by the implementing class. This
     * allows for extensibility and the possibility of different chess games in
     * the future.
     *
     * @return a 2D ArrayList where each inner ArrayList represents a row of
     * cells and each Cell represents an individual square on the chess board
     */
    protected abstract ArrayList<ArrayList<Cell>> populateCells();

    /**
     * Retrieves the number of rows in the board. Up to the implementing class.
     *
     * @return the number of rows in the board
     */
    protected abstract int getBoardRows();

    /**
     * Retrieves the number of columns in the board. Up to the implementing
     * class.
     *
     * @return the total number of columns in the board
     */
    protected abstract int getBoardColumns();

    /**
     * Low-level helper method to retrieve the cell located at the specified
     * position on the board.
     *
     * @param cellPos the position of the cell to retrieve; must be within the
     *                bounds of the board
     * @return the {@code Cell} object located at the specified position
     * @throws IllegalArgumentException if the given cell position is out of
     *                                  bounds
     */
    private Cell getCell(CellPosition cellPos)
    {
        if (!isCellWithinBounds(cellPos))
        {
            throw new IllegalArgumentException(
                    "Attempted to call getCell with out-of-bounds cell position.");
        }
        return this.cells.get(cellPos.row()).get(cellPos.column());
    }

    /**
     * Checks whether the specified cell position is within the bounds of the
     * board.
     *
     * @param pos the cell position to check
     * @return {@code true} if the specified cell position is within the board's
     * boundaries, {@code false} otherwise
     */
    private boolean isCellWithinBounds(CellPosition pos)
    {
        int row = pos.row();
        int col = pos.column();
        return row >= 0 && row < this.cells.size() && col >= 0
                && col < this.cells.get(row).size();
    }
}
