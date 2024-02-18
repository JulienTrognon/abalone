package view.scenes;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class SceneWin extends Scene {
    protected JPanel selectionArea;
    protected GridBagConstraints gbc = new GridBagConstraints();
    protected RoundButton quitButton, menuButton, replayButton;
    protected JLabel winText;
    protected String winner;
    protected SceneMenu sceneMenu;
    protected Color colorButton1, colorButton2;

    public SceneWin(boolean visible, SceneMenu sceneMenu, int winner) {
        super(visible);
        frame.getContentPane().add(this);
        colorButton1 = new Color(22, 138, 173);
        colorButton2 = new Color(181, 228, 140);
        this.sceneMenu = sceneMenu;

        switch (winner) {
            case 1 -> this.winner = 
                "Joueur 1";
            case 2 -> this.winner =
                "Joueur 2";
            default -> this.winner =
                "Vide... :(";
        }
        
        sceneWinSetup();
        selectionAreaSetup();
        replayButtonSetup();
        menuButtonSetup();
        winTextSetup();
    }

    void sceneWinSetup() {
        setSize(screenSize);
    }

    void winTextSetup() {
        Title winText = new Title("Victoire du " + winner);
        add(winText);
    }

    void selectionAreaSetup() {
        selectionArea = new JPanel();
        selectionArea.setLayout(new GridLayout(3, 1, 0, 10));
        selectionArea.setBackground(Color.WHITE);
        selectionArea.setBounds((screenSizeWidth / 2) - 100, (screenSizeHeight / 2) - 55, 200, 200);
        selectionArea.setVisible(true);
        selectionArea.setOpaque(false);
        add(selectionArea);
    }

    void menuButtonSetup() {
        menuButton = new RoundButton();
        menuButton.setLabel("RETOUR");
        menuButton.setRadius(20);
        menuButton.setPreferredSize(new Dimension(200, 50));
        menuButton.setBackgroundColor(colorButton1);
        menuButton.setBorder(new LineBorder(new Color(0, 0, 0, 0)));
        menuButton.setOpaque(false);
        menuButton.addActionListener(e -> {
            frame.setTitle("Javalon");
            Scene.changeScene(this, sceneMenu);
        });
        HoverAnimation animation = new HoverAnimation(menuButton);
        animation.setButtonColor1(colorButton1);
        animation.setButtonColor2(colorButton2);
        menuButton.addMouseListener(animation);
        menuButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        selectionArea.add(menuButton, gbc);
    }

    void replayButtonSetup() {
        replayButton = new RoundButton();
        replayButton.setLabel("REJOUER");
        replayButton.setRadius(20);
        replayButton.setPreferredSize(new Dimension(200, 50));
        replayButton.setBackgroundColor(colorButton1);
        replayButton.setBorder(new LineBorder(new Color(0, 0, 0, 0)));
        replayButton.setOpaque(false);
        replayButton.addActionListener(e -> {
            frame.setTitle("Javalon - partie en cours");
            SceneJeu Jeu = new SceneJeu(false, SceneOptions.getSettings(), sceneMenu);
            Scene.changeScene(this, Jeu);
        });
        HoverAnimation animation = new HoverAnimation(replayButton);
        animation.setButtonColor1(colorButton1);
        animation.setButtonColor2(colorButton2);
        replayButton.addMouseListener(animation);
        replayButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        selectionArea.add(replayButton, gbc);
    }

    private class Title extends JPanel {
        private String title;

        Title(String s) {
            title = s;
            setBounds(0, 0, screenSizeWidth, screenSizeHeight);
            setOpaque(false);
            setVisible(true);
        }

        private void drawStringMiddleOfPanel(String string, Graphics g, int y, boolean underline) {
            FontMetrics fm = g.getFontMetrics();

            int stringWidth = fm.stringWidth(string);
            // int stringAccent = fm.getAscent();
            int xCoordinate = getWidth() / 2 - stringWidth / 2;
            int yCoordinate = y;

            g.drawString(string, xCoordinate, yCoordinate);
            if (underline) {
                g.fillRect(xCoordinate, yCoordinate + 5, stringWidth, 5);
            }
        }

        @Override
        public void paint(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Helvetica", Font.PLAIN, 60));
            drawStringMiddleOfPanel(title, g, (int) (getHeight() * 0.3), false);
        }
    }
}
