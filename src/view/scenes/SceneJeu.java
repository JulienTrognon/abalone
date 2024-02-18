package view.scenes;

import java.awt.BorderLayout;

import model.*;
import view.board.BoardVue;
import view.board.Score;

public class SceneJeu extends Scene {
    private BoardVue board;
    private SceneMenu menu;
    private ScenePause pause;
    private Shortcut shortcut;


    public SceneJeu(boolean visible, Settings settings, SceneMenu menu) {
        super(visible);
        SceneJeuSetup();
        this.menu = menu;
        pause = new ScenePause(false, this.menu, this);
        shortcut = new Shortcut(this.menu, this, pause);
        frame.getContentPane().add(pause);
        frame.getContentPane().add(this);
        gameInfoSetup();
        PlateauSetup();

        pause.addKeyListener(shortcut);
        addKeyListener(shortcut);
    }

    private void SceneJeuSetup() {
        setLayout(new BorderLayout());
        frame.setTitle("Javalon - partie en cours");
    }

    private void PlateauSetup() {
        board = new BoardVue(this);
        add(board, BorderLayout.CENTER);
    }

    private void gameInfoSetup() {
        add(new Score());
    }

    public void showWinScreen(int winner) {
        Scene.changeScene(this, new SceneWin(true, this.menu, winner));
    }

    Shortcut getShortcut() {
        return shortcut;
    }
}