import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BabaIsYouWindow extends JFrame {

    private Level _level;
    private int _levelSuccess;

    public BabaIsYouWindow(){
        createContent();
        loadLevel(Level.generateLevel());
        createKeyListener();
        buildWindow();
    }

    public void loadLevel(Level level){
        if(_level != null)
            remove(_level);

        _level = level;
        _level.build(this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BabaIsYouWindow();
            }
        });
    }

    private void createContent(){
        JPanel content = (JPanel)getContentPane();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS) );
    }

    private void buildWindow(){
        pack();
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createKeyListener() {
        KeyListener listener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                handlingKey(e.getKeyChar());
            }

            @Override
            public void keyPressed(KeyEvent e) {
                handlingKey(e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };
    }

    private void handlingKey(char key){

    }
}
