package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The AllMenuButtons class represents a panel containing the menu buttons for
 * the game. It includes buttons for saving the game, loading a game, and
 * starting a new game.
 * <p>
 * This class sets up a flexible layout to accommodate the buttons and provides
 * a consistent styling for each button.
 *
 * @author Sivananthan Seliyan
 */

public class AllMenuButtons extends JPanel
{
    private JButton saveGameButton;
    private JButton loadGameButton;
    private JButton newGameButton;

    /**
     * The AllMenuButtons class represents a panel containing the menu buttons for
     * a game application. It includes buttons for saving the game, loading a game,
     * and starting a new game.
     * <p>
     * This class sets up a layout to hold the buttons (using {@link GridBagLayout})
     * and provides styling along with hover effects for a visually appealing
     * interface.
     * <p>
     * Usage:
     * <ul>
     *     <li>Create an instance of AllMenuButtons to get a panel with three
     *         buttons (SAVE, LOAD, NEW).</li>
     *     <li>Register action listeners for each button to handle user
     *         interactions.</li>
     * </ul>
     * @author Sivanathan Seliyan
     */
    public AllMenuButtons()
    {
        setLayout(new GridBagLayout()); // Using GridBagLayout for flexible component positioning

        // Configure GridBagConstraints for button positioning
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 20, 0, 20); // Add spacing between buttons
        gbc.gridy = 0; // Place all buttons in the same row

        // Create and configure buttons with their respective labels and colours
        saveGameButton = createButton("SAVE GAME", new Color(255, 0, 0), Color.WHITE);
        loadGameButton = createButton("LOAD GAME", new Color(0, 128, 0), Color.WHITE);
        newGameButton = createButton("NEW GAME", new Color(0, 0, 255), Color.WHITE);

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

    /**
     * Adds an ActionListener to the "SAVE GAME" button to handle save-related
     * user actions.
     *
     * @param listener the {@link ActionListener} to be invoked when the
     *                 "SAVE GAME" button is clicked.
     * @author Abdullah Hawash
     */
    public void addSaveGameListener(ActionListener listener)
    {
        saveGameButton.addActionListener(listener);
    }
    /**
     * Adds an ActionListener to the "LOAD GAME" button to handle load-related
     * user actions.
     *
     * @param listener the {@link ActionListener} to be invoked when the
     *                 "LOAD GAME" button is clicked.
     * @author Abdullah Hawash
     */
    public void addLoadGameListener(ActionListener listener)
    {
        loadGameButton.addActionListener(listener);
    }
    /**
     * Adds an ActionListener to the "NEW GAME" button to handle new-game-related
     * user actions.
     *
     * @param listener the {@link ActionListener} to be invoked when the
     *                 "NEW GAME" button is clicked.
     * @author Abdullah Hawash
     */
    public void addNewGameListener(ActionListener listener)
    {
        newGameButton.addActionListener(listener);
    }

    /**
     * Helper method to create and configure a JButton with the given
     * parameters. It sets the size, colours, font, and adds hover effects for
     * visual feedback.
     *
     * @param text            The label of the button.
     * @param backgroundColor The background colour of the button.
     * @param foregroundColor The foreground (text) colour of the button.
     * @return A fully configured JButton instance with applied styles and
     * effects.
     * @author Sivananthan Seliyan
     */
    private JButton createButton(String text, Color backgroundColor, Color foregroundColor)
    {
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
        button.addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                button.setBackground(backgroundColor.darker()); // Darken colour when hovering
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                button.setBackground(backgroundColor); // Restore original colour when mouse leaves
            }
        });

        return button; // Return the fully configured button
    }
}
