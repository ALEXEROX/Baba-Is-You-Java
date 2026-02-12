import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Level extends JPanel {

    public static final int WIDTH = 16;
    public static final int HEIGHT = 10;

    public static final int CELL_SIZE = 75;

    public static final Color BACKGOUND_COLOR = Color.BLACK;

    private List<GameObject> _gameObjects;
    private BabaIsYouWindow _window;

    public Level(){
        setBounds(0, 0, WIDTH*CELL_SIZE, HEIGHT*CELL_SIZE);
        setPreferredSize(new Dimension(WIDTH*CELL_SIZE, HEIGHT*CELL_SIZE) );
        setBackground(BACKGOUND_COLOR);
    }

    public Level(List<GameObject> gameObjects){
        _gameObjects = gameObjects;
        new Level();
    }


    public void Build(BabaIsYouWindow window){
        this._window = window;
        this._window.add(this);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);

        DrawField(g);

        if(_gameObjects != null) {
            for (GameObject gameObject : _gameObjects) {
                DrawSubject(gameObject, g);
            }
        }
    }

    private void DrawField(Graphics g){
        g.setColor(BACKGOUND_COLOR);
        g.fillRect(0, 0, WIDTH*CELL_SIZE, HEIGHT*CELL_SIZE);
    }

    private void DrawSubject(GameObject gameObject, Graphics g) {
        TexturePaint texture = new TexturePaint(gameObject.GetImage(), new Rectangle(CELL_SIZE, CELL_SIZE));
        Position pos = gameObject.GetPosition();
        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(texture);
        g2d.fillRect(pos.getX()*CELL_SIZE, pos.getY()*CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH * CELL_SIZE,
                HEIGHT * CELL_SIZE);
    }
}
