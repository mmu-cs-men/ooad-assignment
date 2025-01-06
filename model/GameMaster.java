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

    public void movePiece(Cell fromCell, Cell toCell, Player player)
    {
        throw new UnsupportedOperationException();
    }

    public Player getCurrentPlayer()
    {
        throw new UnsupportedOperationException();
    }

    public void advanceTurn()
    {
        throw new UnsupportedOperationException();
    }
}