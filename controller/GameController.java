package controller;

import model.board.CellPosition;
import model.exceptions.NoPieceException;
import model.exceptions.NotYourPieceException;
import model.exceptions.PieceMoveException;
import model.game.GameMaster;
import model.pieces.Piece;
import view.CellClickListener;
import view.KwazamGUI;

public class GameController implements CellClickListener
{
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

            // Moved into a helper method
            movePieceBackend(fromCellPos, toCellPos, board);

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
}
