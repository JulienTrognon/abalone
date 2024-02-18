package view.scenes;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Shortcut extends KeyAdapter {
    // keycode -> touche pour declancher la pause
    int keycode = KeyEvent.VK_ESCAPE;
    SceneMenu quit;
    SceneJeu resume;
    ScenePause pause;
    boolean enPause;

    Shortcut(SceneMenu quit, SceneJeu resume, ScenePause pause) {
        enPause = false;
        this.quit = quit;
        this.resume = resume;
        this.pause = pause;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == keycode && !enPause) {
            Scene.changeScene(resume, pause);
            enPause = true;
        }

        else if (e.getKeyCode() == keycode && enPause) {
            Scene.changeScene(pause, resume);
            enPause = false;
        }
    }
}
