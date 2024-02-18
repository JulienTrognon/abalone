package view.scenes;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

class HoverAnimation extends MouseAdapter {
    private RoundButton component;
    private Color buttonColor1 = Color.RED;
    private Color buttonColor2 = Color.BLUE;
    private Timer timer1 = new Timer(1, null);
    private Timer timer2 = new Timer(1, null);
    private int r1, g1, b1, r2, g2, b2;
    private boolean validate;

    HoverAnimation(RoundButton c) {
        component = c;
        r1 = buttonColor1.getRed();
        g1 = buttonColor1.getGreen();
        b1 = buttonColor1.getBlue();
        r2 = buttonColor2.getRed();
        g2 = buttonColor2.getGreen();
        b2 = buttonColor2.getBlue();
    }

    void setButtonColor1(Color c) {
        buttonColor1 = c;
    }

    void setButtonColor2(Color c) {
        buttonColor2 = c;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (!validate) {
            r1 = buttonColor1.getRed();
            g1 = buttonColor1.getGreen();
            b1 = buttonColor1.getBlue();
            timer2.stop();
            timer1 = new Timer(1, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (r1 < buttonColor2.getRed())
                        r1 = component.getBackgroundColor().getRed() + 1;
                    if (g1 < buttonColor2.getGreen())
                        g1 = component.getBackgroundColor().getGreen() + 1;
                    if (b1 > buttonColor2.getBlue())
                        b1 = component.getBackgroundColor().getBlue() - 1;
                    if (r1 >= buttonColor2.getRed() && g1 >= buttonColor2.getGreen() && b1 <= buttonColor2.getBlue()) {
                        timer1.stop();
                    } else {
                        component.setBackgroundColor(new Color(r1, g1, b1));
                        component.repaint();
                    }

                }
            });
            timer1.start();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!validate) {
            r2 = buttonColor2.getRed();
            g2 = buttonColor2.getGreen();
            b2 = buttonColor2.getBlue();
            timer1.stop();
            timer2 = new Timer(1, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (r2 > buttonColor1.getRed())
                        r2 = component.getBackgroundColor().getRed() - 1;
                    if (g2 > buttonColor1.getGreen())
                        g2 = component.getBackgroundColor().getGreen() - 1;
                    if (b2 < buttonColor1.getBlue())
                        b2 = component.getBackgroundColor().getBlue() + 1;
                    if (r2 <= buttonColor1.getRed() && g2 <= buttonColor1.getGreen() && b2 >= buttonColor1.getBlue()) {
                        timer2.stop();
                    } else {
                        component.setBackgroundColor(new Color(r2, g2, b2));
                        component.repaint();
                    }
                }
            });
            timer2.start();
        }
    }

    void stop() {
        validate = true;
        timer1.stop();
        timer2.stop();
    }

    void resume() {
        validate = false;
    }
}