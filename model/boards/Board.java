package model.boards;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;

import model.Cell;
import model.CellPosition;
import model.Player;
import model.exceptions.PieceMoveException;
import model.pieces.Piece;

public abstract class Board
{
    private ArrayList<ArrayList<Cell>> cells;

    public Board()
    {
        this.cells = this.populateCells();
    }

    public Optional<Piece> getPieceAt(CellPosition cellPos)
    {
        Cell cell = this.getCell(cellPos);
        return cell.getPiece();
    }

    public void movePiece(CellPosition fromPos, CellPosition toPos)
    {
        Cell fromCell = this.getCell(fromPos);
        Piece piece = fromCell.getPiece().orElseThrow(PieceMoveException::new);
        this.removePiece(fromPos);
        Cell toCell = this.getCell(toPos);
        toCell.setPiece(piece);
    }

    public void removePiece(CellPosition cellPos)
    {
        Cell cell = this.getCell(cellPos);
        cell.setPiece(null);
    }

    public boolean isCellOccupied(CellPosition cellPos)
    {
        return this.getPieceAt(cellPos).isPresent();
    }

    public boolean isPathObstructed(LinkedList<CellPosition> path)
    {
        for (CellPosition cellPos : path)
        {
            if (this.isCellOccupied(cellPos))
            {
                return true;
            }
        }
        return false;
    }

    public boolean hasEnemyPieceAt(CellPosition cellPos, Player player)
    {
        Optional<Piece> piece = this.getPieceAt(cellPos);
        return piece.isPresent() && piece.get().getOwner() != player;
    }

    protected abstract ArrayList<ArrayList<Cell>> populateCells();

    private Cell getCell(CellPosition cellPos)
    {
        if (!isCellWithinBounds(cellPos))
        {
            throw new IllegalArgumentException(
                    "Attempted to call getCell with out-of-bounds cell position.");
        }
        return this.cells.get(cellPos.row()).get(cellPos.column());
    }

    private boolean isCellWithinBounds(CellPosition pos)
    {
        int row = pos.row();
        int col = pos.column();
        return row >= 0 && row < this.cells.size() && col >= 0
                && col < this.cells.get(row).size();
    }
}