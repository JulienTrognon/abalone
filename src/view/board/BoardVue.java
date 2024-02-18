package view.board;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;

import model.Game;
import model.Marble;
import model.Point;
import controller.Control;

import view.scenes.Scene;
import view.scenes.SceneJeu;
import view.scenes.SceneOptions;

/**
 * <p>
 * {@code BoardVue} est une extension de {@link JPanel}, cette classe représente
 * graphiquement les données de son modèle ({@link Board}).
 * </p>
 * <p>
 * Affiche le {@code board}, le {@code score} actuel, le {@code tour} actuel et
 * la {@code sélections} des billes pour chaque move.
 * </p>
 * 
 * @param Selectbox controler
 */

public class BoardVue extends JPanel {
    protected int horizontalGap, verticalGap, borderSpacing, pieceDiameter, startX, startY, startXExt, startYExt; // dimension
    private Game model;
    private Control control;
    private int boardSize;
    private SceneJeu sceneJeu;
    private Color color1, color2;
    private String p1, p2, total;

    public BoardVue(SceneJeu sceneJeu) {
        this.sceneJeu = sceneJeu;
        
        model = new Game(SceneOptions.getSettings());
        boardSize = model.getLength();

        this.control = new Control(model);

        color1 = new Color(24, 78, 119);
        color2 = new Color(217, 237, 146);
        int width = Scene.screenSizeWidth / 6;
        int height = Scene.screenSizeHeight / 2;
        horizontalGap = (((width / (boardSize - 1))) / 2) * 2;
        verticalGap = (int) (Math.sqrt(Math.pow(horizontalGap, 2) -
            Math.pow(horizontalGap / 2, 2)) / 2) * 2;
        pieceDiameter = (int) (((horizontalGap) * 0.8));
        borderSpacing = 30;
        startX = (Scene.screenSizeWidth / 2) - width / 2;
        startY = (Scene.screenSizeHeight / 2) - height / 3;

        this.control.setVariables(horizontalGap, verticalGap, boardSize, pieceDiameter, startX, startY);
        this.control.setView(this);
        
        addMouseListener(control);
        setOpaque(false);
    }

    void setRandomColor(Graphics g, Random r) {
        g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
    }

