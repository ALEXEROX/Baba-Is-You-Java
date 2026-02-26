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
        Level level = new Level(16, 10);

        // BABA IS YOU
        TextBlock babaName = new TextBlock(new SubjectName("BABA"), level, new Position(0, 0));
        TextBlock is1 = new TextBlock(new IS(), level, new Position(1, 0));
        TextBlock you = new TextBlock(new YOU(), level, new Position(2, 0));

        // WALL IS STOP
        TextBlock wallName = new TextBlock(new SubjectName("WALL"), level, new Position(13, 0));
        TextBlock is2 = new TextBlock(new IS(), level, new Position(14, 0));
        TextBlock stop = new TextBlock(new STOP(), level, new Position(15, 0));

        // ROCK IS PUSH
        TextBlock rockName = new TextBlock(new SubjectName("ROCK"), level, new Position(0, 9));
        TextBlock is3 = new TextBlock(new IS(), level, new Position(1, 9));
        TextBlock push = new TextBlock(new PUSH(), level, new Position(2, 9));

        // FLAG IS WIN
        TextBlock flagName = new TextBlock(new SubjectName("FLAG"), level, new Position(13, 9));
        TextBlock is4 = new TextBlock(new IS(), level, new Position(14, 9));
        TextBlock win = new TextBlock(new WIN(), level, new Position(15, 9));

        // Стены
        for(int i = 0; i < 16; i++){
            new Subject("WALL", level, new Position(i, 1));
            new Subject("WALL", level, new Position(i, 8));
        }

        // Камни
        for(int i = 0; i < 6; i++){
            new Subject("ROCK", level, new Position(8, i+2));
        }

        // BABA
        new Subject("BABA", level, new Position(3, 5));

        // FLAG
        new Subject("FLAG", level, new Position(13, 5));

        return level;
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
        // Сохраняем текущий уровень для восстановления
        Level currentLevel = _level;

        // Удаляем старый уровень и создаем новый
        mainPanel.remove(_level);
        _level = new Level(currentLevel._width, currentLevel._height);

        // Пересоздаем компоненты уровня
        if (currentLevel == level1()) {
            _level = level1();
        } else if (currentLevel == level2()) {
            _level = level2();
        } else if (currentLevel == level3()) {
            _level = level3();
        } else {
            _level = level1(); // по умолчанию
        }

        // Добавляем новый уровень в панель
        mainPanel.add(_level, "GAME");
        cardLayout.show(mainPanel, "GAME");
        this.requestFocusInWindow();
    }

    /**
     * Возвращает второй уровень
     */
    public Level level2() {
        Level level = new Level(10, 8);

        // BABA IS YOU
        TextBlock babaName = new TextBlock(new SubjectName("BABA"), level, new Position(0, 0));
        TextBlock is1 = new TextBlock(new IS(), level, new Position(1, 0));
        TextBlock you = new TextBlock(new YOU(), level, new Position(2, 0));

        // WALL IS STOP
        TextBlock wallName = new TextBlock(new SubjectName("WALL"), level, new Position(7, 0));
        TextBlock is2 = new TextBlock(new IS(), level, new Position(8, 0));
        TextBlock stop = new TextBlock(new STOP(), level, new Position(9, 0));

        // ROCK IS PUSH
        TextBlock rockName = new TextBlock(new SubjectName("ROCK"), level, new Position(0, 7));
        TextBlock is3 = new TextBlock(new IS(), level, new Position(1, 7));
        TextBlock push = new TextBlock(new PUSH(), level, new Position(2, 7));

        // FLAG IS WIN
        TextBlock flagName = new TextBlock(new SubjectName("FLAG"), level, new Position(7, 7));
        TextBlock is4 = new TextBlock(new IS(), level, new Position(8, 7));
        TextBlock win = new TextBlock(new WIN(), level, new Position(9, 7));

        // Стены по краям
        for(int i = 0; i < 10; i++){
            new Subject("WALL", level, new Position(i, 1));
            new Subject("WALL", level, new Position(i, 6));
        }

        // BABA
        new Subject("BABA", level, new Position(1, 3));

        // ROCK
        new Subject("ROCK", level, new Position(4, 3));

        // FLAG
        new Subject("FLAG", level, new Position(8, 3));

        return level;
    }

    /**
     * Возвращает третий уровень
     */
    public Level level3() {
        Level level = new Level(12, 10);

        // BABA IS YOU
        TextBlock babaName = new TextBlock(new SubjectName("BABA"), level, new Position(0, 0));
        TextBlock is1 = new TextBlock(new IS(), level, new Position(1, 0));
        TextBlock you = new TextBlock(new YOU(), level, new Position(2, 0));

        // WALL IS STOP
        TextBlock wallName = new TextBlock(new SubjectName("WALL"), level, new Position(9, 0));
        TextBlock is2 = new TextBlock(new IS(), level, new Position(10, 0));
        TextBlock stop = new TextBlock(new STOP(), level, new Position(11, 0));

        // ROCK IS PUSH
        TextBlock rockName = new TextBlock(new SubjectName("ROCK"), level, new Position(0, 9));
        TextBlock is3 = new TextBlock(new IS(), level, new Position(1, 9));
        TextBlock push = new TextBlock(new PUSH(), level, new Position(2, 9));

        // FLAG IS WIN
        TextBlock flagName = new TextBlock(new SubjectName("FLAG"), level, new Position(9, 9));
        TextBlock is4 = new TextBlock(new IS(), level, new Position(10, 9));
        TextBlock win = new TextBlock(new WIN(), level, new Position(11, 9));

        // Внутренние стены
        for(int i = 3; i < 9; i++){
            new Subject("WALL", level, new Position(i, 4));
        }

        // Камни
        new Subject("ROCK", level, new Position(4, 3));
        new Subject("ROCK", level, new Position(5, 3));
        new Subject("ROCK", level, new Position(6, 3));

        // BABA
        new Subject("BABA", level, new Position(1, 5));

        // FLAG
        new Subject("FLAG", level, new Position(7, 5));

        return level;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BabaIsYouWindow();
            }
        });
    }
}