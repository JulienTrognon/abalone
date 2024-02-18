package model;

import model.Board.Zone;

public class Point {
    private int i, j;
    private static int LENGTH;


    public final static int[][] TOP = new int[][] {
        { -1, -1 }, { 0, -1 }, { +1,  0 },
        { -1,  0 }, { 0, +1 }, { +1, +1 }
    };
    public final static int[][] MID = new int[][] {
        { -1, -1 }, { 0, -1 }, { +1, -1 },
        { -1,  0 }, { 0, +1 }, { +1,  0 }
    };
    public final static int[][] BOT = new int[][] {
        { -1,  0 }, { 0, -1 }, { +1, -1 },
        { -1, +1 }, { 0, +1 }, { +1,  0 }
    };


    public Point(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int i() {
        return i;
    }
    public int j() {
        return j;
    }

    @Override
    public String toString() {
        return "("+i+","+j+")";
    }

    static void setLength(int length) {
        Point.LENGTH = length;
    }

    boolean equals(Point p) {
        return (this.i == p.i && this.j == p.j);
    }

    Point next(Direction dir) {
        return (new Point(i + vect(dir)[0], j + vect(dir)[1]));
    }

    Direction dir(Point p) {
        int[][] matrice = this.matrice();

        int i_offset = p.i - this.i;
        int j_offset = p.j - this.j;

        for (int i = 0; i < matrice.length; i++) {
            if (i_offset == matrice[i][0] && j_offset == matrice[i][1]) {
                return Direction.value(i); // la direction associée aux coords correspondantes de la matrice
            }
        }

        System.out.println("Les points comparés ne sont pas adjacents");
        return null;
    }

    boolean isAdj(Point p) {
        int[][] matrice = this.matrice();

        int i_offset = p.i - this.i;
        int j_offset = p.j - this.j;

        for (int i = 0; i < matrice.length; i++) {
            if (i_offset == matrice[i][0] && j_offset == matrice[i][1])
                return true;
        }

        return false;
    }

    private Zone zone() {
        if (i < LENGTH - 1)
            return Zone.TOP;
        if (i == LENGTH - 1)
            return Zone.MID;
        else
            return Zone.BOT;
    }
    private int[][] matrice() {
        Zone z = zone();
        if (z == Zone.TOP)
            return TOP;
        if (z == Zone.MID)
            return MID;
        /* if (z == Zone.BOT) */ 
            return BOT;
    }
    private int[] vect(Direction dir) {
        return matrice()[dir.ordinal()];
    }
}
