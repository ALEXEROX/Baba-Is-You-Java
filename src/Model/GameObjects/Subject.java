package Model.GameObjects;

import Model.Level;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Subject extends GameObject {

    public Subject(String type, Level level, Position pos){
        super(GameObjectType.SUBJECT, type, level, pos);
        createTexture();
    }

    /**
     * Создает текстуру
     */
    private void createTexture(){
        try{
            texture = ImageIO.read(new File("images/" + getName() + ".png"));
        }
        catch (IOException exception){
            createDefaultTexture();
        }
    }

    /**
     * Создает текстуру по умолчанию
     */
    public void createDefaultTexture(){
        texture = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = texture.createGraphics();

        // Сглаживание
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2d.setColor(Color.RED);
        g2d.fillOval(0, 0, 100, 100);
        g2d.dispose();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Subject subject){
            return subject.getName().equals(getName()) &&
                    subject.getPosition().equals(getPosition()) &&
                    subject.getNextPosition().equals(getNextPosition());
        }

        return false;
    }

    @Override
    public String toString() {
        return getName() + " " + getPosition();
    }
}
