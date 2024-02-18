package view.scenes;

import model.Settings;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class SceneOptions extends Scene {
    protected JPanel selectionArea;
    protected GridBagConstraints gbc = new GridBagConstraints();
    protected JPanel length, maxScore, maxMarbles;
    protected JLabel validation;
    protected SceneMenu previous;
    protected RoundButton validationButton, previousButton;
    private static Settings settings;
    protected int panelFloor;

    SceneOptions(boolean visible, SceneMenu previous) {
        super(visible);
        this.previous = previous;

        panelFloor = 0;
        settings = new Settings.SettingsBuilder().build();

        setOpaque(false);
        sceneOptionsSetup();
        selectionAreaSetup();

        length = new JPanel();
        addOptionPanel(length, "TAILLE PLATEAU", 4, 8, 4);

        maxScore = new JPanel();
        addOptionPanel(maxScore, "SCORE MAX", 1, 34, 5);

        maxMarbles = new JPanel();
        addOptionPanel(maxMarbles, "NOMBRE MAX BILLES", 1, 6, 3);

        validationButtonSetup();
        previousButtonSetup();
    }

    private void sceneOptionsSetup() {
        setSize(screenSize);
        setLayout(null);
    }

    private void selectionAreaSetup() {
        selectionArea = new JPanel();
        selectionArea.setLayout(new GridLayout(5, 1, 10, 10));
        selectionArea.setBounds((screenSizeWidth / 2) - 170, (screenSizeHeight / 2) - 200, 350, 300);
        selectionArea.setVisible(true);
        selectionArea.setOpaque(false);
        add(selectionArea);
    }

    private void addOptionPanel(JPanel panel, String nom, int min, int max, int defaultVal) {
        panel.setOpaque(false);

        JLabel label = new JLabel(nom);
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Helvetica", Font.PLAIN, 20));

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(defaultVal, min, max, 1);
        JSpinner spinner = new JSpinner(spinnerModel);
        spinner.setFont(new Font("Helvetica", Font.PLAIN, 20));
        spinner.setOpaque(false);

        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);

        panel.add(label);
        panel.add(spinner);

        gbc.gridx = panelFloor++;
        selectionArea.add(panel, gbc);
    }

    private void validationButtonSetup() {
        validationButton = new RoundButton();
        validationButton.setLabel("CONFIRMER");
        validationButton.setFont(new Font("Helvetica", Font.PLAIN, 18));
        validationButton.setRadius(20);
        validationButton.setPreferredSize(new Dimension(200, 50));
        validationButton.setBackgroundColor(previous.getColorButton(1));
        validationButton.setBorder(new LineBorder(new Color(0, 0, 0, 0)));
        validationButton.setOpaque(false);

        HoverAnimation animation = new HoverAnimation(validationButton);
        animation.setButtonColor1(previous.getColorButton(1));
        animation.setButtonColor2(previous.getColorButton(2));
        validationButton.addMouseListener(animation);

        Timer timer = new Timer(1000, l -> {
            validationButton.setLabel("CONFIRMER");
            validationButton.setBackgroundColor(previous.getColorButton(1));
            animation.resume();
        });
        timer.setRepeats(false);

        validationButton.addActionListener(e -> {
            timer.start();
            try {
                int length = (int) ((JSpinner) this.length.getComponent(1)).getValue();
                int maxScore = (int) ((JSpinner) this.maxScore.getComponent(1)).getValue();
                int maxMarbles = (int) ((JSpinner) this.maxMarbles.getComponent(1)).getValue();

                int nbMarblesByPlayer = 0;
                for (int i = length; i < length + ((2 * length / 3)); i++) {
                    nbMarblesByPlayer += i;
                }

                if (nbMarblesByPlayer - 4 < maxScore) {
                    throw new Exception();
                }

                settings = new Settings.SettingsBuilder()
                    .set_length(length)
                    .set_maxScore(maxScore)
                    .set_maxMarbles(maxMarbles)
                    .build()
                ;

                animation.stop();

                validationButton.setBackgroundColor(new Color(0x73a942));
                validationButton.setLabel("SAUVEGARDÉ");
            } catch (Exception prob) {
                validationButton.setBackgroundColor(new Color(0xbf0a30));
                validationButton.setLabel("ERREUR");
            }
        });
        gbc.gridx = panelFloor++;
        selectionArea.add(validationButton, gbc);
    }

    private void previousButtonSetup() {
        previousButton = new RoundButton();
        previousButton.setLabel("RETOUR");
        previousButton.setFont(new Font("Helvetica", Font.PLAIN, 18));
        previousButton.setRadius(20);
        previousButton.setPreferredSize(new Dimension(200, 50));
        previousButton.setBackgroundColor(previous.getColorButton(1));
        previousButton.setBorder(new LineBorder(new Color(0, 0, 0, 0)));
        previousButton.setOpaque(false);
        previousButton.addActionListener(e -> {
            frame.setTitle("Javalon");
            Scene.changeScene(this, previous);
        });
        HoverAnimation animation = new HoverAnimation(previousButton);
        animation.setButtonColor1(previous.getColorButton(1));
        animation.setButtonColor2(previous.getColorButton(2));
        previousButton.addMouseListener(animation);
        gbc.gridx = panelFloor++;
        selectionArea.add(previousButton, gbc);
    }

    public static Settings getSettings() {
        return settings;
    }
}
