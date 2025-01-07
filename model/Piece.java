package model;

import java.util.LinkedList;

public abstract class Piece
{
    private final Player owner;

    public Piece(Player owner)
    {
        this.owner = owner;
    }

    public Player getOwner()
    {
        return owner;
    }

    public abstract boolean canJump();

    public abstract LinkedList<CellPosition> getPotentialPath(
            CellPosition toCellPos);
}