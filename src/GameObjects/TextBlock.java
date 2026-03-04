package GameObjects;

import Core.Level;
import Rules.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TextBlock extends GameObject {
    private static final Color INACTIVE_COLOR = Color.GRAY;
    private static final Color ACTIVE_COLOR = Color.WHITE;
    private static final String FONT = "Fixedsys Excelsior 3.01";

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

        String line1 = text;
        String line2 = null;

        // Разбиваем текст, если он длиннее 3 символов
        if (text.length() > 3) {
            int splitPoint = text.length() / 2;
            // Ищем ближайший пробел для более красивого разбиения
            // или разбиваем по середине, если пробелов нет
            if (text.contains(" ")) {
                String[] words = text.split(" ");
                if (words.length >= 2) {
                    line1 = words[0];
                    StringBuilder sb = new StringBuilder();
                    for (int i = 1; i < words.length; i++) {
                        if (sb.length() > 0) sb.append(" ");
                        sb.append(words[i]);
                    }
                    line2 = sb.toString();
                } else {
                    line1 = text.substring(0, splitPoint);
                    line2 = text.substring(splitPoint);
                }
            } else {
                line1 = text.substring(0, splitPoint);
                line2 = text.substring(splitPoint);
            }
        }

        if (line2 != null) {
            // Рисуем текст в две строки
            int fontSize = findOptimalFontSizeForTwoLines(g2d, line1, line2, 90, 90);
            Font font = new Font(FONT, Font.BOLD, fontSize);
            g2d.setFont(font);

            FontMetrics fm = g2d.getFontMetrics();
            int lineHeight = fm.getHeight();
            int totalHeight = lineHeight * 2;

            int y1 = (100 - totalHeight) / 2 + fm.getAscent();
            int y2 = y1 + lineHeight;

            int x1 = (100 - fm.stringWidth(line1)) / 2;
            int x2 = (100 - fm.stringWidth(line2)) / 2;

            g2d.setColor(_color);
            g2d.drawString(line1, x1, y1);
            g2d.drawString(line2, x2, y2);
        } else {
            // Рисуем текст в одну строку (как было раньше)
            int fontSize = findOptimalFontSize(g2d, text, 90, 90);
            Font font = new Font(FONT, Font.BOLD, fontSize);
            g2d.setFont(font);

            FontMetrics fm = g2d.getFontMetrics();
            int x = (100 - fm.stringWidth(text)) / 2;
            int y = (100 - fm.getHeight()) / 2 + fm.getAscent();

            g2d.setColor(_color);
            g2d.drawString(text, x, y);
        }

        g2d.dispose();
        return image;
    }

    /**
     * Находит оптимальный размер шрифта для двух строк текста
     */
    private int findOptimalFontSizeForTwoLines(Graphics2D g2d, String line1, String line2,
                                               int maxWidth, int maxHeight) {
        int minSize = 1;
        int maxSize = 50; // Уменьшаем максимальный размер для двух строк
        int optimalSize = 1;

        while (minSize <= maxSize) {
            int midSize = (minSize + maxSize) / 2;
            Font font = new Font(FONT, Font.BOLD, midSize);
            FontMetrics fm = g2d.getFontMetrics(font);

            int textWidth1 = fm.stringWidth(line1);
            int textWidth2 = fm.stringWidth(line2);
            int maxTextWidth = Math.max(textWidth1, textWidth2);
            int textHeight = fm.getHeight() * 2; // Общая высота двух строк

            if (maxTextWidth <= maxWidth && textHeight <= maxHeight) {
                optimalSize = midSize;
                minSize = midSize + 1;
            } else {
                maxSize = midSize - 1;
            }
        }

        return optimalSize;
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
            Font font = new Font(FONT, Font.BOLD, midSize);
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
