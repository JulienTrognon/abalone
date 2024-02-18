package model;

public enum Marble {
    NONE,
    WHITE,
    BLACK;

    @Override
    public String toString() {
        if (this == WHITE)
            return "W";
        if (this == BLACK)
                return "B";
        return "-";
    }

    /**
     * Fixe les opposées associées à chaque Piece.
     * Par convention l'opposé de NONE est NONE.
     * 
     * @return la {@link Marble} opposée à la {@link Marble} courante.
     */
    Marble opposite() {
        Marble[] opposite = new Marble[] { NONE, BLACK, WHITE };
        return opposite[this.ordinal()];
    }
}
