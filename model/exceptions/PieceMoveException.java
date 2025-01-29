package model.exceptions;

/**
 * This exception is thrown when there is attempt to move a piece in an
 * "invalid" way.
 * <p>
 * In Kwazam Chess, this exception is thrown when the player attempts to move a
 * piece in a way that does not follow its movement pattern (e.g. moving a Ram
 * two cells ahead). This exception is also thrown if a piece that can't jump
 * tries to move to a cell that is obstructed.
 *
 * @author Siva
 */
public class PieceMoveException extends RuntimeException
{
    public PieceMoveException()
    {
        super("Attempted to move a piece in an invalid way.");
    }

}
