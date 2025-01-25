package view;

import javax.swing.*;
import java.awt.*;

/**
 * The AllMenuButtons class represents a panel containing the menu buttons for
 * the game. It includes buttons for saving the game, loading a game, and
 * starting a new game.
 * 
 * This class sets up a flexible layout to accommodate the buttons and provides
 * a consistent styling for each button.
 * 
 * @author Sivananthan Seliyan
 */

public class AllMenuButtons extends JPanel {

    private final JButton saveGameButton;
    private final JButton loadGameButton;


    /**
     * Constructor to initialize the AllMenuButtons panel with the necessary buttons.
     * It sets up the layout, creates buttons, and adds them to the panel with
     * appropriate positioning and styling.
     */
    public AllMenuButtons() {
        setLayout(new GridBagLayout()); // Using GridBagLayout for flexible component positioning

        // Configure GridBagConstraints for button positioning
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 20, 0, 20); // Add spacing between buttons
        gbc.gridy = 0; // Place all buttons in the same row

        // Create and configure buttons with their respective labels and colours
        saveGameButton = createButton("SAVE GAME", new Color(255, 0, 0), Color.WHITE);
        loadGameButton = createButton("LOAD GAME", new Color(0, 128, 0), Color.WHITE);
        JButton newGameButton = createButton("NEW GAME", new Color(0, 0, 255), Color.WHITE);

        // Add the "SAVE GAME" button to the left 
        gbc.gridx = 0;
        add(saveGameButton, gbc);

        // Add the "LOAD GAME" button in the middle 
        gbc.gridx = 1;
        add(loadGameButton, gbc);

        // Add the "NEW GAME" button to the right
        gbc.gridx = 2;
        add(newGameButton, gbc);

        // Set the preferred size of the whole panel to fit everything nicely
        setPreferredSize(new Dimension(500, 80));
    }

    public JButton getSaveGameButton() {
        return saveGameButton;
    }

    public JButton getLoadGameButton() {
        return loadGameButton;
    }

    /**
     * Helper method to create and configure a JButton with the given parameters.
     * It sets the size, colours, font, and adds hover effects for visual feedback.
     *
     * @param text            The label of the button.
     * @param backgroundColor The background colour of the button.
     * @param foregroundColor The foreground (text) colour of the button.
     * @return A fully configured JButton instance with applied styles and effects.
     */
    private JButton createButton(String text, Color backgroundColor, Color foregroundColor) {
        JButton button = new JButton(text); // Create button with the provided text

        // Set a fixed button size to ensure uniformity
        button.setPreferredSize(new Dimension(120, 40));

        // Apply the specified background and text colours
        button.setBackground(backgroundColor);
        button.setForeground(foregroundColor);

        // Set a visible black border with thickness of 3 pixels
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        // Set the button font to bold with a size of 14
        button.setFont(button.getFont().deriveFont(Font.BOLD, 14));

        // Ensure the background colour is applied by making the button opaque
        button.setOpaque(true);

        // Remove focus outline when the button is clicked
        button.setFocusPainted(false);

        // Add a hover effect --> darken the button when the mouse is over it 
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.darker()); // Darken colour when hovering
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor); // Restore original colour when mouse leaves
            }
        });

        return button; // Return the fully configured button
    }
}
