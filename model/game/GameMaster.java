package model.game;

import model.board.Board;
import model.board.CellPosition;
import model.exceptions.NoPieceException;
import model.exceptions.NotYourPieceException;
import model.exceptions.PieceMoveException;
import model.listeners.CaptureListener;
import model.listeners.WinListener;
import model.pieces.Piece;
import utils.CircularLinkedList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Manages turn order, piece movements, and winning conditions.
 */
public class GameMaster implements CaptureListener
{
    private final Iterator<Player> playerIterator;
    private final Board board;
    private final CircularLinkedList<Player> players;
    private final ArrayList<WinListener> winListeners = new ArrayList<>();
    private Player currentPlayer;

    public GameMaster(Board board, CircularLinkedList<Player> players)
    {
        this.board = board;
        this.players = players;
        this.currentPlayer = players.getFirst();
        this.playerIterator = players.circularIterator();
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
        this.currentPlayer = this.playerIterator.next();
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

    /**
     * Allows the Controller to retrieve the Board for reading piece positions.
     */
    public Board getBoard()
    {
        return this.board;
    }
}
