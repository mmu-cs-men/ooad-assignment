package model.pieces;

import model.board.CellPosition;
import model.game.Player;
import model.listeners.BoardVerticalEdgeListener;

import java.util.LinkedList;
import java.util.Optional;

public class Ram extends Piece implements BoardVerticalEdgeListener
{
    private boolean facingUp;

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
