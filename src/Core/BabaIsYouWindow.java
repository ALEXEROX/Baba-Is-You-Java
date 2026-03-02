package Core;

import GameObjects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BabaIsYouWindow extends JFrame {


    //============================Поля=================================

    // Логика
    private Level currentlevel;
    private Status status;
    private KeyListener keyListener;

    // Визуал
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private MenuPanel menuPanel;
    private WinScreen winScreen;
    private LoseScreen loseScreen;


    //==========================Конструктор============================

    public BabaIsYouWindow(){
        initializeComponents();
        createKeyListener();
        buildWindow();
    }


    //=======================Управление-окном===========================

    /**
     * Загружает уровен для его отображения в окне
     */
    public void loadLevel(Level level){
        if(currentlevel != null) {
            mainPanel.remove(currentlevel);
        }

        currentlevel = level;
        mainPanel.add(currentlevel, "GAME");
        cardLayout.show(mainPanel, "GAME");
        currentlevel.makeStep(Direction.STAY);

        updateWindowSize();
    }

    private void initializeComponents(){
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        menuPanel = new MenuPanel(this);
        winScreen = new WinScreen(this);
        loseScreen = new LoseScreen(this);

        currentlevel = new Level(16, 10); // Устанавливаем начальный уровень

        mainPanel.add(currentlevel, "GAME");
        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(winScreen, "WIN");
        mainPanel.add(loseScreen, "LOSE");

        setContentPane(mainPanel);

        cardLayout.show(mainPanel, "MENU");
    }

    private void buildWindow(){
        pack();
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateWindowSize(){
        pack();
        revalidate();
        repaint();
        setLocationRelativeTo(null);
        this.requestFocusInWindow();
    }


    //==============================Уровни==============================

    public Level level1(){
        return Level.createLevel1();
    }

    public Level level2(){
        return Level.createLevel2();
    }

    public Level level3(){
        return Level.createLevel3();
    }


    //===================Обработчик-событий-клавиатуры==================

    private void createKeyListener() {
        keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                handlingKey(e.getKeyCode());
            }

            @Override
            public void keyPressed(KeyEvent e) {
                handlingKey(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };

        this.addKeyListener(keyListener);
    }

    private void handlingKey(int key){
        if(!"GAME".equals(getCurrentCard())) return;

        switch(key){
            case KeyEvent.VK_ESCAPE -> switchToMenuView();
            case KeyEvent.VK_R -> restartCurrentLevel();
            default -> {
                Direction direction = readDirection(key);
                releaseDirection(direction);
            }
        }
    }

    private static Direction readDirection(int key) {
        Direction direction = null;
        switch (key){
            case KeyEvent.VK_UP -> direction = Direction.UP;
            case KeyEvent.VK_DOWN -> direction = Direction.DOWN;
            case KeyEvent.VK_RIGHT -> direction = Direction.RIGHT;
            case KeyEvent.VK_LEFT -> direction = Direction.LEFT;
            case KeyEvent.VK_SPACE -> direction = Direction.STAY;
        }
        return direction;
    }

    private void releaseDirection(Direction direction) {
        if(direction != null) {
            currentlevel.makeStep(direction);
            status = currentlevel.checkSuccess();

            if(status == Status.WIN){
                win();
            }
            else if(status == Status.LOSE){
                lose();
            }
        }
    }

    /**
     * Вызывается при поражении на уровне
     */
    private void lose() {
        cardLayout.show(mainPanel, "LOSE");
        updateWindowSize();
    }

    /**
     * Вызывается при победе на уровне
     */
    private void win() {
        cardLayout.show(mainPanel, "WIN");
        updateWindowSize();
    }

    private String getCurrentCard() {
        for (Component comp : mainPanel.getComponents()) {
            if (comp.isVisible()) {
                if (comp == menuPanel) return "MENU";
                if (comp == winScreen) return "WIN";
                if (comp == loseScreen) return "LOSE";
                if (comp == currentlevel) return "GAME";
            }
        }
        return "MENU";
    }


    //===============================MAIN================================

    /**
     * Переключает отображение на игровой экран
     */
    public void switchToGameView() {
        cardLayout.show(mainPanel, "GAME");
        mainPanel.remove(menuPanel);
        updateWindowSize();
    }

    /**
     * Переключает отображение на главное меню
     */
    public void switchToMenuView() {
        mainPanel.add(menuPanel, "MENU");
        cardLayout.show(mainPanel, "MENU");
        mainPanel.remove(currentlevel);
        updateWindowSize();
    }

    /**
     * Перезапускает текущий уровень
     */
    public void restartCurrentLevel() {
        Level newLevel = currentlevel.createCopy();
        loadLevel(newLevel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BabaIsYouWindow();
            }
        });
    }
}