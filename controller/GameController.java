package controller;

import model.board.CellPosition;
import model.exceptions.NoPieceException;
import model.exceptions.NotYourPieceException;
import model.exceptions.PieceMoveException;
import model.game.KwazamGameMaster;
import model.game.Player;
import model.listeners.WinListener;
import model.serialization.GameState;
import model.serialization.SaveLoadManager;
import model.serialization.SaveLoadSerializer;
import view.CellClickListener;
import view.KwazamGUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.List;

public class GameController implements CellClickListener, WinListener
{

    private final KwazamGUI gui;
    private final KwazamGameMaster gameMaster;
    private final SaveLoadSerializer saveLoadSerializer;
    private final SaveLoadManager saveLoadManager;

    private int selectedRow = -1;
    private int selectedCol = -1;
    private boolean isPieceSelected = false;

    public GameController(KwazamGUI gui, KwazamGameMaster gameMaster, SaveLoadSerializer saveLoadSerializer, SaveLoadManager saveLoadManager)
    {
        // Initialize the GUI
        this.gui = gui;
        this.gameMaster = gameMaster;

        // Register this Controller as the listener for cell clicks
        this.gui.setCellClickListener(this);

        // Register GUI as a listener to display the win message when a win occurs
        this.gameMaster.registerWinListener(this);

        this.saveLoadSerializer = saveLoadSerializer;
        this.saveLoadManager = saveLoadManager;

        gui.setBoard(gameMaster.getCellsStringRepresentation());

        setupMenuListeners();
    }

    private void setupMenuListeners()
    {
        // Save Game button handler
        gui.addSaveGameListener(e -> handleSaveGame());

        // Load Game button handler
        gui.addLoadGameListener(e -> handleLoadGame());

        gui.addNewGameListener(e -> handleNewGame());
    }

    private void handleSaveGame()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Game");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text files (*.txt)", "txt"));

        int userSelection = fileChooser.showSaveDialog(gui);

        if (userSelection == JFileChooser.APPROVE_OPTION)
        {
            File fileToSave = fileChooser.getSelectedFile();
            GameState gameState = this.saveLoadManager.saveGame();
            this.saveLoadSerializer.saveStateToFile(gameState, fileToSave.getAbsolutePath());
        }
    }

    private void handleLoadGame()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load Game");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text files (*.txt)", "txt"));

        int userSelection = fileChooser.showOpenDialog(gui);

        if (userSelection == JFileChooser.APPROVE_OPTION)
        {
            File fileToLoad = fileChooser.getSelectedFile();
            GameState gameState = this.saveLoadSerializer.loadStateFromFile(fileToLoad.getAbsolutePath());
            this.saveLoadManager.loadGame(gameState);
            gui.enableBoard();
            gui.setBoard(gameMaster.getCellsStringRepresentation());
            gui.setFlipped(false);
            if (gameMaster.getTurnCount() % 2 != 0)
            {
                gui.flipBoard();
            }
            if (gui.isTorXorSwitched())
            {
                gui.toggleTorXorVisuals();
            }
            gui.disableWinMessage();
        }
    }

    private void handleNewGame()
    {
        GameState gameState = this.saveLoadSerializer.loadStateFromFile("assets/new-game.txt");
        this.saveLoadManager.loadGame(gameState);
        gui.enableBoard();
        gui.setBoard(gameMaster.getCellsStringRepresentation());
        gui.setFlipped(false);
        if (gameMaster.getTurnCount() % 2 != 0)
        {
            gui.flipBoard();
        }
        if (gui.isTorXorSwitched())
        {
            gui.toggleTorXorVisuals();
        }
        gui.disableWinMessage();
    }

    @Override
    public void onCellClicked(int row, int col)
    {
        List<List<String>> board = gui.getInitialPieceStartingPositions();

        if (!isPieceSelected)
        {
            // First click: Select a piece
            if (board.get(row).get(col) != null)
            {
                selectedRow = row;
                selectedCol = col;
                isPieceSelected = true;

            }
        }
        else
        {
            CellPosition fromCellPos = new CellPosition(selectedRow, selectedCol);
            CellPosition toCellPos = new CellPosition(row, col);

            if (gui.isFlipped())
            {
                fromCellPos = this.flipCellPos(fromCellPos, 8, 5);
                toCellPos = this.flipCellPos(toCellPos, 8, 5);
            }

            try
            {
                gameMaster.movePiece(fromCellPos, toCellPos);

                // Perform the movement
                board.get(row).set(col, board.get(selectedRow).get(selectedCol)); // Move the piece
                board.get(selectedRow).set(selectedCol, null); // Clear the original
                // position

                if ((row == 0 || row == 7) && board.get(row).get(col) != null && board.get(row).get(col).startsWith("ram"))
                {
                    gui.flipRamPiece(row, col);
                }

                gui.flipBoard();

                // Update the View
                gui.renderPieceToBoard(board);
            }
            catch (PieceMoveException e)
            {
                // blocked path, wrong direction, etc.
                gui.flashCellRed(row, col);
                return;
            }
            catch (NoPieceException | NotYourPieceException e)
            {
                gui.flashCellRed(selectedRow, selectedCol);
                return;
            }
            finally
            {
                // Reset selection whether the move was successful or not
                selectedRow = -1;
                selectedCol = -1;
                isPieceSelected = false;

            }

            gameMaster.advanceTurn();

            // Check if it's time to swap visuals
            if (gameMaster.getTurnCount() % 2 == 0)
            {
                gui.toggleTorXorVisuals();
            }
        }
    }

    private CellPosition flipCellPos(CellPosition cellPos, int boardRows, int boardColumns)
    {
        return new CellPosition(boardRows - 1 - cellPos.row(), boardColumns - 1 - cellPos.column());
    }

    /**
     * @param winner the player who won the game; must not be {@code null}
     * @author Abdullah Hawash Handles the win event by disabling the board and
     * displaying the winner's message
     * <p>
     * This method is triggered when a player wins the game, updating the user
     * interface to reflect the winning player with an appropriate message and
     * color
     * </p>
     */
    @Override
    public void onWin(Player winner)
    {
        gui.disableBoard();
        gui.displayWinMessage(winner.id().equals("1") ? "Blue wins!!" : "Red wins!!",
                winner.id().equals("1") ? Color.BLUE : Color.RED);
    }
}
