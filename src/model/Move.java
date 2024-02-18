package model;

class Move {
    private Board board;
    private Marble player;

    private final int MAX_MARBLES;

    private Point origin;
    private int nbMarbles;
    private Direction dirMarbles;
    private Direction dirMove;

    private MoveType moveType;
    private int nbOppMarb;

    private Point tested;

    static enum MoveType {
        UNI, AHEAD, LATERAL, PUSH;
    }

    static enum Response {
        UNCHANGED, UNSELECTED, SELECTED, MOVED, SCORED;
    }

    Move(Board board, int maxMarbles) {
        this.board = board;
        this.MAX_MARBLES = maxMarbles;
        this.player = Marble.WHITE;
    }

    Marble player() {
        return player;
    }

    Point origin() {
        return origin;
    }
    int nbMarbles() {
        return nbMarbles;
    }
    Direction dirMarbles() {
        return dirMarbles;
    }
    Direction dirMove() {
        return dirMove;
    }
    MoveType moveType() {
        return moveType;
    }
    int nbOppMarb() {
        return nbOppMarb;
    }
    /** Renvoie la bille sélectionnée la plus éloignée de  */
    Point getLast() {
        if (origin == null)
            return null;

        Point last = origin;
        for (int i = 1; i < nbMarbles; i++) {
            last = last.next(dirMarbles);
        }

        return last;
    }

    @Override
    public String toString() {
        String s = "[Move]{\n";

        if (origin != null) {
            s += "  origin=" + origin.toString() + "\n";
        }
        if (dirMarbles != null) {
            s += "  dirMarbles=" + dirMarbles + "\n";
        }
        s += "  nbMarbles=" + nbMarbles + "\n";
        if (dirMove != null) {
            s += "  dirMove=" + dirMove + "\n";
        }
        s += "}";

        return s;
    }

    /** Réinitialise le move et change de joueur. */
    void reset() {
        unselectAll();
        this.player = player.opposite();
    }
    /** Remet la sélection à zéro */
    private void unselectAll() {
        origin = null;
        nbMarbles = 0;
        dirMarbles = null;
        dirMove = null;
        tested = null;
        moveType = null;
        nbOppMarb = 0;
    }

    /**
     * Effectue un traitement de p.
     * Si p existe déjà dans this, le déselectionne.
     * Sinon ajoute p ici. Et si p indique une demande
     * de déplacement des billes, déplace les billes selon p.
     * 
     * @return la réponse associée au résultat du traitment
     */
    Response send(Point p) {
        if (!board.onBoard(p)) {
            return Response.UNSELECTED;
        }

        tested = p;

        boolean didUnselect = unselect();
        if (didUnselect)
            return Response.UNSELECTED;

        boolean didSelect = select();
        if (!didSelect)
            return Response.UNCHANGED;

        if (dirMove == null)
            return Response.SELECTED;
        boolean ejected = this.move();
        if (!ejected)
            return Response.MOVED;
        return Response.SCORED;
    }

    /** Tente d'ajouter le point tested au move.
     * @return true si le point a été ajouté. */
    private boolean select() {
        if (origin == null) {
            return selectOrigin();
        }
        return selectNext();
    }

    /** Enlève p du move, s'il est dedans.
     * @param p le point à enlever.
     * @return true si le plateau a changé d'état */
    private boolean unselect() {
        if (nbMarbles == 0)
            return false;

        if (tested.equals(origin)) {
            unselectOrigin();
            return true;
        }

        if (nbMarbles == 1) {
            return false;
        }

        Point last = origin;
        for (int i = 1; i < nbMarbles; i++) {
            last = last.next(dirMarbles);
        }

        if (tested.equals(last)) {
            decrementMarbles();
            return true;
        }

        return false;
    }

/** Bouge les pièces du plateau selon les instructions de this. 
     * @return true s'il y a eu une éjection de bille */
    private boolean move() {
        if (nbMarbles == 0) return false;
        return board.move(this);
    }


