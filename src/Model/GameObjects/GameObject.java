package Model.GameObjects;

import Model.Level;
import Model.Rules.*;
import Model.Rules.Features.*;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Objects;

public abstract class GameObject {


    //=============================Поля================================

    private final GameObjectType gameObjectType;
    protected final String name;
    private Position currentPosition;
    private Position nextPosition;
    protected BufferedImage texture;
    protected HashSet<Feature> features;
    protected Level owner;


    //==========================Конструктор============================

    /**
     * Игровой объект
     * @param gameObjectType Тип объекта: SUBJECT или TEXT
     * @param name Имя объекта (BABA, KEKE, ROCK, IS и т.д.)
     * @param level Уровень, на который загружается объект
     * @param position Позиция объекта
     */
    protected GameObject(GameObjectType gameObjectType, String name, Level level, Position position) {
        this.gameObjectType = gameObjectType; this.name = name; owner = level;
        currentPosition = nextPosition = position; features = new HashSet<>();

        owner.addGameObject(this);
    }

    //=========================Гетеры-сетеры===========================

    /**
     * @return Имя объекта ("TEXT" для текста)
     */
    public String getName(){
        return name;
    }

    /**
     * @return Позиция объекта
     */
    public Position getPosition(){
        return currentPosition;
    }

    /**
     * @return Позиция, в которую объект собирается прийти
     */
    public Position getNextPosition(){
        return nextPosition;
    }

    /**
     * @return Текстура
     */
    public BufferedImage getImage(){
        return texture;
    }

    /**
     * Может ли объект пустить в свою клетку другой объект
     */
    public boolean canLet(Direction direction){
        if(hasFeature(STOP.class)){
            return false;
        }
        if(hasFeature(PUSH.class)) {
            return owner.canLetToCell(currentPosition.getNeightboor(direction), direction);
        }
        return true;
    }

    /**
     * @return Является ли игровой объект TextBlock
     */
    public boolean isTextBlock(){
        return gameObjectType == GameObjectType.TEXT;
    }

    /**
     * @return Является ли игровой объект Subject
     */
    public boolean isSubject(){
        return gameObjectType == GameObjectType.SUBJECT;
    }

    /**
     * @return Все Feature объекта
     */
    public HashSet<Feature> getFeatures() {
        return features;
    }

    /**
     * Очистить список Feature
     */
    public void clearFeatures(){
        features.clear();
    }

    /**
     * Добавить Feature объекту
     */
    public void addFeature(Feature feature){
        features.add(feature);
    }

    /**
     * Имеет ли объект выбранную Feature
     */
    public boolean hasFeature(Class feature){
        for(Feature feature1 : features){
            Class featureClass = feature1.getClass();
            if(Objects.equals(feature, featureClass)){
                return true;
            }
        }
        return false;
    }


    //==========================Действия==============================

    /**
     * Осуществить заготовленное передвижение
     */
    public void move(){
        currentPosition = nextPosition;
    }

    /**
     * Заготовить перемещение
     * @param direction направление движения
     */
    public void prepareMove(Direction direction){
        nextPosition = currentPosition.getNeightboor(direction);
    }

    /**
     * Отменить заготовленное перемещение
     */
    public void cancelMove(){
        nextPosition = currentPosition;
    }

    /**
     * Превращение в другой объект
     * @param name имя нового объекта ("TEXT" для превращения в текст)
     */
    public void transform(String name){
        owner.transformGameObject(this, name);
    }

    /**
     * Уничтожить объект
     */
    public void destroy(){
        owner.destroyGameObject(this);
    }


    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract String toString();
}

enum GameObjectType{
    SUBJECT,
    TEXT
}
