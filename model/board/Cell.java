package model.board;

import model.pieces.Piece;

import java.util.Optional;

/**
 * Represents a cell that can hold a piece.
 * <p>
 * Note: It may or may not come as a surprise but this class does not keep track
 * of its position on the board. If it did, then potential data inconsistencies
 * could arise between it and the board.
 *
 * @author Harris Majeed
 * @see Board
 */
public class Cell
{
    /**
     * Represents the piece currently held by the cell. The piece might be null,
     * meaning the cell is empty.
     */
    private Piece piece = null;

    /**
     * Empty constructor.
     * <p>
     * The cell will initially hold a null piece indicating that it is empty.
     */
    public Cell()
    {
    }

    /**
     * Retrieves the piece currently held by the cell, if any.
     *
     * @return an {@code Optional} containing the piece if the cell is occupied
     */
    public Optional<Piece> getPiece()
    {
        return Optional.ofNullable(piece);
    }

    /**
     * Setter method for piece.
     *
     * @param piece the piece to be placed in the cell, or {@code null} to clear
     *              the cell
     */
    public void setPiece(Piece piece)
    {
        this.piece = piece;
    }
}