package GameObjects;

public class Position {
    private int _x;
    private int _y;

    public Position(int x, int y){
        _x = x; _y = y;
    }

    public Position() {
        new Position(0, 0);
    }

    public int getX(){
        return _x;
    }

    public int getY(){
        return _y;
    }

    public Position getNeightboor(Direction dir){
        return new Position(_x + dir.getX(), _y + dir.getY());
    }

    public boolean equal(Position pos){
        return _x == pos.getX() && _y == pos.getY();
    }
}

