package model.pieces;

import java.util.LinkedList;
import java.util.Optional;

import model.CellPosition;
import model.Player;

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

    public abstract Optional<LinkedList<CellPosition>> getPotentialPath(
            CellPosition toCellPos);
}