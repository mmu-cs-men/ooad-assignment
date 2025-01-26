package model.listeners;

import model.pieces.Piece;

/**
 * Listener interface to be notified when a piece is captured in the game.
 *
 * <p>This interface follows the Observer pattern, where implementing classes
 * observe and respond to relevant events (in this case, when a piece is
 * captured).</p>
 *
 * @author Harris Majeed
 */
public interface CaptureListener
{
    void onCapture(Piece piece);
}