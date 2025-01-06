package model;

import java.util.LinkedList;

public class GameMaster
{
    private Player currentPlayer;
    private Board board;
    private LinkedList<Player> players; // TODO: Use CircularLinkedList laterrr

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