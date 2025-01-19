package model.pieces;

import java.util.LinkedList;
import java.util.Optional;

import model.board.CellPosition;
import model.game.Player;

public class Tor extends Piece
{
    public Tor(Player player)
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

        if (rowDifference != 0 && columnDifference != 0)
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
