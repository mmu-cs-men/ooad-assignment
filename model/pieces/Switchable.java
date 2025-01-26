package model.pieces;

/**
 * Represents an entity capable of switching between piece types.
 * This interface defines a contract for objects that can provide
 * an alternative piece variant when switched.
 * @author Harris Majeed
 */
public interface Switchable
{
    Piece getSwitchedPiece();
}
