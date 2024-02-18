package model;

/**
 * Définie les options de jeu d'une partie.
 * 
 * Pour instancier cette classe :
 * 
 * new Settings.SettingsBuilder(<paramètres requis>)
 * .set<paramètre optionnel 1>(<valeur>)
 * .set<paramètre optionnel 2>(<valeur>)
 * [...]
 * .set<paramètre optionnel n>(<valeur>)
 * .build()
 * 
 * Il FAUT mettre le .build() à la fin.
 * Les paramètres optionnels sont à mettre seulement si on veut modifier leur
 * valeur par défaut.
 */
public class Settings {
    // required
    /** Taille du plateau. Valeur par défaut : 4. */
    public final int LENGTH;

    // optional
    /** Score à atteindre pour gagner une partie. 
     * Valeur par défaut : LENGTH+1. */
    public final int MAX_SCORE;
    /** Nombre de billes max que l'on peut déplacer lors d'un coup. 
     * Valeur par défaut : 3. */
    public final int MAX_MARBLES;

    public final Marble FIRST_COLOR;

    private Settings(SettingsBuilder builder) {
        this.LENGTH = builder.length;
        this.MAX_SCORE = builder.maxScore;
        this.MAX_MARBLES = builder.maxMarbles;
        this.FIRST_COLOR = builder.firstColor;
    }

    /**
     * builder de Settings. Sert à décider quelles options on souhaite modifier,
     * sans avoir à utiliser un constructeur super long.
     */
    public static class SettingsBuilder {
        // required
        private int length;

        // optional
        public int maxScore;
        public int maxMarbles;
        public Marble firstColor; // quelle couleur joue en premier

        /** Valeurs par défaut */
        public SettingsBuilder() {
            this.length = 4;
            this.maxScore = length + 1;
            this.maxMarbles = 3;
            this.firstColor = Marble.WHITE;
        }

        public SettingsBuilder set_length(int length) {
            this.length = length;
            return this;
        }
        public SettingsBuilder set_maxScore(int maxScore) {
            this.maxScore = maxScore;
            return this;
        }
        public SettingsBuilder set_maxMarbles(int maxMarbles) {
            this.maxMarbles = maxMarbles;
            return this;
        }
        public SettingsBuilder set_firstColor(Marble firstColor) {
            this.firstColor = firstColor;
            return this;
        }

        public Settings build() {
            return new Settings(this);
        }
    }
}
