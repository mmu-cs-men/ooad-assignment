package model.pieces;

import java.util.LinkedList;
import java.util.Optional;

import model.CellPosition;
import model.Player;

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
