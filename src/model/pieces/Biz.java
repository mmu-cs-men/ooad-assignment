package model.pieces;

import model.board.CellPosition;
import model.game.Player;

import java.util.LinkedList;
import java.util.Optional;

public class Biz extends Piece
{
    public Biz(Player player)
    {
        super(player);
    }

    @Override
    public boolean canJump()
    {
        return true;
    }

    private boolean isValidMove(int rowDifference, int columnDifference)
    {
        return (Math.abs(rowDifference) == 2 && Math.abs(columnDifference) == 1)
                || (Math.abs(rowDifference) == 1
                && Math.abs(columnDifference) == 2);
    }

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
