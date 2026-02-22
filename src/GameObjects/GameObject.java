package GameObjects;

import Core.*;
import Rules.*;
import Rules.Features.*;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.List;

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
     * @param name Имя объекта (BABA, KEKE, ROCK, IS и т. д.)
     * @param pos Позиция объекта
     */
    protected GameObject(GameObjectType gameObjectType, String name, Level level, Position pos) {
        _gameObjectType = gameObjectType; _name = name; _level = level;
        _currentPosition = _nextPosition = pos; _features = new HashSet<>();

        _level.addGameObject(this);
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

    public Position getNextPosition(){
        return _nextPosition;
    }

    public BufferedImage getImage(){
        return _image;
    }

    public List<GameObject> getNeightboorCell(Direction direction){
        return _level.getCell(getPosition().getNeightboor(direction));
    }

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
        _currentPosition = _nextPosition;
    }

    public void prepareMove(Direction direction){
        _nextPosition = _currentPosition.getNeightboor(direction);
    }

    public void cancelMove(){
        _nextPosition = _currentPosition;
    }

    public void transform(String name){
        _level.transformGameObject(this, name);
    }

    public void destroy(){
        _level.destroyGameObject(this);
    }
}

enum GameObjectType{
    SUBJECT,
    TEXT;
}
