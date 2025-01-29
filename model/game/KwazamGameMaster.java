package model.game;

import model.board.Cell;
import model.board.KwazamBoard;
import model.pieces.Piece;
import model.pieces.Ram;
import utils.CircularLinkedList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Concrete implementation of {@code GameMaster} for Kwazam Chess.
 *
 * @author Harris Majeed
 * @author Abdullah Hawash -> get cell representation
 * @see GameMaster
 * @see KwazamBoard
 */
public class KwazamGameMaster extends GameMaster<KwazamBoard>
{
    /**
     * Creates a new KwazamGameMaster with the given board and list of players.
     *
     * @param board   the board for this game
     * @param players the list of players participating in this game
     * @author Harris Majeed
     */
    public KwazamGameMaster(KwazamBoard board, CircularLinkedList<Player> players)
    {
        super(board, players);
    }

    /**
     * Advances the turn to the next player and switches Tor/Xor every second
     * turn according to the assignment specification.
     * @author Harris Majeed
     */
    @Override
    public void advanceTurn()
    {
        super.advanceTurn();

        if (this.turnCount % 2 == 0)
        {
            this.board.switchPieces();
        }
    }


    /**
     * Returns a two dimensional list of string representations for each cell on the board,
     * indicating the piece type, color, and orientation (if got).
     * The first player's pieces are shown in blue, and the second player's pieces in red
     * Pieces of type {@link Ram} include a flipped suffix when facing down.
     *
     * @return a 2D {@link List} of {@link String} objects describing each cell of the board
     * @author Abdullah Hawash
     */
    @Override
    public List<List<String>> getCellsStringRepresentation()
    {
        List<List<String>> formattedCells = new ArrayList<>();
        List<Player> players = this.getPlayers();

        for (List<Cell> row : this.board.getCells())
        {
            List<String> rowRepresentations = new ArrayList<>();
            for (Cell cell : row)
            {
                Optional<Piece> pieceOptional = cell.getPiece();
                if (pieceOptional.isEmpty())
                {
                    rowRepresentations.add(null);
                    continue;
                }

                Piece piece = pieceOptional.get();
                String pieceName = piece.getClass().getSimpleName().toLowerCase();

                Player owner = piece.getOwner();
                String color = "red";
                if (players.getFirst().id().equals(owner.id()))
                {
                    color = "blue";
                }

                String flippedSuffix = "";
                if (piece instanceof Ram)
                {
                    Ram ram = (Ram) piece;
                    if (!ram.isFacingUp())
                    {
                        flippedSuffix = "_flipped";
                    }
                }

                String representation = "%s_%s_piece%s".formatted(pieceName, color, flippedSuffix);
                rowRepresentations.add(representation);
            }
            formattedCells.add(rowRepresentations);
        }
        return formattedCells;
    }
}
