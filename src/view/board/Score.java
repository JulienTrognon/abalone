package view.board;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;


public class Score extends JPanel {
    public Score() {
        setOpaque(false);
    }

    private void drawStringMiddleOfPanel(String string, Graphics g) {
        int stringWidth = 0;
        int stringAccent = 0;
        int xCoordinate = 0;
        int yCoordinate = 0;
        FontMetrics fm = g.getFontMetrics();

        stringWidth = fm.stringWidth(string);
        stringAccent = fm.getAscent();
        xCoordinate = getWidth() / 2 - stringWidth / 2;
        yCoordinate = getHeight() / 4 + stringAccent / 2;

        // draw String
        g.drawString(string, xCoordinate, yCoordinate);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        String p1 = "Joueur 1 : " + "4" + " | " + "3" + " : Joueur 2";
        g.setFont(new Font("Helvetica", Font.PLAIN, 30));
        g.setColor(Color.WHITE);
        drawStringMiddleOfPanel(p1, g);
    }
}
