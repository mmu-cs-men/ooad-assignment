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
        Cell cell = this.cells.get(cellPos.row()).get(cellPos.column());
        return cell.getPiece();
    }

    public void movePiece(CellPosition fromPos, CellPosition toPos)
    {
        Cell fromCell = this.getCell(fromPos);
        Piece piece = fromCell.getPiece().orElseThrow();
        this.removePiece(fromPos);
        Cell toCell = this.getCell(toPos);
        toCell.setPiece(piece);
    }

    public void removePiece(CellPosition cellPos)
    {
        Cell cell = this.cells.get(cellPos.row()).get(cellPos.column());
        cell.setPiece(null);
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

    private Cell getCell(CellPosition cellPos)
    {
        return this.cells.get(cellPos.row()).get(cellPos.column());
    }
}