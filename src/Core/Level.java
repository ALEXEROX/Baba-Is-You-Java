package Core;

import Rules.*;
import GameObjects.*;
import Rules.Features.*;
import Rules.Operators.*;

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
    private HashSet<Rule> _rules;


    //====================================Конструкторы======================================

    public Level(){
        _gameObjects = new ArrayList<>();
        createScreen();
        makeDefaultRules();
    }

    public Level(List<GameObject> gameObjects){
        this();
        _gameObjects = gameObjects;
    }

    private void createScreen() {
        setBounds(0, 0, WIDTH*CELL_SIZE, HEIGHT*CELL_SIZE);
        setPreferredSize(new Dimension(WIDTH*CELL_SIZE, HEIGHT*CELL_SIZE) );
        setBackground(BACKGOUND_COLOR);
    }


    //============================Управление-уровнем================================

    /**
     * Вызывается по каждому действию игрока
     * @param dir направление движения игрока
     */
    public void makeStep(Direction dir){
        calculateRules();
        releaseRules();
        releaseFeatures(dir);

        moveGameObjects();
        repaint();
    }

    private void moveGameObjects() {
        for(GameObject gameObject: _gameObjects){
            gameObject.move();
        }
    }

    public boolean canLetTo(Position position, Direction direction){
        List<GameObject> gameObjects = getCell(position);

        for(GameObject gameObject : gameObjects){
            if(gameObject.hasFeature(new STOP())) {
                return false;
            }
            if(gameObject.hasFeature(new PUSH())){
                if(!canLetTo(gameObject.getPosition().getNeightboor(direction), direction)){
                    return false;
                }
            }
        }

        return true;
    }


    /**
     * Выполняет правила по очереди
     */
    private void releaseRules() {
        for(Rule rule : _rules){
            rule.release(this);
        }
    }

    private void releaseFeatures(Direction direction){
        for(GameObject gameObject : _gameObjects){
            for(Feature feature : gameObject.getFeatures()){
                feature.action(gameObject, direction);
            }
        }

        for(int x = 0; x < WIDTH; x++){
            for(int y = 0; y < HEIGHT; y++){
                releaseInteractionsInCell(new Position(x, y), direction);
            }
        }
    }

    private void releaseInteractionsInCell(Position position, Direction direction){
        List<GameObject> gameObjectsInCell = getCellOnNextStep(position);

        for(GameObject gameObject : gameObjectsInCell){
            List<GameObject> otherGameObjects = new ArrayList<>(List.copyOf(gameObjectsInCell));
            otherGameObjects.remove(gameObject);

            for(Feature feature : gameObject.getFeatures()) {
                for (GameObject otherGameobject : otherGameObjects) {
                    feature.interaction(gameObject, otherGameobject, direction);
                }
            }
        }
    }

    /**
     * Находит правила, описанные на поле
     */
    public void calculateRules(){
        makeDefaultRules();

        for(GameObject gameObject : _gameObjects){
            _rules.addAll(findRules(gameObject.getPosition()));
        }

        for(GameObject gameObject : _gameObjects){
            gameObject.clearFeatures();
        }
        
        sortRules();
    }


    /**
     * Находит правила, корень которых находится в положении pos
     * @param pos Начальное положение предпологаемых правил
     * @return Уникальный список правил
     */
    public HashSet<Rule> findRules(Position pos){
        HashSet<Rule> rules = new HashSet<>();

        List<RuleText> rightToLeftPhrase = findPhrase(pos, Direction.RIGHT);
        List<RuleText> topToDownPhrase = findPhrase(pos, Direction.DOWN);

        // TODO

        return rules;
    }

    /**
     * Сортирует правила по порядку приоритета
     */
    private void sortRules() {
    }

    /**
     * Находит словосочетание из трех в ряд стоящих ячеек
     * @param pos Положение первого слова фразы
     * @param dir Направление поиска
     * @return Сочетание слов
     */
    private List<RuleText> findPhrase(Position pos, Direction dir){
        List<RuleText> phrase = new ArrayList<>();
        List<GameObject> cell = new ArrayList<>();

        Position currentPos = pos;
        RuleText ruleText;

        for(int i = 0; i < 3; i++) {
            ruleText = null;
            cell = getCell(currentPos);

            for (GameObject gameObject : cell) {
                if (gameObject.isTextBlock()){
                    ruleText = ((TextBlock) gameObject).getRuleText();
                }
            }
            phrase.add(ruleText);
            currentPos = currentPos.getNeightboor(dir);
        }

        return phrase;
    }

    /**
     * @param phrase Список из трех слов типа RuleText
     * @return Может ли список считаться правилом
     */
    private boolean IsRule(List<RuleText> phrase){
        return false;
    }

    /**
     * Проверяет, не находится ли поле в состоянии победы или поражения
     * @return  1 - победа, 0 - продолжение, -1 - поражение
     */
    public int checkSuccess(){


        return 0;
    }

    /**
     * Превращает один объект в другой
     * @param from Исходный объект
     * @param to Тип объекта, в который надо превратить исходный.
     *           При превращении Subject в TextBlock, он превращается в
     *           SubjectName с именем исходного объекта.
     */


    //===========================Управление-объектами===============================

    public void transformGameObject(GameObject from, String to){
        Position pos = from.getPosition();
        GameObject newGameObject;

        if(!Objects.equals(to, "TEXT")){
            newGameObject = new Subject(to,this, pos);
        }
        else{
            SubjectName subjectName = new SubjectName(from.getName());
            newGameObject = new TextBlock(subjectName, this, pos);
        }

        _gameObjects.remove(from);
        _gameObjects.add(newGameObject);
    }

    public void destroyGameObject(GameObject gameObject){
        _gameObjects.remove(gameObject);
        gameObject = null;
    }

    //==============================Создание-уровня=================================

    /**
     * Обнуляет текущий список правил и создает список правил по умолчанию
     */
    public void makeDefaultRules(){
        _rules = new HashSet<>();
        Rule text_is_push = new Rule(new SubjectName("TEXT"), new IS(), new PUSH());
        _rules.add(text_is_push);


        Rule baba_is_you = new Rule(new SubjectName("BABA"), new IS(), new YOU());
        _rules.add(baba_is_you);
    }


    //================================Гетеры-сетеры===================================

    /**
     * @param pos Позиция "ячейки"
     * @return Список объектов, находящихся в "ячейке"
     */
    public List<GameObject> getCell(Position pos){
        List<GameObject> gameObjects = new ArrayList<>();

        for(GameObject gameObject : _gameObjects){
            if(gameObject.getPosition() == pos){
                gameObjects.add(gameObject);
            }
        }

        return gameObjects;
    }

    public List<GameObject> getCellOnNextStep(Position pos){
        List<GameObject> gameObjects = new ArrayList<>();

        for(GameObject gameObject : _gameObjects){
            if(gameObject.getNextPosition().equal(pos)){
                gameObjects.add(gameObject);
            }
        }

        return gameObjects;
    }

    public List<GameObject> getGameObjects(){
        return _gameObjects;
    }

    public void addGameObject(GameObject gameObject){
        _gameObjects.add(gameObject);
    }


    //=================================РИСОВАНИЕ===================================

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
