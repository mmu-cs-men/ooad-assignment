package model.pieces;

import java.util.LinkedList;
import java.util.Optional;

import model.CellPosition;
import model.Player;

public abstract class Piece
{
    private final Player owner;
    private boolean criticalPiece = false;

    public Piece(Player owner)
    {
        this.owner = owner;
    }

    public Piece(Player owner, boolean criticalPiece)
    {
        this.owner = owner;
        this.criticalPiece = criticalPiece;
    }

    public Player getOwner()
    {
        return owner;
    }

    public boolean isCriticalPiece()
    {
        return criticalPiece;
    }

    public void setCriticalPiece(boolean keyPiece)
    {
        this.criticalPiece = keyPiece;
    }

    public abstract boolean canJump();

    public abstract Optional<LinkedList<CellPosition>> getPotentialPath(
            CellPosition fromCellPos, CellPosition toCellPos);
}