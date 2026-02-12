import java.awt.image.BufferedImage;

public abstract class GameObject {
    private String _type;
    private Position _pos;
    protected BufferedImage _image;

    public GameObject(String type, Position pos) {
        _type = type;
        _pos = pos;
    }

    public Position GetPosition(){
        return _pos;
    }

    public void Move(Position direction){

    }

    public void Transform(String type){

    }

    public void Destroy(){

    }

    public BufferedImage GetImage(){
        return _image;
    }
}
