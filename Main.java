import controller.GameController;
import model.board.KwazamBoard;
import model.game.KwazamGameMaster;
import model.game.Player;
import model.serialization.SaveLoadManager;
import model.serialization.SaveLoadSerializer;
import utils.CircularLinkedList;
import view.KwazamGUI;

/**
 * @author Laxman Pillai -> creator
 */
public class Main
{
    public static void main(String[] args)
    {
        CircularLinkedList<Player> players = new CircularLinkedList<>();
        players.add(new Player("1")); // TODO placeholder
        players.add(new Player("2")); // TODO placeholder

        KwazamBoard board = new KwazamBoard(players);

        KwazamGameMaster gameMaster = new KwazamGameMaster(board, players);
        KwazamGUI gui = new KwazamGUI();
        SaveLoadManager saveLoadManager = new SaveLoadManager(gameMaster, board);
        SaveLoadSerializer saveLoadSerializer = new SaveLoadSerializer();
        new GameController(gui, gameMaster, saveLoadSerializer, saveLoadManager);
    }
}
