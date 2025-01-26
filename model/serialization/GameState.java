package model.serialization;

import model.board.Cell;
import model.game.Player;

import java.util.List;

public record GameState(int turnCount, List<Player> players,
                        Player currentPlayer,
                        List<List<Cell>> cells) implements Stringable
{
    @Override
    public String getStringRepresentation()
    {
        String formattedPlayerIds = String.join(", ", players.stream().map(Player::id).toList());
        String currentPlayerId = this.currentPlayer.id();

        StringBuilder formattedCells = new StringBuilder();
        for (List<Cell> row : cells)
        {
            for (Cell cell : row)
            {
                formattedCells.append(cell.getStringRepresentation());
                formattedCells.append("\n");
            }
        }

        return """
                Game: Kwazam Chess
                Turn Count: %d
                Players: %s
                Current Player: %s
                
                %s
                """.formatted(turnCount, formattedPlayerIds, currentPlayerId, formattedCells);
    }
}
