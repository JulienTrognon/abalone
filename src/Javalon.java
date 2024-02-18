import java.awt.*;
import javax.swing.*;

import view.scenes.Scene;
import view.scenes.SceneMenu;


public class Javalon extends JFrame {
    protected static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static Javalon main;
    private static SceneMenu sceneMenu;

    private void drawStringMiddleOfPanel(String string, Graphics g, int y, boolean underline) {
        FontMetrics fm = g.getFontMetrics();
        int stringWidth = fm.stringWidth(string);
        // int stringAccent = fm.getAscent();

        int xCoordinate = getWidth() / 2 - stringWidth / 2;
        int yCoordinate = y;

        // draw String
        g.drawString(string, xCoordinate, yCoordinate);
        if (underline) {
            g.fillRect(xCoordinate, yCoordinate + 5, stringWidth, 5);
        }
    }

    private Javalon() {
        JPanel p = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint p = new GradientPaint(0, 0, new Color(217, 237, 146), Scene.screenSizeWidth,
                        Scene.screenSizeHeight, new Color(24, 78, 119), true);
                g2.setPaint(p);
                g.fillRect(0, 0, Scene.screenSizeWidth, Scene.screenSizeHeight);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Rockwell", Font.PLAIN, 60));
                drawStringMiddleOfPanel("JAVALON", g2, (int) (getHeight() * 0.1), true);
                g.setFont(new Font("Helvetica",
                        Font.PLAIN, 10));
                drawStringMiddleOfPanel(
                        "Monrousseau Matthieu, Montre Maxime, Pariat Simon, Trognon Julien, Wong Cedrick ©", g2,
                        (int) (getHeight() * 0.99), false);
            }
        };
        p.setLayout(new BorderLayout());
        setContentPane(p);
        setTitle("Javalon");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Scene.setFrame(this);
        Scene.setFullscreen();
        sceneMenu = new SceneMenu(true);
        getContentPane().add(sceneMenu);
    }

    /** Crée et / ou renvoie l'unique instance de {@link Javalon} */
    public static JFrame buildInstance() {
        if (main == null) {
            main = new Javalon();
        }
        return main;
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                buildInstance();
            }
        });
    }
}