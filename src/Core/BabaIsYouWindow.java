package Core;

import GameObjects.*;
import Rules.*;
import Rules.Features.*;
import Rules.Operators.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

        //BABA IS YOU
        TextBlock babaName = new TextBlock(new SubjectName("BABA"), new Position(0, 0));
        TextBlock is = new TextBlock(new IS(), new Position(1, 0));
        TextBlock you = new TextBlock(new YOU(), new Position(2, 0));

        Subject baba = new Subject("BABA", new Position(2, 2));

        List<GameObject> gameObjects = new ArrayList<>();
        gameObjects.add(babaName);
        gameObjects.add(is);
        gameObjects.add(you);
        gameObjects.add(baba);

        loadLevel(new Level(gameObjects));
        createKeyListener();

        buildWindow();
    }


    //=======================Управление-окном===========================

    public void loadLevel(Level level){
        if(_level != null)
            remove(_level);

        _level = level;
        this.add(_level);
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
        }

        System.out.println(direction);
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
