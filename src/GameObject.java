import java.awt.image.BufferedImage;

public abstract class GameObject {
    private GameObjectType _type;
    private Position _pos;
    protected BufferedImage _image;

    protected GameObject(GameObjectType type, Position pos) {
        _type = type;
        _pos = pos;
    }

    public Position getPosition(){
        return _pos;
    }

    public void move(Position direction){

    }

    public void transform(String type){

    }

    public void destroy(){

    }

    public BufferedImage getImage(){
        return _image;
    }
}

enum GameObjectType{
    SUBJECT,
    TEXT;
}
