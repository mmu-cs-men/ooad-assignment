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

    public Piece getPieceAt(Cell cell)
    {
        throw new UnsupportedOperationException();
    }

    public void movePiece(Cell fromCell, Cell toCell)
    {
        throw new UnsupportedOperationException();
    }

    public void removePiece(Cell cell)
    {
        throw new UnsupportedOperationException();
    }

    public boolean isCellOccupied(Cell cell)
    {
        throw new UnsupportedOperationException();
    }

    public boolean isPathObstructed(LinkedList<Cell> path)
    {
        throw new UnsupportedOperationException();
    }

    public boolean hasEnemyPieceAt(Cell cell, Player player)
    {
        throw new UnsupportedOperationException();
    }
}