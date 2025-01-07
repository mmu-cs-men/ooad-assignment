package model;

import java.util.ArrayList;
import java.util.LinkedList;

public class Board
{
    private ArrayList<ArrayList<Cell>> cells;

    public Board()
    {
        cells = new ArrayList<ArrayList<Cell>>();
    }

    public Piece getPieceAt(CellPosition cellPos)
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
}