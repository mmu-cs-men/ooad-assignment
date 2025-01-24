package view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * The AllMenuButtons class represents a panel containing the menu buttons for
 * the game. It includes buttons for saving the game, loading a game, and
 * starting a new game.
 * 
 * @author Sivananthan Seliyan
 */

public class AllMenuButtons extends JPanel
{

    /**
     * Constructor to initialize the AllMenuButtons panel with the necessary
     * buttons.
     */

    public AllMenuButtons()
    {
        setLayout(new GridBagLayout()); // Using GridBagLayout for flexible component positioning

        // Configure GridBagConstraints for button positioning
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 20, 0, 20);
        gbc.gridy = 0;

        // Create and configure buttons with their respective labels and colors
        JButton saveGameButton = createButton("SAVE GAME", new Color(255, 0, 0),
                Color.WHITE);
        JButton loadGameButton = createButton("LOAD GAME", new Color(0, 128, 0),
                Color.WHITE);
        JButton newGameButton = createButton("NEW GAME", new Color(0, 0, 255),
                Color.WHITE);

        // Add buttons to the panel at specific positions
        gbc.gridx = 0;
        add(saveGameButton, gbc);

        gbc.gridx = 1;
        add(loadGameButton, gbc);

        gbc.gridx = 2;
        add(newGameButton, gbc);

        // Set preferred size of the entire menu button panel
        setPreferredSize(new Dimension(500, 80));
    }

    /**
     * Helper method to create and configure a JButton with the given
     * parameters.
     *
     * @param text            The label of the button.
     * @param backgroundColor The background color of the button.
     * @param foregroundColor The foreground (text) color of the button.
     * @return A fully configured JButton instance.
     */

    private JButton createButton(String text, Color backgroundColor,
            Color foregroundColor)
    {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 40)); // Set the fixed button size
        button.setBackground(backgroundColor); // Set the background colour
        button.setForeground(foregroundColor); // Set the text color
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3)); // Set Border with thickness 3
        button.setFont(button.getFont().deriveFont(Font.BOLD, 14));
        button.setOpaque(true); // Ensure the background colour is visible
        button.setFocusPainted(false); // Disable the focus outline on click
        return button;
    }
}