    /** Sélectionne la 1e bille du Move si valide.
     * @return true ssi la sélection s'est faite avec succès. */
    private boolean selectOrigin() {
        if (color(tested) == player) {
            origin = tested;
            nbMarbles = 1;
            return true;
        }
        return false;
    }
    /** Sélectionne le point suivant s'il est valide.
     * @return true ssi la sélection s'est faite avec succès. */
    private boolean selectNext() {
        Point last = manageAdj();
        if (last == null) return false;

        Marble colorT = color(tested);
        Marble colorL = color(last);

        Direction testDir = last.dir(tested);

        if (colorT == Marble.NONE) {
            return calcMovementOnNone(testDir);
        }
        if (colorT == colorL) {
            // moveType = null;
            if (nbMarbles == MAX_MARBLES) {
                return false;
            }
            return addNextMarble(testDir);
        }
        // donc colorT == colorL.opposite();
        moveType = MoveType.PUSH;
        return calcPush(testDir);
    }
    /** Détermine le type et la direction du déplacement
     * des billes, supposé sur des cases vides.
     * @return true ssi la sélection s'est faite avec succès. */
    private boolean calcMovementOnNone(Direction testDir) {
        if (nbMarbles == 1) {
            moveType = MoveType.UNI;
        } else if (testDir == dirMarbles) {
            moveType = MoveType.AHEAD;
        } else if (calcIsLateralMove(testDir)) {
            moveType = MoveType.LATERAL;
        } else {
            return false;
        }

        dirMove = testDir;
        return true;
    }
    /** Ajoute la bille au Move, si elle est valide.
     * @return true si la bille a bien été ajoutée. */
    private boolean addNextMarble(Direction testDir) {
        if (nbMarbles == 1 || testDir == dirMarbles) {
            dirMarbles = testDir;
            nbMarbles++;
            return true;
        }
        return false;
    }
    /** Détermine si un déplacement qui pousse
     * des billes adverses peut se faire, et qui 
     * l'enregistre si c'est le cas.
     * @return true si le déplacement a été enregistré.
     */
    private boolean calcPush(Direction testDir) {
        nbOppMarb = calcOppMarb(testDir);
        if (nbMarbles == 1 || testDir != dirMarbles ||
        nbMarbles <= nbOppMarb) {
            return false;
        }

        /* si parmi ce qui se trouve après les billes adverses
         * on voit une bille du joueur courant, on ne peut pas push */
        Point tmp = tested.next(testDir);
        for (int i = 0; i < nbOppMarb; i++) {
            if (board.onBoard(tmp) && color(tmp) == player) {
                return false;
            }
            tmp = tested.next(testDir);
        }

        dirMove = dirMarbles;
        return true;
    }
    /** Détermine si le point testé est adjacent à l'origine
     * ou à la dernière bille. Inverse la direction de la
     * sélection s'il est adjacent à l'origine.
     * @return le point adjacent au point testé.
     */
    private Point manageAdj() {
        Point last = getLast();

        boolean isAdjLast = tested.isAdj(last);
        if (isAdjLast) {
            return last;
        }

        boolean isAdjOrigin = tested.isAdj(origin);
        if (isAdjOrigin) {
            // swap origin and last
            Point swap = origin;
            origin = last;
            last = swap;
            // invert marbles direction
            dirMarbles = dirMarbles.opposite();
            
            return last;
        }

        return null;
    }
    /** Détermine si le déplacement latéral est possible.
     * @return true si le déplacement latéral est validé. */
    private boolean calcIsLateralMove(Direction dir) {
        if (origin == null) return false;

        Point tmp = origin;
        for (int i = 1; i <= nbMarbles; i++) {
            if (color(tmp.next(dir)) != Marble.NONE) {
                return false;
            }
            tmp = tmp.next(dirMarbles);
        }
        return true;
    }
    /** Calcule le nombre de billes adverses devant le
     * Point testé, dans la Direction dir.
     * @return nombre de billes adverses.
     */
    private int calcOppMarb(Direction dir) {
        nbOppMarb = 1;
        Marble oppColor = board.color(tested);
        Point tmp = tested.next(dir);
        while (board.onBoard(tmp) && board.color(tmp) == oppColor) {
            nbOppMarb++;
            tmp = tmp.next(dir);
        }
        return nbOppMarb;
    }


    /** Enlève l'origine du Move. Met le point suivant à la place s'il existe.
     * @param p point à enlever, supposé == origin */
    private void unselectOrigin() {
        if (nbMarbles == 1)
            origin = null;
        else
            origin = origin.next(dirMarbles);
        decrementMarbles();
    }

    /** Enlève une bille autre que l'origine du Move. S'il reste
     * une seule bille après l'opération, dirMarbles passe à null. */
    private void decrementMarbles() {
        nbMarbles--;
        if (nbMarbles <= 1)
            dirMarbles = null;
    }

    /** @return la couleur de la bille au point p. */
    private Marble color(Point p) {
        return board.color(p);
    }
}
