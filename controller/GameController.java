package controller;

import model.board.CellPosition;
import model.game.GameMaster;
import model.pieces.Piece;
import model.pieces.Ram;
import view.CellClickListener;
import view.KwazamGUI;

/**
 * Controller that mediates between the GUI (View) and the GameMaster/Board (Model).
 * <p>
 * Uses MVC: The View never accesses the Model directly.
 * All game logic is inside the Model; the Controller merely adapts the data flow.
 */
public class GameController implements CellClickListener
{
    private final KwazamGUI gui;
    private final GameMaster gameMaster;

    // Track which cell is currently “selected” by the player.
    private int selectedRow = -1;
    private int selectedCol = -1;
    private boolean isPieceSelected = false;

    // Keep a local copy of the piece-icon layout (strings) for the View.
    private final String[][] boardState;

    /**
     * Constructs a new GameController, wires itself to the GUI, and initializes the piece layout.
     * @param gui The KwazamGUI (View)
     * @param gameMaster The GameMaster (Model), which handles piece movement and turn order
     */
    public GameController(KwazamGUI gui, GameMaster gameMaster)
    {
        this.gui = gui;
        this.gameMaster = gameMaster;

        // Grab the initial piece icons from the View and store them.
        this.boardState = gui.getInitialPieceStartingPositions();

        // Let the GUI forward all user clicks to us
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
}
