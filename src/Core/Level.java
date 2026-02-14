package Core;

import Rules.*;
import GameObjects.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Level extends JPanel {

    //========================Константы=============================

    public static final int WIDTH = 16;
    public static final int HEIGHT = 10;
    public static final int CELL_SIZE = 75;
    public static final Color BACKGOUND_COLOR = Color.BLACK;


    //==========================Поля================================

    private List<GameObject> _gameObjects;
    private Map<Position, List<GameObject>> _gameObjectsMap;
    private HashSet<Rule> _rules;
//    private Core.BabaIsYouWindow _window;


    //===============================Конструкторы=========================================

    public Level(){
        _gameObjects = new ArrayList<>();
        _gameObjectsMap = new HashMap<Position, List<GameObject>>();
        makeDefaultRules();

        setBounds(0, 0, WIDTH*CELL_SIZE, HEIGHT*CELL_SIZE);
        setPreferredSize(new Dimension(WIDTH*CELL_SIZE, HEIGHT*CELL_SIZE) );
        setBackground(BACKGOUND_COLOR);
    }

    public Level(List<GameObject> gameObjects){
        this();
        _gameObjects = gameObjects;

        for(GameObject gameObject : _gameObjects){
            putGameObjectIntoMap(gameObject);
        }
    }


    //============================Управление-уровнем================================

    public void makeMove(Direction dir){

    }

    public void calculateRules(){
        makeDefaultRules();

        for(Map.Entry<Position, List<GameObject>> map : _gameObjectsMap.entrySet()){
            _rules.addAll(findRules(map.getKey()));
        }
    }

    public HashSet<Rule> findRules(Position pos){
        HashSet<Rule> rules = new HashSet<>();

        List<String> rightToLeftPhrase = findPhrase(pos, Direction.RIGHT);
        List<String> topToDownPhrase = findPhrase(pos, Direction.DOWN);

        return rules;
    }

    private List<String> findPhrase(Position pos, Direction dir){
        List<String> phrase = new ArrayList<>();

        Position currentPos = pos;
        String word;

        for(int i = 0; i < 3; i++) {
            word = "";
            for (GameObject gameObject : _gameObjectsMap.get(currentPos)) {
                if (gameObject.isText()){
                    word = ((TextBlock) gameObject).getText();
                }
            }
            phrase.add(word);
            currentPos = currentPos.getNeightboor(dir);
        }

        return phrase;
    }

    private boolean IsRule(List<String> phrase){
        return false;
    }

    public int checkSuccess(){


        return 0;
    }

    private void putGameObjectIntoMap(GameObject obj){
        List<GameObject> list = new ArrayList<>();
        Position pos = obj.getPosition();

        if(_gameObjectsMap.get(pos) != null) {
            list = _gameObjectsMap.get(pos);
        }

        list.add(obj);
        _gameObjectsMap.put(pos, list);
    }

    //==============================Создание-уровня=================================

    public static Level generateLevel(){
        Subject sub = new Subject("BABA", new Position(1, 1));
        Subject sub2 = new Subject("BABAS", new Position(2, 1));
        TextBlock textBlock = new TextBlock(TextType.SUBJECTNAME, "BABAS", new Position(2, 2), Color.RED);
        List<GameObject> gameObjects = new ArrayList<GameObject>();
        gameObjects.add(sub);
        gameObjects.add(sub2);
        gameObjects.add(textBlock);

        return new Level(gameObjects);
    }

    public void makeDefaultRules(){
        _rules = new HashSet<>();
        Rule text_is_push = new Rule("TEXT", "IS", "PUSH");
        _rules.add(text_is_push);
    }

    public void build(BabaIsYouWindow window){
//        this._window = window;
//        this._window.add(this);
        window.add(this);
    }


    //===========================РИСОВАНИЕ=============================

    @Override
    public void paint(Graphics g){
        super.paint(g);

        drawField(g);

        for (GameObject gameObject : _gameObjects) {
            drawSubject(gameObject, g);
        }
    }

    private void drawField(Graphics g){
        g.setColor(BACKGOUND_COLOR);
        g.fillRect(0, 0, WIDTH*CELL_SIZE, HEIGHT*CELL_SIZE);
    }

    private void drawSubject(GameObject gameObject, Graphics g) {
        TexturePaint texture = new TexturePaint(gameObject.getImage(), new Rectangle(CELL_SIZE, CELL_SIZE));
        Position pos = gameObject.getPosition();
        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(texture);
        g2d.fillRect(pos.getX()*CELL_SIZE, pos.getY()*CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH * CELL_SIZE,
                HEIGHT * CELL_SIZE);
    }
}
