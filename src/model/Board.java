package model;

import model.Move.MoveType;

class Board {
    private Marble[][] board;
    
    final int LENGTH;
    final int HEIGHT;

    /** Zones possibles du board. */
    static enum Zone {
        TOP, MID, BOT
    }

    Board(int length) {
        this.LENGTH = length;
        this.HEIGHT = (2 * LENGTH - 1);

        initGrid();
        initColors();
    }


    @Override
    public String toString() {
        String top = "";
        String mid = "";
        String bot = "";

        for (int row = 0; row < LENGTH - 1; row++) {
            String topTMP = " ".repeat(LENGTH - 1 - row);
            String botTMP = " ".repeat(LENGTH - 1 - row);

            for (int col = 0; col < board[row].length; col++) {
                topTMP += board[row][col].toString() + ' ';
                botTMP += board[HEIGHT - 1 - row][col].toString() + ' ';
            }

            top = top + topTMP + "\n";
            bot = botTMP + "\n" + bot;
        }

        for (int col = 0; col < HEIGHT; col++) {
            mid += board[LENGTH - 1][col].toString() + ' ';
        }
        mid += "\n";

        return top + mid + bot;
    }


    /** Initialise comme vides les {@link Marble} du {@link Board}. */
    private void initGrid() {
        board = new Marble[HEIGHT][0];

        for (int row = 0; row < LENGTH - 1; row++) {
            board[row] = new Marble[LENGTH + row]; // tailles des lignes du haut (Zone.TOP)
            board[HEIGHT - 1 - row] = new Marble[LENGTH + row]; // taille des lignes du bas (Zone.BOT)

            for (int col = 0; col < board[row].length; col++) {
                board[row][col] = Marble.NONE;
                board[HEIGHT - 1 - row][col] = Marble.NONE;
            }
        }

        // taille de la ligne du milieu (= nombre de lignes du plateau)
        board[LENGTH - 1] = new Marble[HEIGHT];
        for (int col = 0; col < HEIGHT; col++) {
            board[LENGTH - 1][col] = Marble.NONE;
        }
    }
    /** Place les {@link Marble}s sur le {@link Board}. */
    private void initColors() {
        int pawnsIndex = (2 * LENGTH) / 3 - 1;

        for (int row = 0; row < pawnsIndex; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col] = Marble.WHITE;
                board[HEIGHT - 1 - row][col] = Marble.BLACK;
            }
        }

        for (int col = 2; col < board[pawnsIndex].length - 2; col++) {
            board[pawnsIndex][col] = Marble.WHITE;
            board[HEIGHT - 1 - pawnsIndex][col] = Marble.BLACK;
        }
    }

    /** @return liste dans l'ordre du contenu du Board courant */
    Marble[][] getContent() {
        return board;
    }


    /** @return couleur de la bille au point p, supposé onBoard */
    Marble color(Point p) {
        return board[p.i()][p.j()];
    }

    /** @return true ssi p correspond à des coordonées exisantes du plateau. */
    boolean onBoard(Point p) {
        return (p.i() >= 0 && p.i() < HEIGHT) && (p.j() >= 0 && p.j() < board[p.i()].length);
    }

    /** Place une bille de couleur color à la case de point p, supposé onBoard(). */
    private void place(Point p, Marble color) {
        board[p.i()][p.j()] = color;
    }

    /** Retire la bille au point p et la renvoie (remplace la couleur 
     * de la box visée par NONE). */
    private Marble take(Point p) {
        Marble marble = color(p);
        board[p.i()][p.j()] = Marble.NONE;
        return marble;
    }

    /** Déplace la bille au point p dans la direction dir,
     * en écrasant le contenu de la case ciblée. */
    private void moveMarble(Point p, Direction dir) {
        Marble taken = take(p);
        place(p.next(dir), taken);
        System.out.println(p.next(dir));
    }
    /** Déplace la bille au point origin sur la case au point target,
     * en écrasant le contenu de la case ciblée. */
    private void moveMarble(Point origin, Point target) {
        Marble taken = take(origin);
        place(target, taken);
    }
    

    /** bouge les pièces du plateau selon les instructions de this. 
     * @return true s'il y a eu une éjection de bille */
    boolean move(Move m) {
        Point origin = m.origin();
        Point last = m.getLast();
        Direction dirMove = m.dirMove();
        
        MoveType moveType = m.moveType();
        switch (moveType) {
            case UNI -> 
                moveMarble(origin, dirMove);
            case AHEAD ->
                moveMarble(origin, last.next(dirMove));
            case LATERAL ->
                moveLateral(origin, m.nbMarbles(), m.dirMarbles(), dirMove);
            case PUSH -> {
                boolean ejected = moveOppMarbles(last.next(dirMove), m.nbOppMarb(), dirMove);
                moveMarble(origin, last.next(dirMove));
                return ejected;
            }
        }
        return false;
    }

    /** Déplace les billes du joueur courant dans la direction dir. */
    private void moveLateral(
        Point current, int nbMarbles, Direction dirMarbles, Direction dirMove
    ) {
        for (int i = 0; i < nbMarbles; i++) {
            moveMarble(current, dirMove);
            current = current.next(dirMarbles);
        }
    }
    /** Déplace les billes adverses dans la direction dir.
     * Gère l'éjection éventuelle.
     * @return true ssi une bille a été éjectée.
     */
    private boolean moveOppMarbles(Point oppOrigin, int nbOppMarb, Direction dir) {
        // retire la bille et stocke sa couleur
        Marble taken = take(oppOrigin);

        // on pointe la case suivant la dernière bille adverse
        for (int i = 1 ; i < nbOppMarb+1 ; i++) {
            oppOrigin = oppOrigin.next(dir);
        }
        
        // si la case n'est pas sur le plateau, éjection
        if (!onBoard(oppOrigin)) return true;
        
        // si la case est sur le plateau, on pose
        // la bille stockée sur cette case, pas d'éjection
        place(oppOrigin, taken);
        return false;
    }
}