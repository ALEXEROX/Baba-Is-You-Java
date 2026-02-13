public class Direcion extends Position{
    public static final Direcion RIGHT = new Direcion(1, 0);
    public static final Direcion LEFT = new Direcion(-1, 0);
    public static final Direcion UP = new Direcion(0, -1);
    public static final Direcion DOWN = new Direcion(0, 1);
    public static final Direcion STAY = new Direcion(0, 0);

    public Direcion(int x, int y){
        super(x, y);
    }
}
