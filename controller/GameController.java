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

    private void movePieceBackend(CellPosition fromCellPos, CellPosition toCellPos, String[][] board)
    {
        try
        {
            gameMaster.advanceTurn();
            gameMaster.movePiece(fromCellPos, toCellPos);

            // If the move was successful, let's find which piece ended up at 'toCellPos'
            Piece pieceJustMoved = gameMaster.getBoard()
                    .getPieceAt(toCellPos)
                    .orElse(null);

            // 1) Clear the old slot
            board[fromCellPos.row()][fromCellPos.column()] = null;

            // 2) Put the new icon in the new slot
            if (pieceJustMoved != null)
            {
                String iconName = pieceJustMoved.getIconName();
                board[toCellPos.row()][toCellPos.column()] = iconName;
            }
            else
            {
                board[toCellPos.row()][toCellPos.column()] = null;
            }
        }
        catch (NoPieceException | NotYourPieceException | PieceMoveException e)
        {
            System.err.println("Invalid move: " + e.getMessage());
            // If the move fails, do nothing to the board array.
        }

        // Re-render after either success or failure
        gui.renderPieceToBoard(board);
    }
}
