package model.persistence;

import model.board.Board;
import model.game.GameMaster;

import javax.swing.*;
import java.io.*;

/**
 * Handles saving and loading of the game state to and from a text file.
 */
public class PersistenceManager {

    private final GameMaster<?> gameMaster;

    public PersistenceManager(GameMaster<?> gameMaster) {
        this.gameMaster = gameMaster;
    }

    /**
     * Saves the current game state to a file selected by the user.
     */
    public void saveGame() {
        System.out.println("PersistenceManager: Saving game...");
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile()))) {
                writer.write("TurnCount:" + gameMaster.getTurnCount() + "\n");
                writer.write(gameMaster.getBoard().getBoardStateAsString());
                JOptionPane.showMessageDialog(null, "Game saved successfully!");
                System.out.println("Game state saved to file: " + fileChooser.getSelectedFile().getAbsolutePath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error saving game!");
                e.printStackTrace();
            }
        } else {
            System.out.println("Save operation cancelled.");
        }
    }

    public void loadGame() {
        System.out.println("PersistenceManager: Loading game...");
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
                String line = reader.readLine();
                if (line.startsWith("TurnCount:")) {
                    int turnCount = Integer.parseInt(line.split(":")[1].trim());
                    gameMaster.setTurnCount(turnCount);
                }

                gameMaster.getBoard().loadBoardStateFromString(reader);
                JOptionPane.showMessageDialog(null, "Game loaded successfully!");

                System.out.println("Game state successfully applied.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error loading game!");
                e.printStackTrace();
            }
        } else {
            System.out.println("Load operation cancelled.");
        }
    }

}