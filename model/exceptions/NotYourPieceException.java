package model.exceptions;

/**
 * This exception is thrown when a player attempts to move a piece that does not
 * belong to them.
 * <p>
 * Use this if the player tries to move an opponent's piece (e.g. blue trying to
 * move red's pieces).
 *
 * @author Siva
 */
public class NotYourPieceException extends RuntimeException
{
    public NotYourPieceException()
    {
        super("Attempted to move a piece that does not belong to the current player.");
    }

}
