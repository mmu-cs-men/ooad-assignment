package model.pieces;

import model.board.CellPosition;
import model.game.Player;
import model.serialization.Stringable;

import java.util.LinkedList;
import java.util.Optional;

/**
 * Represents an abstract base class for all chess pieces in the game. Specific
 * piece types should extend this class and implement the abstract movement
 * logic.
 * <p>
 * A key feature of this class is the ability to
 * {@link #setCriticalPiece(boolean) setCriticalPiece} which, if set, denotes
 * that the piece is required for a player to be a participant in the game. If a
 * piece marked as critical is captured in the game, its owner will be
 * eliminated from the game.
 * <p>
 * Note: A player should have only one critical piece.
 *
 * @author Harris Majeed
 */
public abstract class Piece implements Stringable
{
    /**
     * The player this piece belongs to.
     */
    private final Player owner;

    /**
     * Indicates if this piece is needed for a player to be a participant in the
     * game.
     */
    private boolean criticalPiece = false;

    /**
     * Creates a new chess piece owned by the specified player.
     *
     * @param owner the player who will control this piece
     * @author Harris Majeed
     */
    public Piece(Player owner)
    {
        this.owner = owner;
    }

    /**
     * Gets the player that owns this piece.
     *
     * @return the owning player
     * @author Harris Majeed
     */
    public Player getOwner()
    {
        return owner;
    }

    /**
     * Checks if this piece is considered critical (needed for the player to be
     * a participant).
     *
     * @return true if the piece is critical, false otherwise
     * @author Harris Majeed
     */
    public boolean isCriticalPiece()
    {
        return criticalPiece;
    }

    /**
     * Sets the critical status of this piece.
     *
     * @param criticalPiece true to mark as critical, false otherwise
     * @author Harris Majeed
     */
    public void setCriticalPiece(boolean criticalPiece)
    {
        this.criticalPiece = criticalPiece;
    }

    /**
     * Determines if this piece type can jump over other pieces.
     *
     * @return true if the piece can move over obstructing pieces, false
     * otherwise
     * @author Harris Majeed
     */
    public abstract boolean canJump();

    /**
     * Calculates the potential movement path between two positions.
     * <p>
     * The path includes all intermediate cell positions that would be traversed
     * during a move, excluding the starting position. This is used to validate
     * move validity and check for obstructions.
     *
     * @param fromCellPos The starting position of the piece
     * @param toCellPos   The target position for the move
     * @return An {@code Optional} containing the movement path as a LinkedList
     * of CellPosition objects if the move is valid for this piece type. Returns
     * empty Optional if the move pattern is invalid (note: does not check for
     * obstructions, bounds, or game rules).
     * @author Harris Majeed
     */
    public abstract Optional<LinkedList<CellPosition>> getPotentialPath(
            CellPosition fromCellPos, CellPosition toCellPos);

    /**
     * Returns a string-based identifier for this piece, which includes
     * the piece's type name, the owner's ID, and an indication if the
     * piece is marked as critical.
     * <p>
     * The format of this representation is:
     * <pre>
     * [PIECE_NAME]_[OWNER_ID][_CRITICAL]
     * </pre>
     *
     * @return a string representation of this piece that includes the
     *         type name, the owner ID, and a "_CRITICAL" suffix if
     *         applicable
     *
     * @author Harris Majeed
     */
    @Override
    public String getStringRepresentation()
    {
        String pieceName = this.getClass().getSimpleName().toUpperCase();
        String ownerId = this.owner.id();
        String criticalPiece = "";

        if (this.isCriticalPiece())
        {
            criticalPiece = "_CRITICAL";
        }

        return "%s_%s%s".formatted(pieceName, ownerId, criticalPiece);
    }
}