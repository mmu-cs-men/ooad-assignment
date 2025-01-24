package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class KwazamGUI extends JFrame
{

    private final JButton[][] boardCells = new JButton[8][5]; // 8x5 grid of
    // cells
    private final String[][] initialPieceStartingPositions =

            {
                    {"Tor_red_piece", "Biz_red_piece", "Sau_red_piece", "Biz_red_piece",
                            "Xor_red_piece"}, // Row
                    // 1
                    {"Ram_red_piece", "Ram_red_piece", "Ram_red_piece", "Ram_red_piece",
                            "Ram_red_piece"}, // Row
                    // 2
                    {null, null, null, null, null}, // Row 3
                    {null, null, null, null, null}, // Row 4
                    {null, null, null, null, null}, // Row 5
                    {null, null, null, null, null}, // Row 6
                    {"Ram_blue_piece", "Ram_blue_piece", "Ram_blue_piece",
                            "Ram_blue_piece", "Ram_blue_piece"}, // Row
                    // 7
                    {"Xor_blue_piece", "Biz_blue_piece", "Sau_blue_piece",
                            "Biz_blue_piece", "Tor_blue_piece"} // Row
                    // 8
            };
    private boolean flipped = false;
    private int prevRowClicked = -1, prevColClicked = -1;
    private CellClickListener cellClickListener;
    private JLabel winLabel; // Label for displaying the win message
    private boolean torXorSwitched = false;

    public KwazamGUI()
    {
        setTitle("Kwazam Chess Game");
        setSize(550, 800);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        AllMenuButtons menuButtons = new AllMenuButtons();
        // Set the Button Position on top of the Kwazam Chess game
        add(menuButtons, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(new GridLayout(8, 5));
        boardPanel.setBorder(new EmptyBorder(1, 50, 50, 50));

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

        // Now that the layout is done, re-render the board in the next event cycle
        SwingUtilities.invokeLater(() -> {
            renderPieceToBoard(initialPieceStartingPositions);
        });

        // Add label for win message at the bottom
        winLabel = new JLabel("", SwingConstants.CENTER);
        winLabel.setFont(new Font("Arial", Font.BOLD, 20));

        //Reserve space for the label from the start
        winLabel.setPreferredSize(new Dimension(0, 50));

        add(winLabel, BorderLayout.SOUTH);

    }

    private static Timer getTimer(Color defaultColor, JButton cell)
    {
        final Timer flashTimer = new Timer(150, null);

        // This counter tracks how many times we've toggled
        // so we can stop after 2 on/off cycles (4 toggles total basically)
        // array cuz lambdas don't support non-final variables
        final int[] toggleCount = {0};

        flashTimer.addActionListener(e -> {
            // Toggle between default color and red
            if (cell.getBackground() == Color.RED)
            {
                cell.setBackground(defaultColor);
            }
            else
            {
                cell.setBackground(Color.RED);
            }
            toggleCount[0]++;

            // After 4 toggles => 2 flashes completed
            if (toggleCount[0] == 4)
            {
                flashTimer.stop();
                // Ensure we end on the default color
                cell.setBackground(defaultColor);
            }
        });
        return flashTimer;
    }

    private JButton createCellButton(int row, int col)
    {
        JButton cell = new JButton();
        cell.setOpaque(true);

        Color evenColour = new Color(112, 128, 144);
        Color oddColour = new Color(245, 245, 245);

        // Alternate colors based on (row + col) parity
        boolean colour = (row + col) % 2 == 0;
        cell.setBackground(colour ? evenColour : oddColour);

        // store this original color in the buttons client property
        cell.putClientProperty("defaultBackground", cell.getBackground());

        cell.setBorder(new LineBorder(Color.black, 2));
        cell.setFocusable(false);

        // center the icon in the label
        cell.setHorizontalAlignment(SwingConstants.CENTER);
        cell.setVerticalAlignment(SwingConstants.CENTER);
        cell.addActionListener(e -> handleCellClick(row, col));
        return cell;
    }

    // Method to visually toggle Tor/Xor pieces after 2 turns
    public void toggleTorXorVisuals()
    {
        torXorSwitched = !torXorSwitched;
        renderPieceToBoard(initialPieceStartingPositions);
    }

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
                    // Swap Tor/Xor visually based on the toggle state
                    if (torXorSwitched)
                    {
                        if (piece.startsWith("Tor"))
                        {
                            piece = piece.replace("Tor", "Xor");
                        }
                        else if (piece.startsWith("Xor"))
                        {
                            piece = piece.replace("Xor", "Tor");
                        }
                    }
                    String imagePath = "assets/" + piece + ".png";
                    ImageIcon icon = loadScaledToCellIcon(imagePath,
                            cell.getWidth(), cell.getHeight());
                    cell.setIcon(icon);
                }
                else
                {
                    cell.setIcon(null);
                }
            }
        }
    }

    /*
     * Image Scaling Logic: This section calculates the dimensions to scale the
     * original image such that: 1. The image fits within the padded target
     * area(cell dimensions minus padding). 2. The aspect ratio (width-to-height
     * proportion) is preserved to avoid distortion. Steps taken first we get
     * the original image's width and height. Using that we calculate
     * width/height ratios between the padded target area and original image. We
     * then wse the SMALLER ratio to scale the image, ensuring it fits entirely
     * within both the target width and height constraints and finally we derive
     * the final scaled width/height using this ratio.
     */
    private ImageIcon loadScaledToCellIcon(String imagePath, int targetWidth,
                                           int targetHeight)
    {
        if (imagePath == null || targetWidth <= 0 || targetHeight <= 0)
        {
            return null;
        }
        try
        {
            ImageIcon rawIcon = new ImageIcon(imagePath);
            Image rawImage = rawIcon.getImage();

            // Handling Padding
            int padding = 3;
            int paddedTargetWidth = targetWidth - 2 * padding;
            int paddedTargetHeight = targetHeight - 2 * padding;

            // Scalling Calculations
            int originalWidth = rawImage.getWidth(null);
            int originalHeight = rawImage.getHeight(null);
            double widthRatio = (double) paddedTargetWidth / originalWidth;
            double heightRatio = (double) paddedTargetHeight / originalHeight;
            double scale = Math.min(widthRatio, heightRatio);

            int scaledWidth = (int) (originalWidth * scale);
            int scaledHeight = (int) (originalHeight * scale);

            // Create a transparent canvas to hold the chess piece icon This
            // ensures the icon is centered and doesn't stretch.
            BufferedImage paddedImage = new BufferedImage(targetWidth,
                    targetHeight, BufferedImage.TYPE_INT_ARGB);

            // Enable smooth scaling for the icon (avoids pixelation)
            // Bilinear interpolation blends pixels for a smoother appearance.
            Graphics2D g2d = paddedImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            // Center the scaled image
            int x = (targetWidth - scaledWidth) / 2;
            int y = (targetHeight - scaledHeight) / 2;

            if (this.flipped)
            {
                g2d.translate(x + scaledWidth, y + scaledHeight);
                g2d.scale(-1, -1);
                g2d.drawImage(rawImage, 0, 0, scaledWidth, scaledHeight, null);
            }
            else
            {
                // No flipping
                g2d.drawImage(rawImage, x, y, scaledWidth, scaledHeight, null);
            }

            g2d.dispose();
            return new ImageIcon(paddedImage);
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

    /**
     * @author Abdullah Hawash Disables all cells on the board, stopping further
     * user interaction
     * <p>
     * This method is invoked at the end of the game or when input should be
     * restricted to prevent further moves
     * </p>
     */
    public void disableBoard()
    {
        for (JButton[] row : boardCells)
        {
            for (JButton cell : row)
            {
                cell.setEnabled(false);
            }
        }
    }

    /**
     * @param message the text message to be displayed; cannot be {@code null}
     * @param color   the color of the displayed message; cannot be
     *                {@code null}
     * @author Abdullah Hawash Displays a message indicating the game has been
     * won by updating the win label. The message is shown in the specified
     * color and is made visible on the screen.
     * <p>
     * This method called when a player wins the game to provide visual feedback
     * to the user (a game win message below the board)
     * </p>
     */
    public void displayWinMessage(String message, Color color)
    {
        winLabel.setText(message);
        winLabel.setForeground(color);
        winLabel.setVisible(true);
        winLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
    }

    /**
     * @param row the zero-based row index of the cell to be flashed; must be
     *            within the bounds of the board
     * @param col the zero-based column index of the cell to be flashed; must be
     *            within the bounds of the board
     * @author Abdullah Hawash Temporarily flashes the specified cell in red to
     * indicate an error The cell's original color is restored after a smal
     * period
     * <p>
     * This method is used to provide visual feedback for invalid moves or other
     * incorrect actions
     * </p>
     */
    public void flashCellRed(int row, int col)
    {
        JButton cell = boardCells[row][col];

        // get the cell's true default color (stored at creation)
        // this is to prevent the cell from permanently becoming red after spamming the invalid move
        Color defaultColor = (Color) cell.getClientProperty("defaultBackground");
        if (defaultColor == null)
        {
            // Fallback in case client property isn't set
            defaultColor = cell.getBackground();
        }

        final Timer flashTimer = getTimer(defaultColor, cell);

        flashTimer.start();
    }

    /**
     * Flips the orientation of a Ram piece at the specified board position. If
     * the piece is in its default orientation, it will be flipped; if already
     * flipped, it will revert to its original state.
     * <p>
     * This method updates the piece's visual representation and its internal
     * tracking state. The piece must exist at the specified position and be of
     * type "Ram".
     * <p>
     * Usage example:
     * <pre>
     *     board.flipRamPiece(2, 3);
     * </pre>
     * This call will flip the Ram piece at row 2, column 3 if present.
     *
     * @param row the row index of the target cell; must be within board
     *            bounds.
     * @param col the column index of the target cell; must be within board
     *            bounds.
     * @throws ArrayIndexOutOfBoundsException if the provided row or column
     *                                        exceeds board dimensions.
     * @author Abdullah Hawash
     * @see #loadScaledToCellIcon(String, int, int)
     */
    public void flipRamPiece(int row, int col)
    {
        JButton cell = boardCells[row][col];
        String piece = initialPieceStartingPositions[row][col];

        if (piece != null && piece.startsWith("Ram"))
        {
            // Check current orientation and flip accordingly
            String flippedPiece = piece.endsWith("_flipped")
                    ? piece.replace("_flipped", "")  // Switch back to normal
                    : piece + "_flipped";  // Switch to flipped

            String imagePath = "assets/" + flippedPiece + ".png";

            int cellWidth = cell.getWidth();
            int cellHeight = cell.getHeight();

            ImageIcon flippedIcon = loadScaledToCellIcon(imagePath, cellWidth, cellHeight);

            if (flippedIcon != null)
            {
                cell.setIcon(flippedIcon);
                initialPieceStartingPositions[row][col] = flippedPiece;
            }
            else
            {
                System.err.println("Error: Could not load image: " + imagePath);
            }
        }


    }

    private void flipPiecesPosition()
    {
        // Reverse each row
        for (String[] row : this.initialPieceStartingPositions)
        {
            if (row == null)
            {
                continue;
            }
            reverseRow(row);
        }

        // Reverse the order of the rows
        int numRows = this.initialPieceStartingPositions.length;
        for (int i = 0; i < numRows / 2; i++)
        {
            String[] temp = this.initialPieceStartingPositions[i];
            this.initialPieceStartingPositions[i] = this.initialPieceStartingPositions[numRows - 1 - i];
            this.initialPieceStartingPositions[numRows - 1 - i] = temp;
        }
    }

    private void reverseRow(String[] row)
    {
        int start = 0;
        int end = row.length - 1;
        while (start < end)
        {
            String temp = row[start];
            row[start] = row[end];
            row[end] = temp;
            start++;
            end--;
        }
    }

    public void flipBoard()
    {
        this.flipped = !this.flipped;
        this.flipPiecesPosition();
    }

    public boolean isFlipped()
    {
        return flipped;
    }
}