package Core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WinScreen extends JPanel {
    private BabaIsYouWindow gameWindow;

    public WinScreen(BabaIsYouWindow gameWindow) {
        this.gameWindow = gameWindow;
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Сообщение о победе
        JLabel winLabel = new JLabel("YOU WIN!");
        winLabel.setFont(new Font("Arial", Font.BOLD, 48));
        winLabel.setForeground(Color.GREEN);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        add(winLabel, gbc);

        // Кнопка возврата в меню
        JButton menuButton = new JButton("Main Menu");
        menuButton.setPreferredSize(new Dimension(200, 50));
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWindow.switchToMenuView();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(menuButton, gbc);

        // Кнопка повторной попытки
        JButton retryButton = new JButton("Play Again");
        retryButton.setPreferredSize(new Dimension(200, 50));
        retryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWindow.restartCurrentLevel();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(retryButton, gbc);
    }
}