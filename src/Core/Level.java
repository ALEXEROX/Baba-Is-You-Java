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

    public final int width;
    public final int height;
    private List<GameObject> gameObjects;
    private HashSet<Rule> rules;
    private String id;


    //====================================Конструкторы======================================

    public Level(int width, int height){
        this.width = width; this.height = height;
        gameObjects = new ArrayList<>();
        createScreen();
        buildBorder();
        makeDefaultRules();
    }

    public Level(int width, int height, String id){
        this(width, height);
        this.id = id;
    }

    public Level createCopy() {
        return switch (id) {
            case "LEVEL_1" -> createLevel1();
            case "LEVEL_2" -> createLevel2();
            case "LEVEL_3" -> createLevel3();
            default -> createLevel1();
        };
    }

    private void buildBorder(){
        for(int x = 0; x < width; x++){
            new Subject("INVISIBLE_WALL", this, new Position(x, -1));
            new Subject("INVISIBLE_WALL", this, new Position(x, height));
        }
        for(int y = 0; y < height; y++){
            new Subject("INVISIBLE_WALL", this, new Position(-1, y));
            new Subject("INVISIBLE_WALL", this, new Position(width, y));
        }
    }

    public static Level createLevel1() {
        Level level = new Level(16, 10, "LEVEL_1");

        // BABA IS YOU
        new TextBlock(new SubjectName("BABA"), level, new Position(0, 0));
        new TextBlock(new IS(), level, new Position(1, 0));
        new TextBlock(new YOU(), level, new Position(2, 0));

        // WALL IS STOP
        new TextBlock(new SubjectName("WALL"), level, new Position(13, 0));
        new TextBlock(new IS(), level, new Position(14, 0));
        new TextBlock(new STOP(), level, new Position(15, 0));

        // ROCK IS PUSH
        new TextBlock(new SubjectName("ROCK"), level, new Position(0, 9));
        new TextBlock(new IS(), level, new Position(1, 9));
        new TextBlock(new PUSH(), level, new Position(2, 9));

        // FLAG IS WIN
        new TextBlock(new SubjectName("FLAG"), level, new Position(13, 9));
        new TextBlock(new IS(), level, new Position(14, 9));
        new TextBlock(new WIN(), level, new Position(15, 9));

        // Стены
        for(int i = 0; i < 16; i++){
            new Subject("WALL", level, new Position(i, 1));
            new Subject("WALL", level, new Position(i, 8));
        }

        // Камни
        for(int i = 2; i < 8; i++){
            new Subject("ROCK", level, new Position(8, i));
        }

        // BABA
        new Subject("BABA", level, new Position(3, 5));

        // FLAG
        new Subject("FLAG", level, new Position(13, 5));

        return level;
    }
    public static Level createLevel2() {
        Level level = new Level(10, 8, "LEVEL_2");

        // BABA IS YOU
        new TextBlock(new SubjectName("BABA"), level, new Position(0, 0));
        new TextBlock(new IS(), level, new Position(1, 0));
        new TextBlock(new YOU(), level, new Position(2, 0));

        // WALL IS STOP
        new TextBlock(new SubjectName("WALL"), level, new Position(7, 0));
        new TextBlock(new IS(), level, new Position(8, 0));
        new TextBlock(new STOP(), level, new Position(9, 0));

        // ROCK IS DEFEAT
        new TextBlock(new SubjectName("ROCK"), level, new Position(0, 7));
        new TextBlock(new IS(), level, new Position(1, 7));
        new TextBlock(new DEFEAT(), level, new Position(2, 7));

        // FLAG IS WIN
        new TextBlock(new SubjectName("FLAG"), level, new Position(7, 7));
        new TextBlock(new IS(), level, new Position(8, 7));
        new TextBlock(new WIN(), level, new Position(9, 7));

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
    public static Level createLevel3() {
        Level level = new Level(12, 10, "LEVEL_3");

        // BABA IS YOU
        new TextBlock(new SubjectName("BABA"), level, new Position(0, 0));
        new TextBlock(new IS(), level, new Position(1, 0));
        new TextBlock(new YOU(), level, new Position(2, 0));

        // WALL IS STOP
        new TextBlock(new SubjectName("WALL"), level, new Position(9, 0));
        new TextBlock(new IS(), level, new Position(10, 0));
        new TextBlock(new STOP(), level, new Position(11, 0));

        // KEY IS PUSH
        new TextBlock(new SubjectName("KEY"), level, new Position(4, 0));
        new TextBlock(new IS(), level, new Position(5, 0));
        new TextBlock(new PUSH(), level, new Position(6, 0));

        // KEY IS OPEN
        new TextBlock(new SubjectName("KEY"), level, new Position(0, 9));
        new TextBlock(new IS(), level, new Position(1, 9));
        new TextBlock(new OPEN(), level, new Position(2, 9));

        // DOOR IS SHUT
        new TextBlock(new SubjectName("DOOR"), level, new Position(4, 9));
        new TextBlock(new IS(), level, new Position(5, 9));
        new TextBlock(new SHUT(), level, new Position(6, 9));

        // FLAG IS WIN
        new TextBlock(new SubjectName("FLAG"), level, new Position(9, 9));
        new TextBlock(new IS(), level, new Position(10, 9));
        new TextBlock(new WIN(), level, new Position(11, 9));

        new Subject("BABA", level,new Position(3, 6));
        new Subject("KEY", level,new Position(5, 6));
        new Subject("DOOR", level,new Position(7, 6));
        new Subject("FLAG", level,new Position(9, 6));

        return level;
    }

    //============================Управление-уровнем================================

    /**
     * Вызывается по каждому действию игрока
     * @param direction направление движения игрока
     */
    public void makeStep(Direction direction){
        // Выполняем игровые правила (движения, остановки и тп)
        releaseFeatures(direction);
        moveGameObjects();

        // Пересчитывем и выполняем пересчитанные правила
        calculateRules();
        releaseRules();

        // Обновляем состояние уровня (победа/поражение и перерисовка)
        checkSuccess();
        repaint();
    }

    /**
     * Двигает все игровые объекты на поле
     */
    private void moveGameObjects() {
        for(GameObject gameObject: gameObjects){
            gameObject.move();
        }
    }

    /**
     * Проверяет, сможет ли какой-био объект войти в проверяемую ячейку
     * @param position проверяемая ячейка
     * @param direction направление движения объектов на текущем шаге
     */
    public boolean canLetTo(Position position, Direction direction){
        List<GameObject> gameObjects = getCellOnNextStep(position);

        for(GameObject gameObject : gameObjects){
            if(gameObject.hasFeature(STOP.class)) {
                return false;
            }
            if(gameObject.hasFeature(PUSH.class)){
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
        for(Rule rule : rules){
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
                gameObjects.sort((g1, g2) -> Integer.compare(g1.getPosition().getX(), g2.getPosition().getX()));
                break;
            case LEFT:
                gameObjects.sort((g1, g2) -> Integer.compare(g2.getPosition().getX(), g1.getPosition().getX()));
                break;
            case DOWN:
                gameObjects.sort((g1, g2) -> Integer.compare(g1.getPosition().getY(), g2.getPosition().getY()));
                break;
            case UP:
                gameObjects.sort((g1, g2) -> Integer.compare(g2.getPosition().getY(), g1.getPosition().getY()));
                break;
        }
    }

    /**
     * Выполняет действия правил, взаимодействующих с одним объектом в клетке.
     * Например YOU - двигает объект со свойством YOU
     * @param direction навправление движения объектов
     */
    private void releaseActions(Direction direction) {
        for(GameObject gameObject : gameObjects){
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
        for(int index = 0; index < gameObjects.size(); index++) {
            releaseInteractionsInCell(gameObjects.get(index).getNextPosition(), direction);
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
        deactivateTextBlocks();
        makeDefaultRules();
        findAndHighlightRules();
    }

    /**
     * Находит и выделяет правила на уровне
     */
    private void findAndHighlightRules() {
        for(GameObject gameObject : gameObjects){
            rules.addAll(findAndHighlightRulesInPosition(gameObject.getNextPosition()));
        }
    }

    /**
     * Деактивирует текстовые блок (делает серыми,
     * что означает непривязаность к составленному на уровне правилу)
     */
    private void deactivateTextBlocks() {
        for(GameObject gameObject : gameObjects){
            gameObject.clearFeatures();
            if(gameObject instanceof TextBlock textBlock){
                textBlock.deactivate();
            }
        }
    }

    /**
     * Находит и выделяет правила, корень которых находится в положении position
     * @param position начальное положение предпологаемых правил
     * @return Уникальный список правил
     */
    public HashSet<Rule> findAndHighlightRulesInPosition(Position position){
        HashSet<Rule> rules = new HashSet<>();

        Rule rule;
        if((rule = findAndHighlightRulesInPositionByDirection(position, Direction.RIGHT)) != null)
            rules.add(rule);
        if((rule = findAndHighlightRulesInPositionByDirection(position, Direction.DOWN)) != null)
            rules.add(rule);

        return rules;
    }

    /**
     * Находит и выделяет правило
     * @param position начальное положение предпологаемого правила
     * @param direction направление чтения предпологаемого правила
     * @return Правило
     */
    public Rule findAndHighlightRulesInPositionByDirection(Position position, Direction direction){
        Rule rule = null;
        if(direction == Direction.STAY) return rule;

        List<RuleText> phrase = findPhrase(position, direction);
        if(isRule(phrase)){
            rule = ruleFromPhrase(phrase);
            highlightRule(position, direction);
        }

        return rule;
    }

    /**
     * Выделяет (подсвечивает) правило
     * @param position начальное положение правила
     * @param direction направлени чтения правила
     */
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

    /**
     * Создает правило из набора RuleText
     */
    private Rule ruleFromPhrase(List<RuleText> phrase){
        Operand firstWord = (Operand) phrase.get(0);
        Operator secondWord = (Operator) phrase.get(1);
        Operand thirdWord = (Operand) phrase.get(2);

        return new Rule(firstWord, secondWord, thirdWord);
    }

    /**
     * Находит словосочетание из трех в ряд стоящих ячеек
     * @param position положение первого слова фразы
     * @param direction направление чтения
     * @return Сочетание слов
     */
    private List<RuleText> findPhrase(Position position, Direction direction){
        List<RuleText> phrase = new ArrayList<>();
        Position currentPos = position;

        for(int i = 0; i < 3; i++) {
            RuleText ruleText = null;
            List<GameObject> cell = getCellOnNextStep(currentPos);

            for (GameObject gameObject : cell) {
                if (gameObject.isTextBlock()){
                    ruleText = ((TextBlock) gameObject).getRuleText();
                    break;
                }
            }
            phrase.add(ruleText);
            currentPos = currentPos.getNeightboor(direction);
        }

        return phrase;
    }

    /**
     * @param phrase cписок из трех слов типа RuleText
     * @return Может ли список считаться правилом
     */
    private boolean isRule(List<RuleText> phrase){
        if(phrase.get(0) instanceof Operand firstWord &&
                phrase.get(1) instanceof Operator secondWord &&
                phrase.get(2) instanceof Operand thirdWord){
            return secondWord.canInteract(firstWord, thirdWord);
        }

        return false;
    }

    /**
     * @return  Состояние уровня (победа, поражение, продолжение)
     */
    public Status checkSuccess(){
        // Проверить наличие объектов YOU
        if(!hasYouObjects())
            return Status.LOSE;

        // Проверка победы (YOU и WIN в одной ячейке)
        if(hasYouWithWin())
            return Status.WIN;

        // Если нет ни победы, ни поражения, продолжаем
        return Status.CONTINUE;
    }

    /**
     * @return количество объектов со свойством YOU на уровне
     */
    private boolean hasYouObjects(){
        int youGameObjects = 0;
        for(GameObject gameObject : gameObjects){
            if(gameObject.hasFeature(YOU.class)){
                youGameObjects++;
            }
        }
        return youGameObjects > 0;
    }

    /**
     * @return есть ли ячейки, в который находятся объекты со
     * свойствами YOU и WIN одновременно
     */
    private boolean hasYouWithWin(){
        for(GameObject gameObject : gameObjects){
            List<GameObject> cell = getCell(gameObject.getPosition());
            boolean hasYOU = false, hasWIN = false;
            for(GameObject objectInCell : cell){
                if(objectInCell.hasFeature(YOU.class)){
                    hasYOU = true;
                }
                if(objectInCell.hasFeature(WIN.class)){
                    hasWIN = true;
                }
            }
            if(hasYOU && hasWIN){
                return true;
            }
        }
        return false;
    }


    //===========================Управление-объектами===============================

    /**
     * Добавить GameObject в уровень
     */
    public void addGameObject(GameObject gameObject){
        gameObjects.add(gameObject);
    }

    /**
     * Превращает один gameObject в другой
     * @param from какой превращается
     * @param to во что превращается (чтобы превратиться в текст
     *           с названием исходного обхекта, можно ввести "TEXT")
     */
    public void transformGameObject(GameObject from, String to){
        Position pos = from.getPosition();

        // Создаем новый объект
        if(!Objects.equals(to, "TEXT")){
            new Subject(to,this, pos);
        }
        else{
            SubjectName subjectName = new SubjectName(from.getName());
            new TextBlock(subjectName, this, pos);
        }

        // Удаляем старый объект
        from.destroy();

        // Пересчитывем и выполняем пересчитанные правила
        calculateRules();
        releaseRules();
    }


    /**
     * Уничтожает объект с поля
     */
    public void destroyGameObject(GameObject gameObject){
        gameObjects.remove(gameObject);
        gameObject = null;
    }

    //==============================Создание-уровня=================================

    /**
     * Обнуляет текущий список правил и добавляет список правил по умолчанию
     */
    public void makeDefaultRules(){
        rules = new HashSet<>();

        // TEXT IS PUSH
        Rule text_is_push = new Rule(new SubjectName("TEXT"), new IS(), new PUSH());
        rules.add(text_is_push);

        // INVISIBLE_WALL IS STOP
        Rule invisible_wall_is_stop = new Rule(new SubjectName("INVISIBLE_WALL"), new IS(), new STOP());
        rules.add(invisible_wall_is_stop);
    }

    /**
     * Создает окно, закрашенное BACKGROUND_COLOR определенного размера
     */
    private void createScreen() {
        setBounds(0, 0, width * CELL_SIZE, height * CELL_SIZE);
        setPreferredSize(new Dimension(width * CELL_SIZE, height * CELL_SIZE) );
        setBackground(BACKGOUND_COLOR);
        revalidate();
    }


    //================================Гетеры-сетеры===================================

    /**
     * @param pos Позиция "ячейки"
     * @return Список объектов, находящихся в "ячейке"
     */
    public List<GameObject> getCell(Position pos){
        List<GameObject> gameObjects = new ArrayList<>();

        for(GameObject gameObject : this.gameObjects){
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

        for(GameObject gameObject : this.gameObjects){
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
        return gameObjects;
    }

    /**
     * @return id уровня
     */
    public String getId() {
        return id;
    }


    //=================================РИСОВАНИЕ===================================

    @Override
    public void paint(Graphics g){
        super.paint(g);

        drawField(g);
        drawGameObjects(g);
    }

    /**
     * Закрашивает окно фоновым цветом в соответствии с размером уровня
     */
    private void drawField(Graphics g){
        g.setColor(BACKGOUND_COLOR);
        g.fillRect(0, 0, width * CELL_SIZE, height * CELL_SIZE);
    }

    /**
     * Рисует все объекты на уровне
     */
    private void drawGameObjects(Graphics g) {
        for (GameObject gameObject : gameObjects) {
            drawGameObject(gameObject, g);
        }
    }

    /**
     * Рисует GameObject
     */
    private void drawGameObject(GameObject gameObject, Graphics g) {
        TexturePaint texture = new TexturePaint(gameObject.getImage(), new Rectangle(CELL_SIZE, CELL_SIZE));
        Position pos = gameObject.getPosition();
        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(texture);
        g2d.fillRect(pos.getX()*CELL_SIZE, pos.getY()*CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width * CELL_SIZE,
                height * CELL_SIZE);
    }
}