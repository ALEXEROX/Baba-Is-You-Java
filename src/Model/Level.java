package Model;

import View.Condition;
import Model.Rules.*;
import Model.GameObjects.*;
import Model.Rules.Features.*;
import Model.Rules.Operators.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Level{

    //========================Константы=============================

    private static final int CELL_SIZE = 75;
    private static final Color BACKGOUND_COLOR = Color.BLACK;


    //==========================Поля================================

    private final int width;
    private final int height;
    private List<GameObject> gameObjects;
    private HashSet<Rule> currentRules;
    private String id;


    //====================================Конструкторы======================================

    public Level(int width, int height){
        this.width = width; this.height = height;
        gameObjects = new ArrayList<>();
        buildBorder();
        makeDefaultRules();
    }

    public Level(int width, int height, String id){
        this(width, height);
        this.id = id;
    }

    public Level createCopy() {
        return switch (id) {
            case "LEVEL_1" -> LevelBuilder.createLevel1();
            case "LEVEL_2" -> LevelBuilder.createLevel2();
            case "LEVEL_3" -> LevelBuilder.createLevel3();
            default -> new Level(16, 10);
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

    //============================Управление-уровнем================================

    /**
     * Вызывается по каждому действию игрока
     * @param direction направление движения игрока
     */
    public void processStep(Direction direction){
        // Выполняем игровые правила (движения, остановки и тп)
        releaseGameObjectsFeatures(direction);
        moveGameObjects();

        // Пересчитывем и выполняем пересчитанные правила
        calculateRules();
        releaseRules();
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
    public boolean canLetToCell(Position position, Direction direction){
        List<GameObject> gameObjects = getCellOnNextStep(position);

        for(GameObject gameObject : gameObjects){
            if(gameObject.hasFeature(STOP.class)) {
                return false;
            }
            if(gameObject.hasFeature(PUSH.class)){
                if(!canLetToCell(gameObject.getNextPosition().getNeightboor(direction), direction)){
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
        for(Rule rule : currentRules){
            rule.release(this);
        }
    }

    /**
     * Выполняет все действия в соответствии с правилами
     * @param direction навправление движения объектов
     */
    private void releaseGameObjectsFeatures(Direction direction){
        sortGameObjects(direction);
        performFeaturesActions(direction);
        performFeaturesInteractions(direction);
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
    private void performFeaturesActions(Direction direction) {
        for(GameObject gameObject : gameObjects){
            for(Feature feature : gameObject.getFeatures()){
                feature.performActionOnGameObject(gameObject, direction);
            }
        }
    }

    /**
     * Выполняет действия правил, взаимодействующих с двумя объектами, находящимися в одной ячекйке.
     * Например PUSH - пытается вытолкнуть объект, в который пытается двигаться другой объект
     * @param direction навправление движения объектов
     */
    private void performFeaturesInteractions(Direction direction) {
        for(int index = 0; index < gameObjects.size(); index++) {
            performInteractionsInCell(gameObjects.get(index).getNextPosition(), direction);
        }
    }

    /**
     * Выполняет действия правил, взаимодействующих с двумя объектами в определенной ячейке.
     * @param position проверяемая позиция, в которую предполагают оказаться объекты на следующем шаге
     * @param direction навправление движения объектов
     */
    private void performInteractionsInCell(Position position, Direction direction){
        List<GameObject> gameObjectsInCell = getCellOnNextStep(position);

        for(GameObject gameObject : gameObjectsInCell){
            List<GameObject> otherGameObjects = new ArrayList<>(List.copyOf(gameObjectsInCell));
            otherGameObjects.remove(gameObject);

            for(Feature feature : gameObject.getFeatures()) {
                for (GameObject otherGameobject : otherGameObjects) {
                    feature.performInteractionBetweenGameObjects(gameObject, otherGameobject, direction);
                }
            }
        }
    }

    /**
     * Находит правила, описанные на поле
     */
    private void calculateRules(){
        deactivateTextBlocks();
        clearGameObjectsFeatures();
        makeDefaultRules();
        findAndHighlightRules();
    }

    private void clearGameObjectsFeatures() {
        for(GameObject gameObject : gameObjects){
            gameObject.clearFeatures();
        }
    }

    /**
     * Находит и выделяет правила на уровне
     */
    private void findAndHighlightRules() {
        for(GameObject gameObject : gameObjects){
            currentRules.addAll(findAndHighlightRulesInPosition(gameObject.getNextPosition()));
        }
    }

    /**
     * Деактивирует текстовые блок (делает серыми,
     * что означает непривязаность к составленному на уровне правилу)
     */
    private void deactivateTextBlocks() {
        for(GameObject gameObject : gameObjects){
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
        List<List<TextBlock>> phrases = findPhrases(position);

        for(List<TextBlock> phrase : phrases){
            if(isRule(phrase)){
                rules.add(ruleFromPhrase(phrase));
                highlightPhrase(phrase);
            }
        }

        return rules;
    }

    List<List<TextBlock>> findPhrases(Position position) {
        List<List<TextBlock>> phrases = new ArrayList<>();
        Position currentPos = position;
        List<Direction> directions = new ArrayList<>();
        directions.add(Direction.RIGHT);
        directions.add(Direction.DOWN);

        for (Direction direction : directions){
            List<TextBlock> phrase = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                TextBlock textBlock = null;
                List<GameObject> cell = getCellOnNextStep(currentPos);

                for (GameObject gameObject : cell) {
                    if (gameObject.isTextBlock()) {
                        textBlock = ((TextBlock) gameObject);
                        break;
                    }
                }
                phrase.add(textBlock);
                currentPos = currentPos.getNeightboor(direction);
            }
            phrases.add(phrase);
        }

        return phrases;
    }

    /**
     * Выделяет (подсвечивает) правило
     * @param rule правило, находящееся на поле
     */
    private void highlightPhrase(List<TextBlock> rule) {
        for(TextBlock textBlock : rule){
            textBlock.activate();
        }
    }

    /**
     * Создает правило из набора RuleText
     */
    private Rule ruleFromPhrase(List<TextBlock> phrase){
        Operand firstWord = (Operand) phrase.get(0).getRuleText();
        Operator secondWord = (Operator) phrase.get(1).getRuleText();
        Operand thirdWord = (Operand) phrase.get(2).getRuleText();

        return new Rule(firstWord, secondWord, thirdWord);
    }

    /**
     * @param phrase cписок из трех слов типа RuleText
     * @return Может ли список считаться правилом
     */
    private boolean isRule(List<TextBlock> phrase){
        if(phrase.size() == 3 &&
                phrase.get(0) != null &&
                phrase.get(0).getRuleText() instanceof Operand firstWord &&
                phrase.get(1) != null &&
                phrase.get(1).getRuleText() instanceof Operator secondWord &&
                phrase.get(2) != null &&
                phrase.get(2).getRuleText() instanceof Operand thirdWord){
            return secondWord.canInteract(firstWord, thirdWord);
        }

        return false;
    }

    /**
     * @return  Состояние уровня (победа, поражение, продолжение)
     */
    public Condition checkSuccess(){
        // Проверить наличие объектов YOU
        if(!hasYouObjects())
            return Condition.LOSE;

        // Проверка победы (YOU и WIN в одной ячейке)
        if(hasYouWithWin())
            return Condition.WIN;

        // Если нет ни победы, ни поражения, продолжаем
        return Condition.RUNNING;
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
        currentRules = new HashSet<>();

        // TEXT IS PUSH
        Rule text_is_push = new Rule(new SubjectName("TEXT"), new IS(), new PUSH());
        currentRules.add(text_is_push);

        // INVISIBLE_WALL IS STOP
        Rule invisible_wall_is_stop = new Rule(new SubjectName("INVISIBLE_WALL"), new IS(), new STOP());
        currentRules.add(invisible_wall_is_stop);
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

    public int getWidth(){
        return width;
    }

    public int getHeight() {
        return height;
    }
}