package model.serialization;

import model.board.Cell;
import model.game.Player;
import model.pieces.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SaveLoadSerializer
{
    public SaveLoadSerializer()
    {
    }

    public void saveStateToFile(GameState gameState, String path)
    {
        String content = gameState.getStringRepresentation();
        try (FileWriter writer = new FileWriter(path))
        {
            writer.write(content);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Failed to save game state to file", e);
        }
    }

    public GameState loadStateFromFile(String path)
    {
        try
        {
            List<String> lines = Files.readAllLines(Path.of(path));
            if (lines.size() < 5)
            {
                throw new IllegalArgumentException("Invalid file format");
            }

            int turnCount = parseTurnCount(lines.get(1));
            List<String> playerIds = parsePlayers(lines.get(2));
            String currentPlayerId = parseCurrentPlayer(lines.get(3));

            List<Player> players = playerIds.stream()
                    .map(Player::new)
                    .collect(Collectors.toList());

            Player currentPlayer = players.stream()
                    .filter(player -> player.id().equals(currentPlayerId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Current player not found in players list"));

            int cellStartIndex = 5;
            List<Cell> cells = new ArrayList<>();
            for (int i = cellStartIndex; i < lines.size(); i++)
            {
                String line = lines.get(i).trim();
                if (!line.isEmpty())
                {
                    cells.add(parseCell(line, players));
                }
            }

            if (cells.size() != 8 * 5)
            {
                throw new IllegalArgumentException("Cell count does not match 8x5 dimensions");
            }

            List<List<Cell>> cellGrid = new ArrayList<>();
            for (int i = 0; i < cells.size(); i += 5)
            {
                int end = i + 5;
                List<Cell> row = new ArrayList<>(cells.subList(i, end));
                cellGrid.add(row);
            }

            return new GameState(turnCount, players, currentPlayer, cellGrid);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Failed to load game state from file", e);
        }
    }

    private int parseTurnCount(String line)
    {
        return Integer.parseInt(line.split(": ")[1]);
    }

    private List<String> parsePlayers(String line)
    {
        String playersStr = line.split(": ")[1];
        return Arrays.asList(playersStr.split(", "));
    }

    private String parseCurrentPlayer(String line)
    {
        return line.split(": ")[1];
    }

    private Cell parseCell(String line, List<Player> players)
    {
        if (line.equals("EMPTY"))
        {
            return new Cell();
        }

        String[] parts = line.split("_");
        String pieceType = parts[0];
        String ownerId = parts[1];
        List<String> modifiers = Arrays.asList(Arrays.copyOfRange(parts, 2, parts.length));

        Player owner = players.stream()
                .filter(p -> p.id().equals(ownerId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Player not found: " + ownerId));

        Piece piece = createPiece(pieceType, owner, modifiers);
        Cell cell = new Cell();
        cell.setPiece(piece);
        return cell;
    }

    private Piece createPiece(String type, Player owner, List<String> modifiers)
    {
        Piece piece;
        switch (type)
        {
            case "RAM":
                boolean facingUp = modifiers.contains("FACINGUP");
                piece = new Ram(owner, facingUp);
                break;
            case "BIZ":
                piece = new Biz(owner);
                break;
            case "SAU":
                piece = new Sau(owner);
                break;
            case "TOR":
                piece = new Tor(owner);
                break;
            case "XOR":
                piece = new Xor(owner);
                break;
            default:
                throw new IllegalArgumentException("Unknown piece type: " + type);
        }

        if (modifiers.contains("CRITICAL"))
        {
            piece.setCriticalPiece(true);
        }

        return piece;
    }
}