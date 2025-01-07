package controller;

import view.CellClickListener;
import view.KwazamGUI;

public class GameController implements CellClickListener
{
    private KwazamGUI gui;

    private int selectedRow = -1;
    private int selectedCol = -1;
    private boolean isPieceSelected = false;

    public GameController()
    {
        // Initialize the GUI
        gui = new KwazamGUI();

        // Register this Controller as the listener for cell clicks
        gui.setCellClickListener(this);

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
            // Second click: Move the selected piece to the destination cell
            functionNamedByHarrisLater(); //TODO For Mr Harris Ltr

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

    private void functionNamedByHarrisLater()
    {
        //TODO - Implement things here
    }




    public static void main(String[] args)
    {
        // Entry point for the program
        new GameController(); // Initialize the Controller and start the
                              // application
    }
}
