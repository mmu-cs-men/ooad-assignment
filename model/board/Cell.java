package model.board;

import java.util.Optional;

import model.pieces.Piece;

public class Cell
{
    private Piece piece = null;

    public Cell()
    {

    }

    public Cell(Piece piece)
    {
        this.piece = piece;
    }

    public Optional<Piece> getPiece()
    {
        return Optional.ofNullable(piece);
    }

    public void setPiece(Piece piece)
    {
        this.piece = piece;
    }
}