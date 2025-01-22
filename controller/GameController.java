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

        // Render the initial board arrangement on the GUI
        gui.renderPieceToBoard(this.boardState);
    }

    /**
     * Called by the View every time a user clicks one cell on the board.
     * @param row the row index of the clicked cell
     * @param col the column index of the clicked cell
     */
    @Override
    public void onCellClicked(int row, int col)
    {
        if (!isPieceSelected)
        {
            // First click: select a piece if non-empty
            if (boardState[row][col] != null)
            {
                selectedRow = row;
                selectedCol = col;
                isPieceSelected = true;
            }
        }
        else
        {
            // Second click: user is trying to move from (selectedRow,selectedCol) to (row,col)
            CellPosition fromPos = new CellPosition(selectedRow, selectedCol);
            CellPosition toPos   = new CellPosition(row, col);

            // Advance turn in the Model and move the piece
            gameMaster.advanceTurn();
            gameMaster.movePiece(fromPos, toPos);

            // Clear the “old” spot in our local 2D array
            boardState[selectedRow][selectedCol] = null;

            // Figure out which piece now sits at the new position (in the Model)
            Piece pieceJustMoved = gameMaster.getBoard()
                    .getPieceAt(toPos)
                    .orElse(null);

            if (pieceJustMoved instanceof Ram ram)
            {
                // We differentiate red vs. blue by the Player's id().
                String iconName = getString(ram, pieceJustMoved);
                boardState[row][col] = iconName;
            }
            else
            {
                // If it isn’t a Ram, just copy over the old icon name
                boardState[row][col] = boardState[fromPos.row()][fromPos.column()];
            }

            // Redraw with the updated piece icons
            gui.renderPieceToBoard(boardState);

            // Reset selection
            isPieceSelected = false;
            selectedRow = -1;
            selectedCol = -1;
        }
    }

    private static String getString(Ram ram, Piece pieceJustMoved) {
        String color = pieceJustMoved.getOwner().id().equals("1")
                ? "blue"
                : "red";

        // If isFacingUp is false => normal icon. If true => “_flipped”.
        // BUT the red pieces start out with isFacingUp=false (which we want to show as "Ram_red").
        // The moment they hit the far edge, facingUp toggles to true => "Ram_red_flipped".
        // Blue pieces do the opposite because in KwazamBoard they start isFacingUp=true.
        // That means the default “Ram_blue” is displayed when facingUp=true,
        // and “Ram_blue_flipped” when false, etc.

        boolean facingUp = ram.isFacingUp();
        String iconName;

        if (color.equals("red"))
        {
            // Red Rams => normal icon if facingUp==false, flipped if true
            iconName = facingUp ? "Ram_red_flipped" : "Ram_red";
        }
        else
        {
            // Blue Rams => normal icon if facingUp==true, flipped if false
            iconName = facingUp ? "Ram_blue" : "Ram_blue_flipped";
        }
        return iconName;
    }
}
