package model.pieces;

import model.board.CellPosition;
import model.game.Player;
import model.listeners.BoardVerticalEdgeListener;

import java.util.LinkedList;
import java.util.Optional;

/**
 * Represents a Ram piece that moves vertically and changes its facing direction
 *                                              when it reaches a vertical edge.
 * @author Harris majeed
 */
public class Ram extends Piece implements BoardVerticalEdgeListener
{
    private boolean facingUp;

    /**
     * Constructs a new Ram with the specified owner and initial facing direction.
     *
     * @param player   the owner of this piece
     * @param facingUp true if the piece is initially facing up, false if facing down
     * @author Harris Majeed
     */
    public Ram(Player player, boolean facingUp)
    {
        super(player);
        this.facingUp = facingUp;
    }

    public boolean isFacingUp()
    {
        return facingUp;
    }

    @Override
    public boolean canJump()
    {
        return false;
    }


    /**
     * Determines the potential path this Ram can take when moving from one cell to another.
     * This piece can only move one row vertically in its facing direction, and it cannot change columns.
     *
     * @param fromCellPos the current cell position
     * @param toCellPos   the target cell position
     * @return an Optional containing the path if valid, or an empty Optional if the move is invalid
     * @author Harris Majeed
     */
    @Override
    public Optional<LinkedList<CellPosition>> getPotentialPath(
            CellPosition fromCellPos, CellPosition toCellPos)
    {
        if (fromCellPos.column() != toCellPos.column())
        {
            return Optional.empty();
        }

        int rowDifference = facingUp ? fromCellPos.row() - toCellPos.row()
                : toCellPos.row() - fromCellPos.row();

        if (rowDifference == 1)
        {
            LinkedList<CellPosition> path = new LinkedList<>();
            path.add(toCellPos);
            return Optional.of(path);
        }

        return Optional.empty();
    }

    /**
     * Reverses the Ram's facing direction when it reaches any vertical edge of the board.
     *
     * @param piece the piece that has reached the edge
     * @author Harris Majeed
     */
    @Override
    public void onBoardVerticalEdgeReached(Piece piece)
    {
        if (piece != this)
        {
            return;
        }

        facingUp = !facingUp;
    }

    @Override
    public String getStringRepresentation()
    {
        String pieceName = this.getClass().getSimpleName().toUpperCase();
        String ownerId = this.getOwner().id();
        String facingDirection = isFacingUp() ? "_FACINGUP" : "_FACINGDOWN";
        String criticalPiece = "";

        if (this.isCriticalPiece())
        {
            criticalPiece = "_CRITICAL";
        }

        return "%s_%s%s%s".formatted(pieceName, ownerId, facingDirection, criticalPiece);
    }

}
