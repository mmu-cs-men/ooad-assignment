package model.listeners;

import model.pieces.Piece;

/**
 * Listener interface to be notified when a piece reaches one of the vertical
 * edges (top or bottom) of the board.
 *
 * @author Harris Majeed
 */
public interface BoardVerticalEdgeListener
{
    void onBoardVerticalEdgeReached(Piece piece);
}
