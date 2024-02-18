package model;

import java.util.LinkedList;
import java.util.List;

import model.Move.Response;

/**
 * Gère une partie d'Abalone côté model.
 */
public class Game {
    private final Settings SETTINGS; // les options de la partie

    private Board board; // le plateau de jeu associé
    
    private Move move; // état du coup courant
    private LinkedList<Move> history; // historique des coups
    private List<Point> selected; // Liste des points sélectionnés

    private int scoreW, scoreB; // score des WHITE et score des BLACK
    private Marble won; // Quelle couleur a gagné. NONE : pas encore de gagnant.


    public Game(Settings settings) {
        this.SETTINGS = settings;
        this.board = new Board(SETTINGS.LENGTH);
        Point.setLength(board.LENGTH);

        move = new Move(board, settings.MAX_MARBLES);
        history = new LinkedList<>();
        selected = new LinkedList<>();

        won = Marble.NONE;
    }

    /** Demande au move courant quoi faire
     * et agit selon la réponse.
     * 
     * @param i coordonnée horizontale 
     * @param j coordonnée verticale
     * @return true si l'état du model a changé */
    public boolean addPoint(int i, int j) {
        Point p = new Point(i, j);
        Response response = move.send(p);
        
        switch (response) {
            case SELECTED :
                selected.add(p);
                break;
            case UNSELECTED :
                selectedRemove(p);
                break;
            case MOVED : 
                this.nextTurn();
                break;
            case SCORED :
                this.score();
                this.nextTurn();
                break;
            default :
                return false;
        }

        System.out.println(turnToString());
        return true;
    }


    public Marble[][] getBoardContent() {
        return board.getContent();
    }
    public List<Point> getSelectedPoints() {
        return selected;
    }
    public int getLength() {
        return board.LENGTH;
    }
    public int getScoreWhite() {
        return scoreW;
    }
    public int getScoreBlack() {
        return scoreB;
    }
    /** @return 0 si aucun vainqueur, 1 si WHITE, 2 si BLACK */
    public int whoWon() {
        return won.ordinal();
    }
    /** @return À quel tour la partie en est.
     * 1 tour = 1 coup d'un seul joueur. */
    public int getTurn() {
        return history.size()+1;
    }


    private String turnToString() {
        return (
            "Tour " + (history.size()+1) + "\n" +
            "Joueur " + move.player().ordinal() + "\n" +
            move.toString() + "\n" +
            "--------------------"
        );
    }


    private void nextTurn() {
        history.push(move);
        move.reset();
        selected.clear();
        System.out.println(board);
    }

    private void score() {
        if (move.player() == Marble.BLACK) {
            ++scoreB;
        } else {
            ++scoreW;
        }
        if (scoreB == SETTINGS.MAX_SCORE) won = Marble.BLACK;
        if (scoreW == SETTINGS.MAX_SCORE) won = Marble.WHITE;
    }

    private void selectedRemove(Point p) {
        for (Point ps : selected) {
            if (ps.i() == p.i() && ps.j() == p.j()) {
                selected.remove(ps);
            }
        }
    }
}
