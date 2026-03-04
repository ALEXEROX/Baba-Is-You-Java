package Model.GameObjects;

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

    public boolean equal(Position pos){
        return x == pos.getX() && y == pos.getY();
    }
}
