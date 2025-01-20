package model.board;

import model.exceptions.PieceMoveException;
import model.game.Player;
import model.listeners.BoardVerticalEdgeListener;
import model.listeners.CaptureListener;
import model.pieces.Piece;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class Board
{
    private final ArrayList<ArrayList<Cell>> cells;
    private final ArrayList<BoardVerticalEdgeListener> verticalEdgeListeners = new ArrayList<>();
    private final ArrayList<CaptureListener> captureListeners = new ArrayList<>();
    protected final List<Player> players;

    public Board(List<Player> players)
    {
        this.players = players;
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
        Optional<Piece> existingPiece = this.getPieceAt(toPos);
        existingPiece.ifPresent(this::notifyCaptureListeners);
        this.removePiece(fromPos);
        Cell toCell = this.getCell(toPos);
        toCell.setPiece(piece);

        if (toPos.row() == 0 || toPos.row() == this.getBoardRows() - 1)
        {
            notifyVerticalEdgeListeners(piece);
        }

    }

    private void notifyVerticalEdgeListeners(Piece piece)
    {
        for (BoardVerticalEdgeListener listener : this.verticalEdgeListeners)
        {
            listener.onBoardVerticalEdgeReached(piece);
        }
    }

    private void notifyCaptureListeners(Piece piece)
    {
        for (CaptureListener listener : this.captureListeners)
        {
            listener.onCapture(piece);
        }
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

    public boolean hasFriendlyPieceAt(CellPosition cellPos, Player player)
    {
        Optional<Piece> piece = this.getPieceAt(cellPos);
        return piece.isPresent() && piece.get().getOwner() == player;
    }

    public void registerVerticalEdgeListener(BoardVerticalEdgeListener listener)
    {
        this.verticalEdgeListeners.add(listener);
    }

    public void registerCaptureListener(CaptureListener listener)
    {
        this.captureListeners.add(listener);
    }

    protected abstract ArrayList<ArrayList<Cell>> populateCells();

    protected abstract int getBoardRows();

    protected abstract int getBoardColumns();

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
