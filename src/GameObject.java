import java.awt.image.BufferedImage;

public abstract class GameObject {
    private String _type;
    private Position _pos;
    protected BufferedImage _image;

    public GameObject(String type, Position pos) {
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
