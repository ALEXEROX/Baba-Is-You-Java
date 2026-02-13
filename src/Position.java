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
}

enum Direction {

    STAY(new Position(0, 0)),
    UP(new Position(0, -1)),
    RIGHT(new Position(1, 0)),
    DOWN(new Position(0, 1)),
    LEFT(new Position(-1, 0)),
    ;

    Direction(Position pos) {
        _pos = pos;
    }

    private final Position _pos;

    public int getX(){
        return _pos.getX();
    }

    public int getY(){
        return _pos.getY();
    }
}