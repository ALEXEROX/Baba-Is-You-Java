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
//    private Core.BabaIsYouWindow _window;


    //===============================Конструкторы=========================================

    public Level(){
        _gameObjects = new ArrayList<>();
        makeDefaultRules();

        setBounds(0, 0, WIDTH*CELL_SIZE, HEIGHT*CELL_SIZE);
        setPreferredSize(new Dimension(WIDTH*CELL_SIZE, HEIGHT*CELL_SIZE) );
        setBackground(BACKGOUND_COLOR);
    }

    public Level(List<GameObject> gameObjects){
        this();
        _gameObjects = gameObjects;
    }


    //==============================Гетеры-сетеры===================================

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


    //============================Управление-уровнем================================


    /**
     * Вызывается по каждому действию игрока
     * @param dir направление движения игрока
     */
    public void makeStep(Direction dir){
        calculateRules();
    }

    /**
     * Находит правила, описанные на поле
     */
    public void calculateRules(){
        makeDefaultRules();

        for(GameObject gameObject : _gameObjects){
            _rules.addAll(findRules(gameObject.getPosition()));
        }
        
        sortRules();
    }

    /**
     * Сортирует правила по порядку приоритета
     */
    private void sortRules() {
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



        return rules;
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
                if (gameObject.isText()){
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
    public void transformGameObject(Subject from, String to){
        Position pos = from.getPosition();
        GameObject newGameObject;

        if(!Objects.equals(to, "TEXT")){
            newGameObject = new Subject(to, pos);
        }
        else{
            SubjectName subjectName = new SubjectName(from.getText());
            newGameObject = new TextBlock(subjectName, from.getText(), pos);
        }

        _gameObjects.remove(from);
        _gameObjects.add(newGameObject);
    }

    //==============================Создание-уровня=================================


    /**
     * Обнуляет текущий список правил и создает список правил по умолчанию
     */
    public void makeDefaultRules(){
        _rules = new HashSet<>();
        Rule text_is_push = new Rule(new SubjectName("TEXT"), new IS(), new PUSH());
        _rules.add(text_is_push);
    }

    /**
     * Добавляет уровень в игровое окно
     * @param window окно добавления
     */
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
