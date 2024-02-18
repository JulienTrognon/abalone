package view.scenes;

// import java.awt.GridBagLayout;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class SceneMenu extends Scene {
    private JPanel selectionArea;
    private GridBagConstraints gbc = new GridBagConstraints();
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int screenSizeWidth = (int) screenSize.getWidth();
    private int screenSizeHeight = (int) screenSize.getHeight();
    private RoundButton playButton, optionsButton, quitButton;
    private SceneJeu menuJeu;
    private SceneOptions menuOptions;
    private Color colorButton1, colorButton2;

    public SceneMenu(boolean visible) {
        super(visible);
        colorButton1 = new Color(22, 138, 173);
        colorButton2 = new Color(181, 228, 140);
        sceneMenuSetup();
        menuOptions = new SceneOptions(false, this);
        frame.getContentPane().add(menuOptions);
        selectionAreaSetup();
        playButtonSetup();
        optionsButtonSetup();
        quitButtonSetup();
    }

    private void sceneMenuSetup() {
        setSize(screenSize);
    }

    private void selectionAreaSetup() {
        selectionArea = new JPanel();
        selectionArea.setLayout(new GridLayout(3, 1, 0, 10));
        selectionArea.setBackground(Color.WHITE);
        selectionArea.setBounds((screenSizeWidth / 2) - 100, (screenSizeHeight / 2) - 150, 200, 200);
        selectionArea.setVisible(true);
        selectionArea.setOpaque(false);
        add(selectionArea, 1);
    }

    private void playButtonSetup() {
        playButton = new RoundButton();
        playButton.setLabel("JOUER");
        playButton.setRadius(20);
        playButton.setPreferredSize(new Dimension(200, 50));
        playButton.setBackgroundColor(colorButton1);
        playButton.setBorder(new LineBorder(new Color(0, 0, 0, 0)));
        playButton.setOpaque(false);
        playButton.addActionListener(e -> {
            menuJeu = new SceneJeu(false, SceneOptions.getSettings(), this);
            Scene.changeScene(this, menuJeu);
        });
        HoverAnimation animation = new HoverAnimation(playButton);
        animation.setButtonColor1(colorButton1);
        animation.setButtonColor2(colorButton2);
        playButton.addMouseListener(animation);
        playButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        selectionArea.add(playButton, gbc);
    }

    private void optionsButtonSetup() {
        optionsButton = new RoundButton();
        optionsButton.setLabel("OPTIONS");
        optionsButton.setRadius(20);
        optionsButton.setPreferredSize(new Dimension(200, 50));
        optionsButton.setBackgroundColor(colorButton1);
        optionsButton.setBorder(new LineBorder(new Color(0, 0, 0, 0)));
        optionsButton.setOpaque(false);
        optionsButton.addActionListener(e -> {
            frame.setTitle("Javalon - OPTIONS");
            Scene.changeScene(this, menuOptions);
        });
        HoverAnimation animation = new HoverAnimation(optionsButton);
        animation.setButtonColor1(colorButton1);
        animation.setButtonColor2(colorButton2);
        optionsButton.addMouseListener(animation);
        optionsButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        selectionArea.add(optionsButton, gbc);

    }

    private void quitButtonSetup() {
        quitButton = new RoundButton();
        quitButton.setLabel("QUITTER");
        quitButton.setRadius(20);
        quitButton.setPreferredSize(new Dimension(200, 50));
        quitButton.setBackgroundColor(colorButton1);
        quitButton.setBorder(new LineBorder(new Color(0, 0, 0, 0)));
        quitButton.setOpaque(false);
        quitButton.addActionListener(e -> {
            System.exit(0);
        });
        HoverAnimation animation = new HoverAnimation(quitButton);
        animation.setButtonColor1(colorButton1);
        animation.setButtonColor2(colorButton2);
        quitButton.addMouseListener(animation);
        quitButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        selectionArea.add(quitButton, gbc);
    }

    /** revoie colorButton correspondant au n.
        n  = 1 : colorButton1
        n != 1 : colorButton2 */
    Color getColorButton(int n) {
        return (n == 1) ? colorButton1 : colorButton2;
    }
}