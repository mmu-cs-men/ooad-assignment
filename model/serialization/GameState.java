package model.serialization;

import model.board.Cell;
import model.game.Player;

import java.util.List;

public record GameState(int turnCount, List<String> playerIds,
                        Player currentPlayer, List<List<Cell>> cells)
{
}
