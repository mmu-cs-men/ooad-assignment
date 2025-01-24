package controller;

import model.board.CellPosition;
import model.game.GameMaster;
import model.game.Player;
import model.listeners.WinListener;
import view.CellClickListener;
import view.KwazamGUI;
import java.awt.Color;

public class GameController implements CellClickListener, WinListener {

    private final KwazamGUI gui;
    private final GameMaster gameMaster;

    private int selectedRow = -1;
    private int selectedCol = -1;
    private boolean isPieceSelected = false;

    public GameController(KwazamGUI gui, GameMaster gameMaster)
    {
        // Initialize the GUI
        this.gui = gui;
        this.gameMaster = gameMaster;

        // Register this Controller as the listener for cell clicks
        this.gui.setCellClickListener(this);

        // Register GUI as a listener to display the win message when a win occurs
        this.gameMaster.registerWinListener(this);
    }

    @Override
    public void onCellClicked(int row, int col)
    {
        String[][] board = gui.getInitialPieceStartingPositions();

        if (!isPieceSelected)
        {
            // First click: Select a piece
            if (board[row][col] != null)
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

            movePieceBackend(fromCellPos, toCellPos);

            // Perform the movement
            board[row][col] = board[selectedRow][selectedCol]; // Move the piece
            board[selectedRow][selectedCol] = null; // Clear the original
            // position

            // Update the View
            gui.renderPieceToBoard(board);
            board = gui.flipBoard();

            // Reset the selection
            selectedRow = -1;
            selectedCol = -1;
            isPieceSelected = false;
        }
    }

    private void movePieceBackend(CellPosition fromCellPos, CellPosition toCellPos)
    {
        gameMaster.advanceTurn();
        gameMaster.movePiece(fromCellPos, toCellPos);
    }

    /**
     * @author Abdullah Hawash
     * @param winner
     */
    @Override
    public void onWin(Player winner) {
        gui.disableBoard();
        gui.displayWinMessage(winner.id().equals("1") ? "Blue wins!!" : "Red wins!!",
                winner.id().equals("1") ? Color.BLUE : Color.RED);
    }
}
