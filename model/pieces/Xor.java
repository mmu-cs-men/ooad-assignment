package model.pieces;

import model.board.CellPosition;
import model.game.Player;

import java.util.LinkedList;
import java.util.Optional;

/**
 * Represents an Xor piece, which moves diagonally on the board.
 * It does not have the ability to jump over other pieces.
 * @author Harris Majeed
 */
public class Xor extends Piece implements Switchable
{
    /**
     * Creates a new Xor piece with the specified owner.
     *
     * @param player the owner of this piece.
     * @author Harris Majeed
     */
    public Xor(Player player)
    {
        super(player);
    }

    @Override
    public boolean canJump()
    {
        return false;
    }

    /**
     *
     * @param fromCellPos The starting position of the piece
     * @param toCellPos   The target position for the move
     * @author Sivanathan
     */
    @Override
    public Optional<LinkedList<CellPosition>> getPotentialPath(
            CellPosition fromCellPos, CellPosition toCellPos)
    {
        int rowDifference = toCellPos.row() - fromCellPos.row();
        int columnDifference = toCellPos.column() - fromCellPos.column();

        if (Math.abs(rowDifference) != Math.abs(columnDifference))
        {
            return Optional.empty();
        }

        LinkedList<CellPosition> path = new LinkedList<>();
        int rowStep = Integer.signum(rowDifference);
        int columnStep = Integer.signum(columnDifference);

        int row = fromCellPos.row();
        int column = fromCellPos.column();

        while (row != toCellPos.row() || column != toCellPos.column())
        {
            row += rowStep;
            column += columnStep;
            path.add(new CellPosition(row, column));
        }

        return Optional.of(path);
    }

    @Override
    public Piece getSwitchedPiece()
    {
        return new Tor(this.getOwner());
    }
}
