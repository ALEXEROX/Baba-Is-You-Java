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

    public static final int CELL_SIZE = 75;
    public static final Color BACKGOUND_COLOR = Color.BLACK;


    //==========================Поля================================

    public final int _width;
    public final int _height;
    private List<GameObject> _gameObjects;
    private HashSet<Rule> _rules;


    //====================================Конструкторы======================================

    public Level(int width, int height){
        _width = width; _height = height;
        _gameObjects = new ArrayList<>();
        createScreen();
        makeDefaultRules();
    }


    //============================Управление-уровнем================================

    /**
     * Вызывается по каждому действию игрока
     * @param direction направление движения игрока
     */
    public void makeStep(Direction direction){
        releaseFeatures(direction);
        moveGameObjects();

        calculateRules();
        releaseRules();

        checkSuccess();
        repaint();
    }

    /**
     * Двигает все игровые объекты на поле
     */
    private void moveGameObjects() {
        for(GameObject gameObject: _gameObjects){
            gameObject.move();
        }
    }

    /**
     * Проверяет, сможет ли какой-био объект войти в тпроверяемую ячейку
     * @param position проверяемая ячейка
     * @param direction направление движения объектов на текущем шаге
     */
    public boolean canLetTo(Position position, Direction direction){
        List<GameObject> gameObjects = getCellOnNextStep(position);

        for(GameObject gameObject : gameObjects){
            if(gameObject.hasFeature(new STOP())) {
                return false;
            }
            if(gameObject.hasFeature(new PUSH())){
                if(!canLetTo(gameObject.getNextPosition().getNeightboor(direction), direction)){
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Выполняет правила
     */
    private void releaseRules() {
        for(Rule rule : _rules){
            rule.release(this);
        }
    }

    /**
     * Выполняет все действия в соответствии с правилами
     * @param direction навправление движения объектов
     */
    private void releaseFeatures(Direction direction){
        sortGameObjects(direction);
        releaseActions(direction);
        releaseInteractions(direction);
    }

    /**
     * Сортирует объекты по направлению движения.
     * Нужна, чтобы обработка выполнялась правильно.
     * @param direction навправление движения объектов
     */
    private void sortGameObjects(Direction direction) {
        switch (direction) {
            case RIGHT:
                _gameObjects.sort((g1, g2) -> Integer.compare(g1.getPosition().getX(), g2.getPosition().getX()));
                break;
            case LEFT:
                _gameObjects.sort((g1, g2) -> Integer.compare(g2.getPosition().getX(), g1.getPosition().getX()));
                break;
            case DOWN:
                _gameObjects.sort((g1, g2) -> Integer.compare(g1.getPosition().getY(), g2.getPosition().getY()));
                break;
            case UP:
                _gameObjects.sort((g1, g2) -> Integer.compare(g2.getPosition().getY(), g1.getPosition().getY()));
                break;
        }
    }

    /**
     * Выполняет действия правил, взаимодействующих с одним объектом в клетке.
     * Например YOU - двигает объект со свойством YOU
     * @param direction навправление движения объектов
     */
    private void releaseActions(Direction direction) {
        for(GameObject gameObject : _gameObjects){
            for(Feature feature : gameObject.getFeatures()){
                feature.action(gameObject, direction);
            }
        }
    }

    /**
     * Выполняет действия правил, взаимодействующих с двумя объектами, находящимися в одной ячекйке.
     * Например PUSH - пытается вытолкнуть объект, в который пытается двигаться другой объект
     * @param direction навправление движения объектов
     */
    private void releaseInteractions(Direction direction) {
        for(GameObject gameObject : _gameObjects) {
            releaseInteractionsInCell(gameObject.getNextPosition(), direction);
        }
    }

    /**
     * Выполняет действия правил, взаимодействующих с двумя объектами в определенной ячейке.
     * @param position проверяемая позиция, в которую предполагают оказаться объекты на следующем шаге
     * @param direction навправление движения объектов
     */
    public void releaseInteractionsInCell(Position position, Direction direction){
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
            gameObject.clearFeatures();
            if(gameObject instanceof TextBlock textBlock){
                textBlock.deactivate();
            }
        }

        for(GameObject gameObject : _gameObjects){
            _rules.addAll(findRules(gameObject.getNextPosition()));
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

        List<RuleText> leftToRightPhrase = findPhrase(pos, Direction.RIGHT);
        List<RuleText> topToBottomPhrase = findPhrase(pos, Direction.DOWN);

        if(isRule(leftToRightPhrase)){
            rules.add(ruleFromPhrase(leftToRightPhrase));
            highlightRule(pos, Direction.RIGHT);
        }
        if(isRule(topToBottomPhrase)){
            rules.add(ruleFromPhrase(topToBottomPhrase));
            highlightRule(pos, Direction.DOWN);
        }

        return rules;
    }

    private void highlightRule(Position position, Direction direction) {
        Position currentPosition = position;
        
        for(int i = 0; i < 3; i++) {
            List<GameObject> cell = getCellOnNextStep(currentPosition);
            for(GameObject gameObject : cell){
                if(gameObject instanceof TextBlock textBlock){
                    textBlock.activate();
                }
            }
            currentPosition = currentPosition.getNeightboor(direction);
        }
    }

    private Rule ruleFromPhrase(List<RuleText> phrase){
        Operand firstWord = (Operand) phrase.getFirst();
        Operator secondWord = (Operator) phrase.get(1);
        Operand thirdWord = (Operand) phrase.get(2);

        return new Rule(firstWord, secondWord, thirdWord);
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
        List<GameObject> cell;

        Position currentPos = pos;
        RuleText ruleText;

        for(int i = 0; i < 3; i++) {
            ruleText = null;
            cell = getCellOnNextStep(currentPos);

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
    private boolean isRule(List<RuleText> phrase){
        if(phrase.getFirst() instanceof Operand firstWord &&
                phrase.get(1) instanceof Operator secondWord &&
                phrase.get(2) instanceof Operand thirdWord){
            return secondWord.canInteract(firstWord, thirdWord);
        }

        return false;
    }

    /**
     * Проверяет, не находится ли поле в состоянии победы или поражения
     * @return  1 - победа, 0 - продолжение, -1 - поражение
     */
    public int checkSuccess(){
        // Проверить наличие объектов YOU
        int youGameObjects = 0;
        for(GameObject gameObject : _gameObjects){
            if(gameObject.hasFeature(new YOU())){
                youGameObjects++;
            }
        }
        if(youGameObjects == 0){
            return -1;
        }

        //Проверка победы (YOU и WIN в одной ячейке)
        for(GameObject gameObject : _gameObjects){
            List<GameObject> cell = getCell(gameObject.getPosition());
            boolean hasYOU = false, hasWIN = false;
            for(GameObject objectInCell : cell){
                if(objectInCell.hasFeature(new YOU())){
                    hasYOU = true;
                }
                if(objectInCell.hasFeature(new WIN())){
                    hasWIN = true;
                }
            }
            if(hasYOU && hasWIN){
                return 1;
            }
        }

        return 0;
    }


    //===========================Управление-объектами===============================

    /**
     * Превращает один gameObject в другой
     * @param from какой превращается
     * @param to во что превращается (чтобы превратиться в текст
     *           с названием исходного обхекта, можно ввести "TEXT")
     */
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

        from.destroy();
        
        calculateRules();
        releaseRules();
    }


    /**
     * Уничтожает объект с поля
     */
    public void destroyGameObject(GameObject gameObject){
        _gameObjects.remove(gameObject);
        gameObject = null;
    }

    //==============================Создание-уровня=================================

    /**
     * Обнуляет текущий список правил и добавляет список правил по умолчанию
     */
    public void makeDefaultRules(){
        _rules = new HashSet<>();
        Rule text_is_push = new Rule(new SubjectName("TEXT"), new IS(), new PUSH());
        _rules.add(text_is_push);
    }

    /**
     * Создает окно, закрашенное BACKGROUND_COLOR определенного размера
     */
    private void createScreen() {
        setBounds(0, 0, _width *CELL_SIZE, _height *CELL_SIZE);
        setPreferredSize(new Dimension(_width *CELL_SIZE, _height *CELL_SIZE) );
        setBackground(BACKGOUND_COLOR);
    }


    //================================Гетеры-сетеры===================================

    /**
     * @param pos Позиция "ячейки"
     * @return Список объектов, находящихся в "ячейке"
     */
    public List<GameObject> getCell(Position pos){
        List<GameObject> gameObjects = new ArrayList<>();

        for(GameObject gameObject : _gameObjects){
            if(gameObject.getPosition().equal(pos)){
                gameObjects.add(gameObject);
            }
        }

        return gameObjects;
    }

    /**
     * Возвращает все GameObject, которые собираются переместиться в выбранную ячейку
     */
    public List<GameObject> getCellOnNextStep(Position pos){
        List<GameObject> gameObjects = new ArrayList<>();

        for(GameObject gameObject : _gameObjects){
            if(gameObject.getNextPosition().equal(pos)){
                gameObjects.add(gameObject);
            }
        }

        return gameObjects;
    }

    /**
     * @return все GameObject на уровне
     */
    public List<GameObject> getGameObjects(){
        return _gameObjects;
    }

    /**
     * Добавить GameObject в уровень
     */
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
        g.fillRect(0, 0, _width *CELL_SIZE, _height *CELL_SIZE);
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
        return new Dimension(_width * CELL_SIZE,
                _height * CELL_SIZE);
    }
}
