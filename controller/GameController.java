package controller;

import model.board.CellPosition;
import model.game.GameMaster;
import view.CellClickListener;
import view.KwazamGUI;
import model.game.Player;


public class GameController implements CellClickListener
{
    private final KwazamGUI gui;
    private final GameMaster gameMaster;

    private int selectedRow = -1;
    private int selectedCol = -1;
    private boolean isPieceSelected = false;
    private boolean gameOver = false;


    public GameController(KwazamGUI gui, GameMaster gameMaster)
    {
        // Initialize the GUI
        this.gui = gui;
        this.gameMaster = gameMaster;

        // Register this Controller as the listener for cell clicks
        this.gui.setCellClickListener(this);

        // Register win listener to handle game-over scenario
        this.gameMaster.setWinListener(this::handleWin);

    }

    @Override
    public void onCellClicked(int row, int col)
    {
        String[][] board = gui.getInitialPieceStartingPositions();


        if (gameOver)
        {
            return;
        }


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

    private void handleWin(Player winner)
    {
        gameOver = true;

        // Infer color based on player ID (assuming 1 = Red, 2 = Blue)
        String color = winner.id().equals("1") ? "Red" : "Blue";
        String message = "Player " + winner.id() + " (" + color + ")";

        gui.displayWinMessage(message);
        gui.disableBoard();
    }

}
