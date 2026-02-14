package Core;

import GameObjects.*;
import Rules.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class BabaIsYouWindow extends JFrame {

    private Level _level;
    private int _levelSuccess;

    public BabaIsYouWindow(){
        createContent();
        //loadLevel(generateLevel());
        Subject sub = new Subject("BABA", new Position(1, 1));
        Subject sub2 = new Subject("BABAS", new Position(2, 1));
        TextBlock textBlock = new TextBlock(new SubjectName("BABAS"), "BABAS", new Position(2, 2), Color.RED);
        List<GameObject> gameObjects = new ArrayList<GameObject>();
        gameObjects.add(sub);
        gameObjects.add(sub2);
        gameObjects.add(textBlock);
        loadLevel(new Level(gameObjects));

        createKeyListener();
        buildWindow();
    }

    public void loadLevel(Level level){
        if(_level != null)
            remove(_level);

        _level = level;
        _level.build(this);
    }

    public static Level generateLevel(){
        Subject sub = new Subject("BABA", new Position(1, 1));
        Subject sub2 = new Subject("BABAS", new Position(2, 1));
        TextBlock textBlock = new TextBlock(new SubjectName("BABAS"), "BABAS", new Position(2, 2), Color.RED);
        List<GameObject> gameObjects = new ArrayList<GameObject>();
        gameObjects.add(sub);
        gameObjects.add(sub2);
        gameObjects.add(textBlock);

        return new Level(gameObjects);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BabaIsYouWindow();
            }
        });
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

    private void createKeyListener() {
        KeyListener listener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                handlingKey(e.getKeyChar());
            }

            @Override
            public void keyPressed(KeyEvent e) {
                handlingKey(e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };
    }

    private void handlingKey(char key){

    }
}
