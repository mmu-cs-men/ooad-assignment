import controller.GameController;
import view.KwazamGUI;

public class Main
{
    public static void main(String[] args)
    {
        KwazamGUI gui = new KwazamGUI();
        new GameController(gui);
    }
}
