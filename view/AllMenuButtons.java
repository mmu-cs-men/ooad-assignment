package view;

import javax.swing.*;
import java.awt.*;

public class AllMenuButtons extends JPanel
{

    public AllMenuButtons()
    {
        setLayout(new GridBagLayout());

        // GridBagLayout settings
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 20, 0, 20);
        gbc.gridy = 0;

        // Create buttons
        JButton saveGameButton = createButton("SAVE GAME", new Color(255, 0, 0),
                Color.WHITE);
        JButton loadGameButton = createButton("LOAD GAME", new Color(0, 128, 0),
                Color.WHITE);
        JButton newGameButton = createButton("NEW GAME", new Color(0, 0, 255),
                Color.WHITE);

        gbc.gridx = 0;
        add(saveGameButton, gbc);

        gbc.gridx = 1;
        add(loadGameButton, gbc);

        gbc.gridx = 2;
        add(newGameButton, gbc);

        setPreferredSize(new Dimension(500, 80));
    }

    private JButton createButton(String text, Color backgroundColor,
            Color foregroundColor)
    {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 40)); // Set fixed size
        button.setBackground(backgroundColor);
        button.setForeground(foregroundColor);
        button.setFont(new Font("Montserrat", Font.BOLD, 14));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
    }
}