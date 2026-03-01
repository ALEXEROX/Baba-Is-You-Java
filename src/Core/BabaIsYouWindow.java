package Core;

import GameObjects.*;
import Rules.*;
import Rules.Features.*;
import Rules.Operators.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class BabaIsYouWindow extends JFrame {


    //============================Поля=================================

    private Level _level;
    private int _levelSuccess;
    private KeyListener _keyListener;
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
        if(_level != null) {
            mainPanel.remove(_level);
        }

        _level = level;
        mainPanel.add(_level, "GAME");
        _level.makeStep(Direction.STAY);
    }

    private void initializeComponents(){
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        menuPanel = new MenuPanel(this);
        winScreen = new WinScreen(this);
        loseScreen = new LoseScreen(this);

        _level = new Level(16, 10); // Устанавливаем начальный уровень

        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(_level, "GAME");
        mainPanel.add(winScreen, "WIN");
        mainPanel.add(loseScreen, "LOSE");

        setContentPane(mainPanel);
    }

    private void buildWindow(){
        pack();
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Отображаем главное меню при запуске
        cardLayout.show(mainPanel, "MENU");
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
        _keyListener = new KeyListener() {
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

        this.addKeyListener(_keyListener);
    }

    private void handlingKey(int key){
        Direction direction = null;

        switch (key){
            case KeyEvent.VK_UP -> direction = Direction.UP;
            case KeyEvent.VK_DOWN -> direction = Direction.DOWN;
            case KeyEvent.VK_RIGHT -> direction = Direction.RIGHT;
            case KeyEvent.VK_LEFT -> direction = Direction.LEFT;
            case KeyEvent.VK_SPACE -> direction = Direction.STAY;
        }

        if(direction != null) {
            _level.makeStep(direction);
            _levelSuccess = _level.checkSuccess();

            if(_levelSuccess == 1){
                win();
            }
            if(_levelSuccess == -1){
                lose();
            }

            System.out.println(_levelSuccess);
        }
    }

    private void lose() {
        cardLayout.show(mainPanel, "LOSE");
    }

    private void win() {
        cardLayout.show(mainPanel, "WIN");
    }


    //===============================MAIN================================

    /**
     * Переключает отображение на игровой экран
     */
    public void switchToGameView() {
        cardLayout.show(mainPanel, "GAME");
        this.requestFocusInWindow();
    }

    /**
     * Переключает отображение на главное меню
     */
    public void switchToMenuView() {
        cardLayout.show(mainPanel, "MENU");
    }

    /**
     * Перезапускает текущий уровень
     */
    public void restartCurrentLevel() {
        // Просто создаем копию текущего уровня
        Level newLevel = _level.createCopy();

        mainPanel.remove(_level);
        loadLevel(newLevel);
        mainPanel.add(_level, "GAME");
        cardLayout.show(mainPanel, "GAME");
        this.requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BabaIsYouWindow();
            }
        });
    }
}