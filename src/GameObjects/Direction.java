package GameObjects;

public enum Direction {

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

    public Direction opposite(){
        switch(this){
            case RIGHT ->{ return LEFT; }
            case LEFT ->{ return RIGHT; }
            case UP ->{ return DOWN; }
            case DOWN ->{ return UP; }
            default -> {return STAY; }
        }
    }
}
