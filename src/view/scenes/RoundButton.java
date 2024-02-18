package view.scenes;

import java.awt.*;
import javax.swing.*;

class RoundButton extends JButton {
    private Color backgroundColor, borderColor;
    private String label = "";
    private int radius;

    RoundButton() {
        setContentAreaFilled(false);
    }

    Color getBackgroundColor() {
        return backgroundColor;
    }
    void setBackgroundColor(Color c) {
        backgroundColor = c;
    }
    Color getBorderColor() {
        return borderColor;
    }
    void setBorderColor(Color c) {
        borderColor = c;
    }
    int getRadius() {
        return radius;
    }
    void setRadius(int r) {
        radius = r;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String s) {
        label = s;
    }

    void drawStringMiddleOfPanel(String string, Graphics g) {
        FontMetrics fm = g.getFontMetrics();

        int stringWidth = fm.stringWidth(string);
        int stringAccent = fm.getAscent();
        int xCoordinate = getWidth() / 2 - stringWidth / 2;
        int yCoordinate = getHeight() / 2 + stringAccent / 2;

        // draw String
        g.drawString(string, xCoordinate, yCoordinate);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g);
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.setColor(Color.WHITE);
        g.setFont(new Font("Helvetica", Font.PLAIN, 18));
        drawStringMiddleOfPanel(label, g);
    }
}
