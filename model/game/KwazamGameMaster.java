package model.game;

import model.board.KwazamBoard;
import utils.CircularLinkedList;

/**
 * Concrete implementation of {@code GameMaster} for Kwazam Chess.
 *
 * @author Harris Majeed
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
     */
    public KwazamGameMaster(KwazamBoard board, CircularLinkedList<Player> players)
    {
        super(board, players);
    }

    /**
     * Advances the turn to the next player and switches Tor/Xor every second
     * turn according to the assignment specification.
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
}
