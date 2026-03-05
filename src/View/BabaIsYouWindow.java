package View;

import Model.GameObjects.*;
import Model.Level;
import Model.LevelBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BabaIsYouWindow extends JFrame {

    //============================Поля=================================

    // Логика
    private Level currentlevel;
    private KeyListener keyListener;
    private Condition condition;

    // Визуал
    private JLayeredPane layeredPane;
    private LevelPanel levelPanel;
    private WinOverlay winOverlay;
    private LoseOverlay loseOverlay;
    private MenuPanel menuPanel;

    //==========================Конструктор============================

    public BabaIsYouWindow(){
        initializeComponents();
        createKeyListener();
    }


    //=======================Управление-окном===========================

    /**
     * Создает физуальные компоненты
     */
    private void initializeComponents(){
        layeredPane = new JLayeredPane();

        // Начальный размер (пока нет уровня)
        Dimension initialSize = new Dimension(1200, 750);
        layeredPane.setPreferredSize(initialSize);

        // Создаем начальный уровень и панель к нему
        currentlevel = new Level(16, 10);
        levelPanel = new LevelPanel(currentlevel);

        // Создаем оверлеи (полупрозрачные)
        winOverlay = new WinOverlay(this);
        winOverlay.setBounds(0, 0, initialSize.width, initialSize.height);
        winOverlay.setVisible(false);

        loseOverlay = new LoseOverlay(this);
        loseOverlay.setBounds(0, 0, initialSize.width, initialSize.height);
        loseOverlay.setVisible(false);

        // Меню
        menuPanel = new MenuPanel(this);
        menuPanel.setBounds(0, 0, initialSize.width, initialSize.height);

        // Добавляем все в layeredPane с разными уровнями
        layeredPane.add(levelPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(menuPanel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(winOverlay, JLayeredPane.MODAL_LAYER);
        layeredPane.add(loseOverlay, JLayeredPane.MODAL_LAYER);

        setContentPane(layeredPane);

        // Показываем меню
        menuPanel.setVisible(true);
        levelPanel.setVisible(false);

        pack();
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Загружает уровен для его отображения в окне
     */
    public void loadLevel(Level level){
        layeredPane.remove(levelPanel);

        currentlevel = level;
        levelPanel = new LevelPanel(currentlevel);
        layeredPane.add(levelPanel, JLayeredPane.DEFAULT_LAYER);

        // Обновляем размеры всех компонентов под новый уровень
        updateAllSizes();

        // Скрываем меню и оверлеи, показываем уровень
        menuPanel.setVisible(false);
        winOverlay.setVisible(false);
        loseOverlay.setVisible(false);
        levelPanel.setVisible(true);

        currentlevel.makeStep(Direction.STAY);
        this.requestFocusInWindow();
    }

    /**
     * Обновляет размеры всех компонентов под текущий уровень
     */
    private void updateAllSizes() {
        if (levelPanel.getCurrentLevel() != null) {
            Dimension levelSize = levelPanel.getPreferredSize();

            // Обновляем размер основного окна
            setSize(levelSize.width, levelSize.height);

            // Обновляем размеры layeredPane
            layeredPane.setPreferredSize(levelSize);
            layeredPane.setSize(levelSize);

            // Обновляем размеры всех компонентов
            levelPanel.setBounds(0, 0, levelSize.width, levelSize.height);
            menuPanel.setBounds(0, 0, levelSize.width, levelSize.height);
            winOverlay.setBounds(0, 0, levelSize.width, levelSize.height);
            loseOverlay.setBounds(0, 0, levelSize.width, levelSize.height);

            // Обновляем предпочтительные размеры меню (если оно статичное)
            menuPanel.setPreferredSize(levelSize);

            pack();
            revalidate();
            repaint();
        }
    }


    //==============================Уровни==============================

    public Level level1(){
        return LevelBuilder.createLevel1();
    }

    public Level level2(){
        return LevelBuilder.createLevel2();
    }

    public Level level3(){
        return LevelBuilder.createLevel3();
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

    /**
     * Обработка нажатой клавиши
     * @param key клавиша
     */
    private void handlingKey(int key){
        if(!isLevelVisible()) return; // Работает только находясь на уровне

        switch(key){
            case KeyEvent.VK_ESCAPE -> switchToMenuView();
            case KeyEvent.VK_R -> restartCurrentLevel();
            default -> {
                Direction direction = readDirection(key);
                releaseDirection(direction);
            }
        }

        if(condition == Condition.WIN){
            win();
        }
        else if(condition == Condition.LOSE){
            lose();
        }
    }

    /**
     * Проверяет, виден ли уровень (не меню и не оверлеи)
     */
    private boolean isLevelVisible() {
        return levelPanel.isVisible() &&
                !winOverlay.isVisible() &&
                !loseOverlay.isVisible();
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
            condition = currentlevel.checkSuccess();
        }
    }

    /**
     * Вызывается при поражении на уровне
     */
    private void lose() {
        winOverlay.setVisible(false);
        loseOverlay.setVisible(true);
        levelPanel.setVisible(true);
        // Не меняем размер окна при поражении
        this.requestFocusInWindow();
    }

    /**
     * Вызывается при победе на уровне
     */
    private void win() {
        loseOverlay.setVisible(false);
        winOverlay.setVisible(true);
        levelPanel.setVisible(true);
        // Не меняем размер окна при победе
        this.requestFocusInWindow();
    }

    /**
     * Переключает отображение на игровой экран
     */
    public void switchToGameView() {
        menuPanel.setVisible(false);
        winOverlay.setVisible(false);
        loseOverlay.setVisible(false);
        levelPanel.setVisible(true);
        // Размер уже должен быть правильным после loadLevel
        this.requestFocusInWindow();
    }

    /**
     * Переключает отображение на главное меню
     */
    public void switchToMenuView() {
        menuPanel.setVisible(true);
        winOverlay.setVisible(false);
        loseOverlay.setVisible(false);
        levelPanel.setVisible(false);

        // Возвращаем размер для меню
        Dimension menuSize = menuPanel.getPreferredSize();
        layeredPane.setPreferredSize(menuSize);
        layeredPane.setSize(menuSize);
        menuPanel.setBounds(0, 0, menuSize.width, menuSize.height);

        pack();
        setLocationRelativeTo(null);
        this.requestFocusInWindow();
    }

    /**
     * Перезапускает текущий уровень
     */
    public void restartCurrentLevel() {
        loadLevel(currentlevel.createCopy());
    }

    //===============================MAIN================================

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BabaIsYouWindow();
            }
        });
    }
}