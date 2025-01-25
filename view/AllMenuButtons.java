package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

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
        JButton saveGameButton = createButton("SAVE GAME", new Color(255, 0, 0), Color.WHITE);
        JButton loadGameButton = createButton("LOAD GAME", new Color(0, 128, 0), Color.WHITE);
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

        // Add action listener to "New Game" button
        newGameButton.addActionListener(e -> {
            try {
                showNewGameConfirmation();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    /**
     * Displays a confirmation dialog when the "New Game" button is clicked.
     * @author Sivananthan Seliyan
     * If the user clicks "Yes", the logic to start a new game will be executed.
     * @throws IOException 
     */
    private void showNewGameConfirmation() throws IOException {
        int response = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to start a new game? Your progress will be lost mate!",
                "NEW GAME CONFIRMATION",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (response == JOptionPane.YES_OPTION) {
            restartFromIDE();
    }
         else {
            System.out.println("User canceled starting a new game.");
        }
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

    private static void restartFromIDE() {
        try {
            // Get the current Java executable path
            String javaBin = System.getProperty("java.home") + "/bin/java";

            // Get the classpath to include all dependencies
            String classpath = System.getProperty("java.class.path");

            // Get the main class name
            String className = "Main";

            // Build the command to relaunch
            ArrayList<String> command = new ArrayList<>();
            command.add(javaBin);
            command.add("-cp");
            command.add(classpath);
            command.add(className);

            // Print command for debugging
            System.out.println("Restart command: " + command);

            // Start the new process
            new ProcessBuilder(command).inheritIO().start();

            // Exit current application
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to restart: " + e.getMessage());
        }
    }
}