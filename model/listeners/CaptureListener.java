package model.listeners;

import model.pieces.Piece;

/**
 * Listener interface to be notified when a piece is captured in the game.
 *
 * @author Harris Majeed
 */
public interface CaptureListener
{
    void onCapture(Piece piece);
}