package model.pieces;

import model.board.CellPosition;
import model.game.Player;

import java.util.LinkedList;
import java.util.Optional;

public class Xor extends Piece implements Switchable
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

    @Override
    public Piece getSwitchedPiece()
    {
        return new Tor(this.getOwner());
    }
}
