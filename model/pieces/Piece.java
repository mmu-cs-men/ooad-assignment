package model.pieces;

import java.util.LinkedList;
import java.util.Optional;

import model.board.CellPosition;
import model.game.Player;

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
}