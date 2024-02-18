package view.scenes;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import java.awt.*;

public abstract class Scene extends JLayeredPane {
    protected static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int screenSizeWidth = (int) screenSize.getWidth();
    public static int screenSizeHeight = (int) screenSize.getHeight();
    protected static JFrame frame;

    public Scene(boolean visible) {
        setVisible(visible);
    }

    public static void changeScene(Scene from, Scene to) {
        from.setVisible(false);
        to.setVisible(true);
        to.requestFocus();
    }

    public static void setFrame(JFrame f) {
        frame = f;
    }

    public static void setFullscreen() {
        screenSizeWidth = (int) fullscreen().getWidth();
        screenSizeHeight = (int) fullscreen().getHeight();
    }

    public static Rectangle fullscreen() {
        GraphicsConfiguration gc = frame.getGraphicsConfiguration();

        Rectangle bounds = gc.getBounds();

        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(gc);

        Rectangle effectiveScreenArea = new Rectangle();

        effectiveScreenArea.x = bounds.x + screenInsets.left;
        effectiveScreenArea.y = bounds.y + screenInsets.top;
        effectiveScreenArea.height = bounds.height - screenInsets.top - screenInsets.bottom;
        effectiveScreenArea.width = bounds.width - screenInsets.left - screenInsets.right;
        return effectiveScreenArea;
    }
}
