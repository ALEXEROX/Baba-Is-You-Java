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


    //==========================Конструктор============================

    public BabaIsYouWindow(){
        createContent();

        _level = new Level(16, 10);

        //BABA IS YOU
        TextBlock babaName = new TextBlock(new SubjectName("BABA"), _level, new Position(4, 4));
        TextBlock is = new TextBlock(new IS(), _level, new Position(5, 4));
        TextBlock you = new TextBlock(new YOU(), _level, new Position(6, 4));

        //WALL IS STOP
        TextBlock wallName = new TextBlock(new SubjectName("WALL"), _level, new Position(1, 3));
        TextBlock is2 = new TextBlock(new IS(), _level, new Position(2, 3));
        TextBlock stop = new TextBlock(new STOP(), _level, new Position(3, 3));

        Subject baba = new Subject("BABA", _level, new Position(6, 5));
        Subject wall = new Subject("WALL", _level, new Position(7, 4));

        loadLevel(_level);
        createKeyListener();

        buildWindow();
    }


    //=======================Управление-окном===========================

    /**
     * Загружает уровен для его отображения в окне
     */
    public void loadLevel(Level level){
        if(_level != null)
            remove(_level);

        _level = level;
        this.add(_level);
        _level.makeStep(Direction.STAY);
    }

    private void createContent(){
        JPanel content = (JPanel)getContentPane();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS) );
    }

    private void buildWindow(){
        pack();
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
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
        }
    }

    private void lose() {

    }

    private void win() {
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
