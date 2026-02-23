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

        _level = level1();

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


    //==============================Уровни==============================

    private Level level1(){
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
