package View;

import javax.swing.*;
import java.awt.*;

public class LoseOverlay extends JPanel {
    private final BabaIsYouWindow gameWindow;
    private static final String FONT = "Fixedsys Excelsior 3.01";

    public LoseOverlay(BabaIsYouWindow gameWindow) {
        this.gameWindow = gameWindow;
        setOpaque(false);
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel loseLabel = new JLabel("YOU LOSE!");
        loseLabel.setFont(new Font(FONT, Font.BOLD, 48));
        loseLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        add(loseLabel, gbc);

        JButton menuButton = new JButton("Main Menu");
        menuButton.setFont(new Font(FONT, Font.BOLD, 32));
        menuButton.setBackground(Color.BLACK);
        menuButton.setForeground(Color.WHITE);
        menuButton.setPreferredSize(new Dimension(250, 50));
        menuButton.addActionListener(e -> gameWindow.switchToMenuView());
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(menuButton, gbc);

        JButton retryButton = new JButton("Try Again");
        retryButton.setFont(new Font(FONT, Font.BOLD, 32));
        retryButton.setBackground(Color.BLACK);
        retryButton.setForeground(Color.WHITE);
        retryButton.setPreferredSize(new Dimension(250, 50));
        retryButton.addActionListener(e -> gameWindow.restartCurrentLevel());
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(retryButton, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(new Color(0, 0, 0, 180));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
    }
}