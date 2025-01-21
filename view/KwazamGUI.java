package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class KwazamGUI extends JFrame
{

    private final JButton[][] boardCells = new JButton[8][5]; // 8x5 grid of cells
    private final String[][] initialPieceStartingPositions =
            {
                    {"Tor_red", "Biz_red", "Sau_red", "Biz_red", "Xor_red"}, // Row
                    // 1
                    {"Ram_red", "Ram_red", "Ram_red", "Ram_red", "Ram_red"}, // Row
                    // 2
                    {null, null, null, null, null}, // Row 3
                    {null, null, null, null, null}, // Row 4
                    {null, null, null, null, null}, // Row 5
                    {null, null, null, null, null}, // Row 6
                    {"Ram_blue", "Ram_blue", "Ram_blue", "Ram_blue", "Ram_blue"}, // Row
                    // 7
                    {"Xor_blue", "Biz_blue", "Sau_blue", "Biz_blue", "Tor_blue"} // Row
                    // 8
            };
    private int prevRowClicked = -1, prevColClicked = -1;
    private CellClickListener cellClickListener;

    public KwazamGUI()
    {
        setTitle("Kwazam Chess Game");
        setSize(600, 800);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel boardPanel = new JPanel(new GridLayout(8, 5));
        boardPanel.setBorder(new EmptyBorder(50, 50, 50, 50));

        // Initialize the cells
        for (int row = 0; row < 8; row++)
        {
            for (int col = 0; col < 5; col++)
            {
                JButton cell = createCellButton(row, col);

                boardPanel.add(cell);
                boardCells[row][col] = cell;
            }
        }

        add(boardPanel, BorderLayout.CENTER);
        setVisible(true);

        renderPieceToBoard(initialPieceStartingPositions);

    }

    private JButton createCellButton(int row, int col)
    {
        JButton cell = new JButton();
        cell.setOpaque(true);
        cell.setBackground(Color.white);
        cell.setBorder(new LineBorder(Color.black, 2));
        cell.setFocusable(false);

        // center the icon in the label
        cell.setHorizontalAlignment(SwingConstants.CENTER);
        cell.setVerticalAlignment(SwingConstants.CENTER);

        int rowclicked = row;
        int colclicked = col;
        cell.addActionListener(
                e -> handleCellClick(rowclicked, colclicked));
        return cell;
    }

    // Dynamically render pieces on the board based on the given positions array
    public void renderPieceToBoard(String[][] positions)
    {
        for (int row = 0; row < positions.length; row++)
        {
            for (int col = 0; col < positions[row].length; col++)
            {
                JButton cell = boardCells[row][col];

                String piece = positions[row][col];

                if (piece != null)
                {
                    String imagePath = "assets/" + piece + ".png";
                    int cellWidth = cell.getWidth();
                    int cellHeight = cell.getHeight();

                    // Use the helper function
                    ImageIcon icon = loadScaledToCellIcon(imagePath, cellWidth,
                            cellHeight);
                    cell.setIcon(icon);
                }
                else
                {
                    // empty cell
                    cell.setIcon(null);
                }
            }
        }
    }

    /**
     * Loads an image from disk and scales it to the given width and height.
     * Returns null if the path is null or if width/height <= 0.
     */
    private ImageIcon loadScaledToCellIcon(String imagePath, int width,
                                           int height)
    {
        if (imagePath == null || width <= 0 || height <= 0)
        {
            return null;
        }
        try
        {
            ImageIcon rawIcon = new ImageIcon(imagePath);

            width = (int) Math.floor(width / 1.12);
            height = (int) Math.floor(height / 1.12);

            Image scaled = rawIcon.getImage().getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        }
        catch (Exception e)
        {
            System.err.println("Error loading image: " + imagePath);
            return null;
        }
    }

    private void handleCellClick(int row, int col)
    {

        if (prevRowClicked != -1 && prevColClicked != -1)
        {
            boardCells[prevRowClicked][prevColClicked]
                    .setBorder(new LineBorder(Color.BLACK, 2));
        }

        // Highlight newly clicked cell
        boardCells[row][col].setBorder(new LineBorder(Color.blue, 3));

        // Update the previous cell coordinates
        prevColClicked = col;
        prevRowClicked = row;

        System.out.println(
                "Button clicked at position: (" + row + ", " + col + ")");

        // Notify the listener (Controller)
        if (cellClickListener != null)
        {
            cellClickListener.onCellClicked(row, col);
        }

    }

    public void setCellClickListener(CellClickListener listener)
    {
        this.cellClickListener = listener;
    }

    // Getter for initial piece starting positions
    public String[][] getInitialPieceStartingPositions()
    {
        return initialPieceStartingPositions;
    }
}
