package model.exceptions;

/**
 * This exception is thrown when there is an attempt to access a piece in a cell
 * that does not contain any piece on the game board.
 * <p>
 * Use this if you need a piece to exist before performing some action like
 * moving.
 *
 * @author Harris Majeed
 */
public class NoPieceException extends RuntimeException
{
    public NoPieceException()
    {
        super("No piece found in the cell.");
    }
}
