package View;

import Model.GameObjects.GameObject;
import Model.GameObjects.Position;
import Model.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LevelPanel extends JPanel {

    private static final int CELL_SIZE = 75;
    private static final Color BACKGOUND_COLOR = Color.BLACK;
    private static final int FRAME_RATE = 60;
    private static Level currentLevel;

    public LevelPanel(Level level){
        currentLevel = level;
        createPanel();

        Timer framerateTimer = new Timer(1000 / FRAME_RATE, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        framerateTimer.start();
    }

    /**
     * Создает окно, закрашенное BACKGROUND_COLOR определенного размера
     */
    private void createPanel() {
        setLayout(new BorderLayout());
        setBounds(0, 0, currentLevel.getWidth() * CELL_SIZE, currentLevel.getHeight() * CELL_SIZE);
        setPreferredSize(new Dimension(currentLevel.getWidth() * CELL_SIZE, currentLevel.getHeight() * CELL_SIZE) );
        setBackground(BACKGOUND_COLOR);
        revalidate();
    }

    public Level getCurrentLevel(){
        return currentLevel;
    }

    //=================================РИСОВАНИЕ===================================

    @Override
    public void paint(Graphics g){
        super.paint(g);

        drawField(g);
        drawGameObjects(g);
    }

    /**
     * Закрашивает окно фоновым цветом в соответствии с размером уровня
     */
    private void drawField(Graphics g){
        g.setColor(BACKGOUND_COLOR);
        g.fillRect(0, 0, currentLevel.getWidth() * CELL_SIZE, currentLevel.getHeight() * CELL_SIZE);
    }

    /**
     * Рисует все объекты на уровне
     */
    private void drawGameObjects(Graphics g) {
        for (GameObject gameObject : currentLevel.getGameObjects()) {
            drawGameObject(gameObject, g);
        }
    }

    /**
     * Рисует GameObject
     */
    private void drawGameObject(GameObject gameObject, Graphics g) {
        TexturePaint texture = new TexturePaint(gameObject.getImage(), new Rectangle(CELL_SIZE, CELL_SIZE));
        Position pos = gameObject.getPosition();
        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(texture);
        g2d.fillRect(pos.getX()*CELL_SIZE, pos.getY()*CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(currentLevel.getWidth() * CELL_SIZE,
                currentLevel.getHeight() * CELL_SIZE);
    }
}
