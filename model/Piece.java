package model;

import java.util.LinkedList;

public abstract class Piece
{
    private Player owner;

    public Piece(Player owner)
    {
        this.owner = owner;
    }

    public Player getOwner()
    {
        return owner;
    }

    public abstract boolean canMoveTo(CellPosition toCell, Board board);

    public abstract boolean canJump();

    public abstract LinkedList<CellPosition> getPotentialPath(
            CellPosition toCell);
}