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

    /**
     * Override the default icon name with flipping logic:
     */
    @Override
    public String getIconName()
    {
        String baseColor = getOwner().id().equals("1") ? "blue" : "red";

        // "red" piece: if facingUp==true => we use "_flipped", else normal
        // "blue" piece: if facingUp==true => normal, else "_flipped"
        // Adjust if our initial orientation is reversed
        if (baseColor.equals("red"))
        {
            return facingUp ? "ram_red_piece_flipped" : "ram_red_piece";
        }
        else
        {
            return facingUp ? "ram_blue_piece" : "ram_blue_piece_flipped";
        }
    }
}
