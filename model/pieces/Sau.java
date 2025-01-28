package model.pieces;

import model.board.CellPosition;
import model.game.Player;

import java.util.LinkedList;
import java.util.Optional;

/**
 * Represents a Sau piece in the game. This piece can move only one step in any direction,
 * and it cannot jump over other pieces.
 * @author Harris Majeed
 */
public class Sau extends Piece
{
    public Sau(Player player)
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

        if (Math.abs(rowDifference) > 1 || Math.abs(columnDifference) > 1)
        {
            return Optional.empty();
        }

        LinkedList<CellPosition> path = new LinkedList<>();
        path.add(toCellPos);
        return Optional.of(path);
    }

}
