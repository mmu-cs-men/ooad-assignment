package model.pieces;

import model.board.CellPosition;
import model.game.Player;

import java.util.LinkedList;
import java.util.Optional;

/**
 * Represents a Biz piece with its unique movement behavior and jump capability.
 * @author Harris Majeed
 */
public class Biz extends Piece
{

    /**
     * Constructs a new Biz piece owned by the specified player.
     *
     * @param player the player associated with this piece
     * @author Harris Majeed
     */
    public Biz(Player player)
    {
        super(player);
    }

    @Override
    public boolean canJump()
    {
        return true;
    }

    /**
     * Checks if the specified row and column differences represent a valid move for this piec
     *
     * @param rowDifference    the difference in rows
     * @param columnDifference the difference in columns
     * @return true if the move is valid; false otherwise
     * @author Harris Majeed
     */
    private boolean isValidMove(int rowDifference, int columnDifference)
    {
        return (Math.abs(rowDifference) == 2 && Math.abs(columnDifference) == 1)
                || (Math.abs(rowDifference) == 1
                && Math.abs(columnDifference) == 2);
    }

    /**
     * Calculates the potential path this piece takes from the initial cell to the target cell.
     * If the move is invalid, returns an empty pptional.
     *
     * @param fromCellPos the starting cell position
     * @param toCellPos   the destination cell position
     * @return an Optional containing the path of positions if valid; otherwise an empty optional
     * @author Harris Majeed
     */
    @Override
    public Optional<LinkedList<CellPosition>> getPotentialPath(
            CellPosition fromCellPos, CellPosition toCellPos)
    {
        int rowDifference = toCellPos.row() - fromCellPos.row();
        int columnDifference = toCellPos.column() - fromCellPos.column();

        if (!isValidMove(rowDifference, columnDifference))
        {
            return Optional.empty();
        }

        LinkedList<CellPosition> path = new LinkedList<>();
        int rowStep = Integer.signum(rowDifference);
        int columnStep = Integer.signum(columnDifference);

        if (Math.abs(rowDifference) == 2)
        {
            path.add(new CellPosition(fromCellPos.row() + rowStep,
                    fromCellPos.column()));
            path.add(new CellPosition(fromCellPos.row() + rowStep * 2,
                    fromCellPos.column() + columnStep));
        }
        else
        {
            path.add(new CellPosition(fromCellPos.row(),
                    fromCellPos.column() + columnStep));
            path.add(new CellPosition(fromCellPos.row() + rowStep,
                    fromCellPos.column() + columnStep * 2));
        }

        path.add(toCellPos);

        return Optional.of(path);
    }
}
