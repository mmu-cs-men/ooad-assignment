package model.listeners;

import model.pieces.Piece;

/**
 * Listener interface to be notified when a piece reaches one of the vertical
 * edges (top or bottom) of the board.
 *
 * <p>This interface follows the Observer pattern, where implementing classes
 * observe and respond to relevant events (in this case, a piece reaching
 * a vertical edge).</p>
 * @author Harris Majeed
 */
public interface BoardVerticalEdgeListener
{
    void onBoardVerticalEdgeReached(Piece piece);
}
