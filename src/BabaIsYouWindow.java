import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BabaIsYouWindow extends JFrame {

    private Level _level;

    public BabaIsYouWindow(){
        JPanel content = (JPanel)getContentPane();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS) );

        Subject sub = new Subject("Baba", new Position(1, 1));
        Subject sub2 = new Subject("Babas", new Position(2, 1));
        Text text = new Text("Babas", new Position(2, 2), Color.RED);
        List<GameObject> gameObjects = new ArrayList<GameObject>();
        gameObjects.add(sub);
        gameObjects.add(sub2);
        gameObjects.add(text);

        Level level = new Level(gameObjects);

        LoadLevel(level);

        pack();
        this.setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void LoadLevel(Level level){
        if(_level != null)
            remove(_level);

        _level = level;
        _level.Build(this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BabaIsYouWindow();
            }
        });
    }

}
