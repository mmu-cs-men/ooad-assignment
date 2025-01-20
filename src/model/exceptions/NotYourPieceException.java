package model.exceptions;

public class NotYourPieceException extends RuntimeException
{
    public NotYourPieceException()
    {
        super("Attempted to move a piece that does not belong to the current player.");
    }

}
