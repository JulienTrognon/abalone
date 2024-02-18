package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import model.Game;
import view.board.BoardVue;

public class Control extends MouseAdapter {
    private int horizontalGap, verticalGap, boardSize, pieceDiameter, startX, startY;
    private Game model;
    private BoardVue view;

    public Control(Game model) {
        this.model = model;
    }

    private void update() {
        view.repaint();
    }

    public void setView(BoardVue vue) {
        this.view = vue;
    }

    public void setVariables(int horizontalGap, int verticalGap, int boardSize, int pieceDiameter, int startX,
            int startY) {
        this.horizontalGap = horizontalGap;
        this.verticalGap = verticalGap;
        this.boardSize = boardSize;
        this.pieceDiameter = pieceDiameter;
        this.startX = startX;
        this.startY = startY;
    }

    /** Convertit des coordonnées en pixels en des coordonnnées de point
     * que le model comprend. */
    private int[] convert(int x, int y) {
        int i = 0;
        int j = 0;
        if (((y - startY) % verticalGap) > verticalGap / 2) {
            i = ((y - startY) / verticalGap) + 1;
        } else {
            i = ((y - startY) / verticalGap);
        }
        int fromX = startX - (boardSize - 1 - Math.abs(i - (boardSize - 1))) *
                (horizontalGap / 2);
        if (((x - fromX) % horizontalGap) > horizontalGap / 2) {
            j = (x - fromX) / horizontalGap + 1;
        } else {
            j = (x - fromX) / horizontalGap;
        }
        boolean betweenTwoLines = ((y - startY) % verticalGap) > pieceDiameter / 2
                && ((y - startY) % verticalGap) < verticalGap - pieceDiameter / 2;
        boolean betweenTwoColumns = false;
        if (i % 2 == 0) {
            if ((((x - fromX) % horizontalGap) > pieceDiameter / 2)
                    && (((x - fromX) % horizontalGap) < horizontalGap - pieceDiameter / 2)) {
                betweenTwoColumns = true;
            }
        } else {
            if ((((x - fromX) % horizontalGap) > pieceDiameter / 2)
                    && (((x - fromX) % horizontalGap) < horizontalGap
                            - pieceDiameter / 2)) {
                betweenTwoColumns = true;
            }
        }
        if (betweenTwoLines || betweenTwoColumns) {
            return new int[] {-1, -1};
        } else {
            return new int[] {i, j};
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int[] boardCoords = convert(e.getX(), e.getY());
        if (boardCoords[0] == -1 || boardCoords[1] == -1) {
            return;
        }
        boolean needUpdate = model.addPoint(boardCoords[0], boardCoords[1]);

        int winner = model.whoWon();
        if (winner != 0) {
            System.out.println("winner : Joueur "+winner);
            view.win(winner);
        }
        if (needUpdate) this.update();
    }
}
