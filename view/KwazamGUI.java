package view;

import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class KwazamGUI extends JFrame {

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

    private int prevRowClicked = -1, prevColClicked = -1;
    private CellClickListener cellClickListener;
    private JLabel winLabel; // Label for displaying the win message

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

        // Add label for win message at the bottom
        winLabel = new JLabel("", SwingConstants.CENTER);
        winLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(winLabel, BorderLayout.SOUTH);

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
        cell.addActionListener(e -> handleCellClick(row, col));
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
            g2d.drawImage(rawImage, x, y, scaledWidth, scaledHeight, null);
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
     * @author Abdullah Hawash
     */
    public void disableBoard() {
        for (JButton[] row : boardCells) {
            for (JButton cell : row) {
                cell.setEnabled(false);
            }
        }
    }

    /**
     * @author Abdullah Hawash
     * @param message
     * @param color
     */
    public void displayWinMessage(String message, Color color) {
        winLabel.setText(message);
        winLabel.setForeground(color);
        winLabel.setVisible(true);
    }

}