    @Override
    public void update(Graphics g) {
        Marble[][] board = model.getBoardContent();
        List<Point> selected = model.getSelectedPoints();

        for (Point p : selected) {
            selectPiece(g, p.i(), p.j());
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == Marble.WHITE) {
                    placePiece(g, i, j, Color.RED);
                }
                if (board[i][j] == Marble.BLACK) {
                    placePiece(g, i, j, Color.BLACK);
                }
            }
        }
    }


    /** Convertit des coordonnées en pixels en des coordonnnées de point
     * que le model comprend. */
    public int[] convert(int x, int y) {
        int i = 0;
        int j = 0;
        if (((y - startY) % verticalGap) > verticalGap / 2) {
            i = ((y - startY) / verticalGap) + 1;
        } else {
            i = ((y - startY) / verticalGap);
        }
        int fromX = startX - (boardSize - 1 - Math.abs(i - (boardSize - 1))) * (horizontalGap / 2);
        if (((x - fromX) % horizontalGap) > horizontalGap / 2) {
            j = (x - fromX) / horizontalGap + 1;
        } else {
            j = (x - fromX) / horizontalGap;
        }
        boolean entreDeuxLignes = ((y - startY) % verticalGap) > pieceDiameter / 2
                && ((y - startY) % verticalGap) < verticalGap - pieceDiameter / 2;
        boolean entreDeuxColonnes = false;
        if (i % 2 == 0) {
            if ((((x - fromX) % horizontalGap) > pieceDiameter / 2)
                    && (((x - fromX) % horizontalGap) < horizontalGap - pieceDiameter / 2)) {
                entreDeuxColonnes = true;
            }
        } else {
            if ((((x - fromX) % horizontalGap) > pieceDiameter / 2)
                    && (((x - fromX) % horizontalGap) < horizontalGap
                            - pieceDiameter / 2)) {
                entreDeuxColonnes = true;
            }
        }
        if (entreDeuxLignes || entreDeuxColonnes) {
            return null;
        } else {
            int[] t = { i, j };
            return t;
        }
    }

    public void placePiece(Graphics g, int i, int j, Color c) {
        Color tmp = g.getColor();
        g.setColor(c);
        int fromX = ((startX - ((boardSize - 1) - Math.abs(i - (boardSize - 1))) * (horizontalGap / 2))
            + j * horizontalGap) - pieceDiameter / 2;
        int fromY = startY + i * verticalGap - pieceDiameter / 2;

        g.fillOval(fromX, fromY, pieceDiameter, pieceDiameter); // bille
        g.setColor(new Color(255, 255, 255, 80));

        // reflet sur la bille
        g.fillOval(fromX + pieceDiameter / 8, fromY + pieceDiameter / 8, pieceDiameter / 3, pieceDiameter / 3); 
        g.setColor(tmp);
    }

    public void selectPiece(Graphics g, int i, int j) {
        Color tmp = g.getColor();
        g.setColor(Color.PINK);
        int fromX = ((startX - ((boardSize - 1) - Math.abs(i - (boardSize - 1))) * (horizontalGap / 2))
                + j * horizontalGap) - pieceDiameter / 2;
        int fromY = startY + i * verticalGap - pieceDiameter / 2;
        g.fillOval(fromX - 5, fromY - 5, pieceDiameter + 10, pieceDiameter + 10); // bille
        g.setColor(tmp);
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
        yCoordinate = getHeight() / 6 + stringAccent / 2;

        // draw String
        g.drawString(string, xCoordinate, yCoordinate);
        g.setColor(Color.RED);
        g.fillOval(xCoordinate - 20, yCoordinate - 10, 10, 10);
        g.setColor(Color.BLACK);
        g.fillOval((xCoordinate + stringWidth + 10), yCoordinate - 10, 10, 10);
        g.setColor(Color.WHITE);
        if (model.getTurn() % 2 == 1) {
            g.fillRect(xCoordinate, yCoordinate + 5, fm.stringWidth(p1), 5);
        } else {
            g.fillRect(xCoordinate + (stringWidth - fm.stringWidth(p2)), yCoordinate + 5,
                    fm.stringWidth(p2), 5);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        startXExt = startX - borderSpacing;
        startYExt = startY - borderSpacing - borderSpacing / 2;

        // Création des lignes permettant de faire le cadre autour du plateau
        Polygon hexagone = new Polygon();
        hexagone.addPoint(startXExt, startYExt);
        java.awt.Point start = new java.awt.Point(startXExt, startYExt);
        startXExt = startXExt - (boardSize - 1) * (horizontalGap / 2) - borderSpacing;
        startYExt = startYExt + (boardSize - 1) * verticalGap + borderSpacing + borderSpacing / 2;
        hexagone.addPoint(startXExt, startYExt);
        startXExt = startXExt + (boardSize - 1) * (horizontalGap / 2) + borderSpacing;
        startYExt = startYExt + (boardSize - 1) * verticalGap + borderSpacing + borderSpacing / 2;
        hexagone.addPoint(startXExt, startYExt);
        startXExt = startXExt + (boardSize - 1) * (horizontalGap) + 2 * borderSpacing;
        java.awt.Point end = new java.awt.Point(startXExt, startYExt);
        hexagone.addPoint(startXExt, startYExt);
        startXExt = startXExt + (boardSize - 1) * (horizontalGap / 2) + borderSpacing;
        startYExt = startYExt - (boardSize - 1) * verticalGap - borderSpacing - borderSpacing / 2;
        hexagone.addPoint(startXExt, startYExt);
        startXExt = startXExt - (boardSize - 1) * (horizontalGap / 2) - borderSpacing;
        startYExt = startYExt - (boardSize - 1) * verticalGap - borderSpacing - borderSpacing / 2;

        hexagone.addPoint(startXExt, startYExt);
        GradientPaint p = new GradientPaint(start, color1, end, color2, true);
        // g.setColor(new Color(193, 193, 193));
        ((Graphics2D) g).setPaint(p);
        ((Graphics2D) g).fillPolygon(hexagone);
        g.setColor(new Color(0, 0, 0));
        g.drawPolygon(hexagone);
        // lignes horizontales
        for (int k = -(boardSize - 1); k < boardSize; k++) {
            int fromX = startX - (boardSize - 1 - Math.abs(k)) * (horizontalGap / 2);
            int fromY = startY + (boardSize - 1 + k) * verticalGap;
            int toX = (startX + (boardSize - 1) * horizontalGap)
                    + (boardSize - 1 - Math.abs(k)) * (horizontalGap / 2);
            int toY = startY + (boardSize - 1 + k) * verticalGap;
            g.drawLine(fromX, fromY, toX, toY);
        }

        for (int i = 0; i < boardSize; i++) { // diagonales de la ligne du haut
            // diagonale vers la droite
            int fromXDroite = startX + i * horizontalGap;
            int fromYDroite = startY;
            int toXDroite = startX + (2 * boardSize - 2) * (horizontalGap / 2) + i * (horizontalGap / 2);
            int toYDroite = startY + (2 * boardSize - 2 - i) * verticalGap;
            g.drawLine(fromXDroite, fromYDroite, toXDroite, toYDroite);

            // diagonale vers la gauche
            int fromXGauche = startX + i * horizontalGap;
            int fromYGauche = startY;
            int toXGauche = startX - (boardSize - 1) * (horizontalGap / 2) + i * (horizontalGap / 2);
            int toYGauche = startY + (boardSize - 1 + i) * verticalGap;
            g.drawLine(fromXGauche, fromYGauche, toXGauche, toYGauche);
        }

        for (int j = 0; j < boardSize; j++) { // diagonales de la ligne du bas
            // diagonale vers la droite
            int fromXDroite = startX + j * horizontalGap;
            int fromYDroite = startY + ((2 * boardSize) - 2) *
                    verticalGap;
            int toXDroite = startX + (2 * boardSize - 2) * (horizontalGap / 2) + j * (horizontalGap / 2);
            int toYDroite = startY + j * verticalGap;
            g.drawLine(fromXDroite, fromYDroite, toXDroite, toYDroite);

            // diagonale vers la gauche
            int fromXGauche = startX + j * horizontalGap;
            int fromYGauche = startY + ((2 * boardSize) - 2) *
                    verticalGap;
            int toXGauche = startX - (boardSize - 1) * (horizontalGap / 2) + j * (horizontalGap / 2);
            int toYGauche = startY + ((2 * boardSize) - 2) *
                    verticalGap - (boardSize - 1 + j) * verticalGap;
            g.drawLine(fromXGauche, fromYGauche, toXGauche, toYGauche);
        }

        update(g);

        p1 = "Joueur 1";
        p2 = "Joueur 2";
        total = p1 + " : " + model.getScoreWhite() + " | " + model.getScoreBlack() + " : " + p2;
        g.setFont(new Font("Helvetica", Font.PLAIN, 30));
        g.setColor(Color.WHITE);
        drawStringMiddleOfPanel(total, g);
    }

    public void win(int winner) {
        sceneJeu.showWinScreen(winner);
    }
}