package GameObjects;

import Core.Level;
import Rules.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TextBlock extends GameObject {
    private static final Color INACTIVE_COLOR = Color.GRAY;
    private static final Color ACTIVE_COLOR = Color.WHITE;

    private RuleText _ruleText;
    private Color _color;

    public TextBlock(RuleText ruleText, Level level, Position pos){
        super(GameObjectType.TEXT, "TEXT", level, pos);
        createText(ruleText);
    }

    public RuleText getRuleText(){
        return _ruleText;
    }

    private void createText(RuleText ruleText){
        _ruleText = ruleText; _color = INACTIVE_COLOR;
        texture = createOptimalTextImage(_ruleText.getText());
    }

    /**
     * Создает текстуру текста
     */
    private BufferedImage createOptimalTextImage(String text) {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Находим оптимальный размер шрифта
        int fontSize = findOptimalFontSize(g2d, text, 90, 90);

        Font font = new Font("Montserrat", Font.BOLD, fontSize);
        g2d.setFont(font);

        FontMetrics fm = g2d.getFontMetrics();
        int x = (100 - fm.stringWidth(text)) / 2;
        int y = (100 - fm.getHeight()) / 2 + fm.getAscent();

        g2d.setColor(_color);
        g2d.drawString(text, x, y);
        g2d.dispose();

        return image;
    }

    /**
     * Находит оптимальный размер шрифта для текстуры
     * @param text текст
     * @param maxWidth максимальная длина
     * @param maxHeight максимальная высота
     * @return размер шрифта
     */
    private int findOptimalFontSize(Graphics2D g2d, String text,
                                           int maxWidth, int maxHeight) {
        int minSize = 1;
        int maxSize = 100;
        int optimalSize = 1;

        while (minSize <= maxSize) {
            int midSize = (minSize + maxSize) / 2;
            Font font = new Font("Montserrat", Font.BOLD, midSize);
            FontMetrics fm = g2d.getFontMetrics(font);

            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getHeight();

            if (textWidth <= maxWidth && textHeight <= maxHeight) {
                optimalSize = midSize;
                minSize = midSize + 1;
            } else {
                maxSize = midSize - 1;
            }
        }

        return optimalSize;
    }

    /**
     * Активирует (подсвечивает) текст
     */
    public void activate(){
        _color = ACTIVE_COLOR;
        texture = createOptimalTextImage(_ruleText.getText());
    }

    /**
     * Деактивирует (приглушает) текст
     */
    public void deactivate(){
        _color = INACTIVE_COLOR;
        texture = createOptimalTextImage(_ruleText.getText());
    }
}
