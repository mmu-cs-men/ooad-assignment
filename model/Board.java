package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;

public abstract class Board
{
    private ArrayList<ArrayList<Cell>> cells;

    public Board()
    {
        this.cells = this.populateCells();
    }

    public Optional<Piece> getPieceAt(CellPosition cellPos)
    {
        throw new UnsupportedOperationException();
    }

    public void movePiece(CellPosition fromPos, CellPosition toPos)
    {
        throw new UnsupportedOperationException();
    }

    public void removePiece(CellPosition cellPos)
    {
        throw new UnsupportedOperationException();
    }

    public boolean isCellOccupied(CellPosition cellPos)
    {
        throw new UnsupportedOperationException();
    }

    public boolean isPathObstructed(LinkedList<CellPosition> path)
    {
        throw new UnsupportedOperationException();
    }

    public boolean hasEnemyPieceAt(CellPosition cellPos, Player player)
    {
        throw new UnsupportedOperationException();
    }

    protected abstract ArrayList<ArrayList<Cell>> populateCells();

    protected abstract ArrayList<ArrayList<Cell>> populateCells();
}