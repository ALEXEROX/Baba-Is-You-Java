package GameObjects;

import java.awt.image.BufferedImage;

public abstract class GameObject {
    private GameObjectType _gameObjectType;
    private String _type;
    private Position _pos;
    protected BufferedImage _image;

    protected GameObject(GameObjectType gameObjectType, String type, Position pos) {
        _gameObjectType = gameObjectType; _type = type; _pos = pos;
    }

    public String getText(){
        return _type;
    }

    public boolean isText(){
        return _gameObjectType == GameObjectType.TEXT;
    }

    public boolean isSubject(){
        return _gameObjectType == GameObjectType.SUBJECT;
    }

    public Position getPosition(){
        return _pos;
    }

    public void move(Direction direction){
        _pos = _pos.getNeightboor(direction);
    }

    public BufferedImage getImage(){
        return _image;
    }
}

enum GameObjectType{
    SUBJECT,
    TEXT;
}
