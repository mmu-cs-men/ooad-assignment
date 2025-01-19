package model.pieces;

import java.util.LinkedList;
import java.util.Optional;

import model.board.CellPosition;
import model.game.Player;
import model.listeners.BoardVerticalEdgeListener;

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

}
