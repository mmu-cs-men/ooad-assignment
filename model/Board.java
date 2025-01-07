package model;

import java.util.ArrayList;
import java.util.LinkedList;

public class Board
{
    private ArrayList<ArrayList<Cell>> grid;

    public Board()
    {
        grid = new ArrayList<ArrayList<Cell>>();
    }

    public Piece getPieceAt(CellPosition cell)
    {
        throw new UnsupportedOperationException();
    }

    public void movePiece(CellPosition fromCell, Cell toCell)
    {
        throw new UnsupportedOperationException();
    }

    public void removePiece(CellPosition cell)
    {
        throw new UnsupportedOperationException();
    }

    public boolean isCellOccupied(CellPosition cell)
    {
        throw new UnsupportedOperationException();
    }

    public boolean isPathObstructed(LinkedList<CellPosition> path)
    {
        throw new UnsupportedOperationException();
    }

    public boolean hasEnemyPieceAt(CellPosition cell, Player player)
    {
        throw new UnsupportedOperationException();
    }
}