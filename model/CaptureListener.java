package model;

import model.pieces.Piece;

public interface CaptureListener
{
    void onCapture(Piece piece);
}