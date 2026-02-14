package GameObjects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Subject extends GameObject {

    public Subject(String type, Position pos){
        super(GameObjectType.SUBJECT, type, pos);

        File file = new File("images/" + type + ".png");
        try{
            _image = ImageIO.read(file);
        }
        catch (IOException exception){
            _image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = _image.createGraphics();

            // Сглаживание
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            g2d.setColor(Color.RED);
            g2d.fillOval(0, 0, 100, 100);
            g2d.dispose();
        }
    }
}
