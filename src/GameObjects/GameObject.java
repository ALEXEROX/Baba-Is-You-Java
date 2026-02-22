package GameObjects;

import Core.*;
import Rules.*;
import Rules.Features.*;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public abstract class GameObject {


    //=============================Поля================================

    private final GameObjectType _gameObjectType;
    protected final String _name;
    private Position _currentPosition;
    private Position _nextPosition;
    protected BufferedImage _image;
    protected HashSet<Feature> _features;
    protected Level _level;


    //==========================Конструктор============================

    /**
     * Игровой объект
     * @param gameObjectType Тип объекта: SUBJECT или TEXT
     * @param name Имя объекта (BABA, KEKE, ROCK, IS и т.д.)
     * @param level Уровень, на который загружается объект
     * @param pos Позиция объекта
     */
    protected GameObject(GameObjectType gameObjectType, String name, Level level, Position pos) {
        _gameObjectType = gameObjectType; _name = name; _level = level;
        _currentPosition = _nextPosition = pos; _features = new HashSet<>();

        _level.addGameObject(this);
    }

    //=========================Гетеры-сетеры===========================

    /**
     * @return Имя объекта ("TEXT" для текста)
     */
    public String getName(){
        return _name;
    }

    /**
     * @return Позиция объекта
     */
    public Position getPosition(){
        return _currentPosition;
    }

    /**
     * @return Позиция, в которую объект собирается прийти
     */
    public Position getNextPosition(){
        return _nextPosition;
    }

    /**
     * @return Текстура
     */
    public BufferedImage getImage(){
        return _image;
    }

    /**
     * Может ли объект пустить в свою клетку другой объект
     */
    public boolean canLet(Direction direction){
        if(hasFeature(new STOP())){
            return false;
        }

        return _level.canLetTo(_currentPosition.getNeightboor(direction), direction);
    }

    /**
     * @return Является ли игровой объект TextBlock
     */
    public boolean isTextBlock(){
        return _gameObjectType == GameObjectType.TEXT;
    }

    /**
     * @return Является ли игровой объект Subject
     */
    public boolean isSubject(){
        return _gameObjectType == GameObjectType.SUBJECT;
    }

    /**
     * @return Все Feature объекта
     */
    public HashSet<Feature> getFeatures() {
        return _features;
    }

    /**
     * Очистить список Feature
     */
    public void clearFeatures(){
        _features.clear();
    }

    /**
     * Добавить Feature объекту
     */
    public void addFeature(Feature feature){
        _features.add(feature);
    }

    /**
     * Имеет ли объект выбранную Feature
     */
    public boolean hasFeature(Feature feature){
        for(Feature feature1 : _features){
            if(Objects.equals(feature.getText(), feature1.getText())){
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
        _currentPosition = _nextPosition;
    }

    /**
     * Заготовить перемещение
     * @param direction Направление движения
     */
    public void prepareMove(Direction direction){
        _nextPosition = _currentPosition.getNeightboor(direction);
    }

    /**
     * Отменить заготовленное перемещение
     */
    public void cancelMove(){
        _nextPosition = _currentPosition;
    }

    /**
     * Превращение в другой объект
     * @param name Имя нового объекта ("TEXT" для превращения в текст)
     */
    public void transform(String name){
        _level.transformGameObject(this, name);
    }

    /**
     * Уничтожить объект
     */
    public void destroy(){
        _level.destroyGameObject(this);
    }
}

enum GameObjectType{
    SUBJECT,
    TEXT
}
