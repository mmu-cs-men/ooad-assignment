package model.exceptions;

public class NoPieceException extends RuntimeException
{
    public NoPieceException()
    {
        super("No piece found in the cell.");
    }
}
