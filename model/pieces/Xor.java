package model.pieces;

import java.util.LinkedList;
import java.util.Optional;

import model.CellPosition;
import model.Player;

public class Xor extends Piece
{
    public Xor(Player player)
    {
        super(player);
    }

    @Override
    public boolean canJump()
    {
        return false;
    }

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

}
