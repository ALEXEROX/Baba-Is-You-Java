package GameObjects;

import Core.*;
import Rules.*;

import java.awt.image.BufferedImage;
import java.util.HashSet;

public abstract class GameObject {


    //=============================Поля================================

    private final GameObjectType _gameObjectType;
    private final String _name;
    private Position _currentPosition;
    private Position _nextPosition;
    protected BufferedImage _image;
    protected HashSet<Feature> _features;
    protected Level _level;


    //==========================Конструктор============================

    /**
     * Игровой объект
     * @param gameObjectType Тип объекта: SUBJECT или TEXT
     * @param name Имя объекта (BABA, KEKE, ROCK, IS и т. д.)
     * @param pos Позиция объекта
     */
    protected GameObject(GameObjectType gameObjectType, String name, Position pos) {
        _gameObjectType = gameObjectType; _name = name; _currentPosition = pos; _features = new HashSet<>();
    }

    //=========================Гетеры-сетеры===========================

    /**
     * @return Имя объекта или содержание текста
     */
    public String getName(){
        return _name;
    }

    public Position getPosition(){
        return _currentPosition;
    }

    public BufferedImage getImage(){
        return _image;
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

    public HashSet<Feature> getFeatures() {
        return _features;
    }

    public void clearFeatures(){
        _features.clear();
    }

    public void addFeature(Feature feature){
        _features.add(feature);
    }

    public boolean hasFeature(Feature feature){
        return _features.contains(feature);
    }


    //==========================Действия==============================

    public void move(){
        if(_nextPosition != null) {
            _currentPosition = _nextPosition;
            _nextPosition = null;
        }
    }

    public void prepareMove(Direction direction){
        _nextPosition = _currentPosition.getNeightboor(direction);
    }

    public void cancelMove(){
        _nextPosition = _currentPosition;
    }

    public void transform(GameObject newGameObject){
        _level.transformGameObject(this, newGameObject.getName());
    }

    public void destroy(){
        _level.destroyGameObject(this);
    }
}

enum GameObjectType{
    SUBJECT,
    TEXT;
}
