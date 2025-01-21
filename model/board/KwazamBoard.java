package model.board;

import model.game.Player;
import model.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class KwazamBoard extends Board
{
    public KwazamBoard(List<Player> players)
    {
        super(players);
    }

    @Override
    protected ArrayList<ArrayList<Cell>> populateCells()
    {
        ArrayList<ArrayList<Cell>> cells = new ArrayList<>();
        for (int row = 0; row < getBoardRows(); row++)
        {
            ArrayList<Cell> rowCells = new ArrayList<>();
            for (int column = 0; column < getBoardColumns(); column++)
            {
                rowCells.add(new Cell());
            }
            cells.add(rowCells);
        }

        cells.get(0).get(0).setPiece(new Tor(players.get(1)));
        cells.get(0).get(1).setPiece(new Biz(players.get(1)));

        Piece redSau = new Sau(players.get(1));
        redSau.setCriticalPiece(true);
        cells.get(0).get(2).setPiece(redSau);

        cells.get(0).get(3).setPiece(new Biz(players.get(1)));
        cells.get(0).get(4).setPiece(new Xor(players.get(1)));
        for (int column = 0; column < getBoardColumns(); column++)
        {
            Ram ram = new Ram(players.get(1), false);
            this.registerVerticalEdgeListener(ram);
            cells.get(1).get(column).setPiece(ram);
        }

        for (int column = 0; column < getBoardColumns(); column++)
        {
            Ram ram = new Ram(players.get(0), true);
            this.registerVerticalEdgeListener(ram);
            cells.get(6).get(column).setPiece(ram);
        }
        cells.get(7).get(0).setPiece(new Xor(players.get(0)));
        cells.get(7).get(1).setPiece(new Biz(players.get(0)));

        Piece blueSau = new Sau(players.get(0));
        blueSau.setCriticalPiece(true);
        cells.get(7).get(2).setPiece(blueSau);

        cells.get(7).get(3).setPiece(new Biz(players.get(0)));
        cells.get(7).get(4).setPiece(new Tor(players.get(0)));

        return cells;
    }

    @Override
    protected int getBoardRows()
    {
        return 8;
    }

    @Override
    protected int getBoardColumns()
    {
        return 5;
    }
}
