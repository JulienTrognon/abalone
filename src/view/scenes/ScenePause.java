package view.scenes;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class ScenePause extends Scene {
    protected JPanel selectionArea;
    protected GridBagConstraints gbc = new GridBagConstraints();
    protected SceneMenu quit;
    protected SceneJeu resume;
    protected RoundButton resumeButton, quitButton;
    protected int panelFloor;

    public ScenePause(boolean visible, SceneMenu quit, SceneJeu resume) {
        super(visible);
        this.resume = resume;
        this.quit = quit;

        panelFloor = 0;

        setOpaque(false);
        sceneOptionSetup();
        selectionAreaSetup();

        resumeButtonSetup();
        quitBoutonSetup();
    }

    private void sceneOptionSetup() {
        setSize(screenSize);
        setLayout(null);
    }

    private void selectionAreaSetup() {
        selectionArea = new JPanel();
        selectionArea.setLayout(new GridLayout(2, 1, 10, 10));
        selectionArea.setBounds((screenSizeWidth / 2) - 110, (screenSizeHeight / 2) - 200, 220, 180);
        selectionArea.setVisible(true);
        selectionArea.setOpaque(false);
        add(selectionArea);
    }

    private void quitBoutonSetup() {
        quitButton = new RoundButton();
        quitButton.setLabel("QUITTER");
        quitButton.setFont(new Font("Helvetica", Font.PLAIN, 18));
        quitButton.setRadius(20);
        quitButton.setPreferredSize(new Dimension(200, 50));
        quitButton.setBackgroundColor(quit.getColorButton(1));
        quitButton.setBorder(new LineBorder(new Color(0, 0, 0, 0)));
        quitButton.setOpaque(false);
        quitButton.addActionListener(e -> {
            frame.setTitle("Javalon");
            Scene.changeScene(this, quit);
            resume.getShortcut().enPause = false;
        });
        HoverAnimation animation = new HoverAnimation(quitButton);
        animation.setButtonColor1(quit.getColorButton(1));
        animation.setButtonColor2(quit.getColorButton(2));
        quitButton.addMouseListener(animation);
        gbc.gridx = panelFloor++;
        selectionArea.add(quitButton, gbc);
    }

    private void resumeButtonSetup() {
        resumeButton = new RoundButton();
        resumeButton.setLabel("REPRENDRE");
        resumeButton.setFont(new Font("Helvetica", Font.PLAIN, 18));
        resumeButton.setRadius(20);
        resumeButton.setPreferredSize(new Dimension(200, 50));
        resumeButton.setBackgroundColor(quit.getColorButton(1));
        resumeButton.setBorder(new LineBorder(new Color(0, 0, 0, 0)));
        resumeButton.setOpaque(false);
        resumeButton.addActionListener(e -> {
            Scene.changeScene(this, resume);
            resume.getShortcut().enPause = false;
        });
        HoverAnimation animation = new HoverAnimation(resumeButton);
        animation.setButtonColor1(quit.getColorButton(1));
        animation.setButtonColor2(quit.getColorButton(2));
        resumeButton.addMouseListener(animation);
        gbc.gridx = panelFloor++;
        selectionArea.add(resumeButton, gbc);
    }
}
