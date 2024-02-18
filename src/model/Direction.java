package model;

enum Direction {
    UP_LEFT,
    LEFT,
    DOWN_LEFT,
    UP_RIGHT,
    RIGHT,
    DOWN_RIGHT;

    /**
     * Fixe les opposées associées à chaque direction.
     * @return la Direction opposée à la direction courante
     */
    Direction opposite() {
        Direction[] opposite = {
            DOWN_RIGHT,
            RIGHT,
            UP_RIGHT,
            DOWN_LEFT,
            LEFT,
            UP_LEFT
        };

        return opposite[this.ordinal()];
    }

    /** 
     * Renvoie la direction associée à son ordinal {@code n}.
     * {@code n} est supposé inférieur au nombre de valeurs de Direction possibles.
     */
    static Direction value(int n) {
        Direction[] directions = {
            UP_LEFT,
            LEFT,
            DOWN_LEFT,
            UP_RIGHT,
            RIGHT,
            DOWN_RIGHT
        };

        return directions[n];
    }
}
