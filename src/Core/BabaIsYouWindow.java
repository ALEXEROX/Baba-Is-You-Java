package Core;

import GameObjects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BabaIsYouWindow extends JFrame {


    //============================Поля=================================

    // Логика
    private Level level;
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
        if(this.level != null) {
            mainPanel.remove(this.level);
        }

        this.level = level;
        mainPanel.add(this.level, "GAME");
        cardLayout.show(mainPanel, "GAME");

        updateWindowSize();
        this.level.makeStep(Direction.STAY);
    }

    private void initializeComponents(){
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        menuPanel = new MenuPanel(this);
        winScreen = new WinScreen(this);
        loseScreen = new LoseScreen(this);

        level = new Level(16, 10); // Устанавливаем начальный уровень

        mainPanel.add(level, "GAME");
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
            level.makeStep(direction);
            status = level.checkSuccess();

            if(status == Status.WIN){
                win();
            }
            else if(status == Status.LOSE){
                lose();
            }
        }
    }

    private void lose() {
        cardLayout.show(mainPanel, "LOSE");
        updateWindowSize();
    }

    private void win() {
        cardLayout.show(mainPanel, "WIN");
        updateWindowSize();
    }


    //===============================MAIN================================

    /**
     * Переключает отображение на игровой экран
     */
    public void switchToGameView() {
        cardLayout.show(mainPanel, "GAME");
        updateWindowSize();
    }

    /**
     * Переключает отображение на главное меню
     */
    public void switchToMenuView() {
        cardLayout.show(mainPanel, "MENU");
        updateWindowSize();
    }

    /**
     * Перезапускает текущий уровень
     */
    public void restartCurrentLevel() {
        Level newLevel = level.createCopy();
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