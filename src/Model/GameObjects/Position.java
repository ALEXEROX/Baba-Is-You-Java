package Model.GameObjects;

import java.util.Objects;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y){
        this.x = x; this.y = y;
    }

    public Position() {
        new Position(0, 0);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public Position getNeightboor(Direction dir){
        return new Position(x + dir.getX(), y + dir.getY());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Position position){
            return position.getX() == getX() && position.getY() == getY();
        }

        return false;
    }
}
