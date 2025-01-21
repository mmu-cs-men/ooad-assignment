package model.exceptions;

public class PieceMoveException extends RuntimeException
{
    public PieceMoveException()
    {
        super("Attempted to move a piece in an invalid way.");
    }

}
