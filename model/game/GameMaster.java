package model.game;

import java.util.ArrayList;
import java.util.LinkedList;

import model.board.Board;
import model.board.CellPosition;
import model.exceptions.NoPieceException;
import model.exceptions.NotYourPieceException;
import model.exceptions.PieceMoveException;
import model.listeners.CaptureListener;
import model.listeners.WinListener;
import model.pieces.Piece;
import utils.CircularLinkedList;

public class GameMaster implements CaptureListener
{
    private Player currentPlayer;
    private final Board board;
    private final CircularLinkedList<Player> players;
    private ArrayList<WinListener> winListeners = new ArrayList<>();

    public GameMaster(Board board, CircularLinkedList<Player> players)
    {
        this.board = board;
        this.players = players;
        this.currentPlayer = players.getFirst();

        this.board.registerCaptureListener(this);
    }

    public void movePiece(CellPosition fromCellPos, CellPosition toCellPos)
    {
        Piece piece = this.board.getPieceAt(fromCellPos)
                .orElseThrow(NoPieceException::new);

        if (piece.getOwner() != this.currentPlayer)
        {
            throw new NotYourPieceException();
        }

        LinkedList<CellPosition> path = piece
                .getPotentialPath(fromCellPos, toCellPos)
                .orElseThrow(PieceMoveException::new);

        CellPosition lastPos = path.removeLast();

        if (!piece.canJump() && this.board.isPathObstructed(path)
                || this.board.hasFriendlyPieceAt(lastPos, currentPlayer))
        {
            throw new PieceMoveException();
        }

        this.board.movePiece(fromCellPos, toCellPos);
    }

    public Player getCurrentPlayer()
    {
        return this.currentPlayer;
    }

    public void advanceTurn()
    {
        this.currentPlayer = this.players.circularIterator().next();
    }

    public void registerWinListener(WinListener listener)
    {
        this.winListeners.add(listener);
    }

    public void notifyWinListeners(Player player)
    {
        for (WinListener listener : this.winListeners)
        {
            listener.onWin(player);
        }
    }

    @Override
    public void onCapture(Piece piece)
    {
        if (!piece.isCriticalPiece())
        {
            return;
        }

        for (Player player : this.players)
        {
            if (piece.getOwner() == player)
            {
                this.players.remove(player);
                break;
            }
        }

        if (this.players.size() == 1)
        {
            this.notifyWinListeners(this.players.getFirst());
        }
    }
}