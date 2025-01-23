package model.pieces;

import model.board.CellPosition;
import model.game.Player;

import java.util.LinkedList;
import java.util.Optional;

public abstract class Piece
{
    private final Player owner;
    private boolean criticalPiece = false;

    public Piece(Player owner)
    {
        this.owner = owner;
    }

    public Player getOwner()
    {
        return owner;
    }

    public boolean isCriticalPiece()
    {
        return criticalPiece;
    }

    public void setCriticalPiece(boolean criticalPiece)
    {
        this.criticalPiece = criticalPiece;
    }

    public abstract boolean canJump();

    public abstract Optional<LinkedList<CellPosition>> getPotentialPath(
            CellPosition fromCellPos, CellPosition toCellPos);

    /**
     * returns the icon name (without .png) for this piece
     * default implementation just returns <ClassName>_<color>
     * Subclasses can override
     */
    public String getIconName()
    {
        // Player("1") is Blue, Player("2") is Red:
        String color = owner.id().equals("1") ? "blue" : "red";
        // Use the class name (e.g. "sau", "biz") plus "_blue" or "_red"
        // This means sau blue and biz red and etc
        return this.getClass().getSimpleName() + "_" + color;
    }
}