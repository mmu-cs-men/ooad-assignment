package model.listeners;

import model.pieces.Piece;

public interface CaptureListener
{
    void onCapture(Piece piece);
}