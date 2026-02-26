package Core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoseScreen extends JPanel {
    private BabaIsYouWindow gameWindow;

    public LoseScreen(BabaIsYouWindow gameWindow) {
        this.gameWindow = gameWindow;
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        setBackground(Color.BLACK);


        // Сообщение о поражении
        JLabel loseLabel = new JLabel("YOU LOSE!");
        loseLabel.setFont(new Font("Arial", Font.BOLD, 48));
        loseLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        add(loseLabel, gbc);

        // Кнопка возврата в меню
        JButton menuButton = new JButton("Main Menu");
        menuButton.setBackground(Color.BLACK);
        menuButton.setForeground(Color.WHITE);
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
        JButton retryButton = new JButton("Try Again");
        retryButton.setBackground(Color.BLACK);
        retryButton.setForeground(Color.WHITE);
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