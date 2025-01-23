package model.game;

import model.board.KwazamBoard;
import utils.CircularLinkedList;

public class KwazamGameMaster extends GameMaster<KwazamBoard>
{
    public KwazamGameMaster(KwazamBoard board, CircularLinkedList<Player> players)
    {
        super(board, players);
    }

    @Override
    public void advanceTurn()
    {
        super.advanceTurn();

        if (this.turnCount % 2 == 0)
        {
            this.board.switchTorXor();
        }
    }
}
