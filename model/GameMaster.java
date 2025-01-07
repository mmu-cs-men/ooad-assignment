package model;

import util.CircularLinkedList;

public class GameMaster
{
    private Player currentPlayer;
    private Board board;
    private CircularLinkedList<Player> players;

    public GameMaster(Board board, CircularLinkedList<Player> players)
    {
        this.board = board;
        this.players = players;
        this.currentPlayer = players.getFirst();
    }

    public void movePiece(CellPosition fromCellPos, CellPosition toCellPos)
    {
        throw new UnsupportedOperationException();
    }

    public Player getCurrentPlayer()
    {
        return this.currentPlayer;
    }

    public void advanceTurn()
    {
        this.currentPlayer = this.players.iterator().next();
    }
}