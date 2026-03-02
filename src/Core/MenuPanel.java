package Core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    private BabaIsYouWindow gameWindow;

    public MenuPanel(BabaIsYouWindow gameWindow) {
        this.gameWindow = gameWindow;
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        setBackground(Color.BLACK);

        // Заголовок
        JLabel titleLabel = new JLabel("BABA IS YOU");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 64));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Кнопка для уровня 1
        JButton level1Button = new JButton("Level 1");
        level1Button.setBackground(Color.BLACK);
        level1Button.setForeground(Color.WHITE);
        level1Button.setPreferredSize(new Dimension(200, 50));
        level1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWindow.loadLevel(gameWindow.level1());
                gameWindow.switchToGameView();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(level1Button, gbc);

        // Кнопка для уровня 2
        JButton level2Button = new JButton("Level 2");
        level2Button.setBackground(Color.BLACK);
        level2Button.setForeground(Color.WHITE);
        level2Button.setPreferredSize(new Dimension(200, 50));
        level2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWindow.loadLevel(gameWindow.level2());
                gameWindow.switchToGameView();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(level2Button, gbc);

        // Кнопка для уровня 3
        JButton level3Button = new JButton("Level 3");
        level3Button.setBackground(Color.BLACK);
        level3Button.setForeground(Color.WHITE);
        level3Button.setPreferredSize(new Dimension(200, 50));
        level3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWindow.loadLevel(gameWindow.level3());
                gameWindow.switchToGameView();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(level3Button, gbc);

        // Кнопка выхода
        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(Color.BLACK);
        exitButton.setForeground(Color.WHITE);
        exitButton.setPreferredSize(new Dimension(200, 50));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(exitButton, gbc);
    }



    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1200, 750);
    }
}