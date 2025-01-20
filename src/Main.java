import controller.GameController;
import model.board.Board;
import model.board.KwazamBoard;
import model.game.GameMaster;
import model.game.Player;
import utils.CircularLinkedList;
import view.KwazamGUI;

public class Main
{
    public static void main(String[] args)
    {
        CircularLinkedList<Player> players = new CircularLinkedList<>();
        players.add(new Player("1")); // TODO placeholder
        players.add(new Player("2")); // TODO placeholder

        Board board = new KwazamBoard(players);

        GameMaster gameMaster = new GameMaster(board, players);
        KwazamGUI gui = new KwazamGUI();
        new GameController(gui, gameMaster);
    }
}
