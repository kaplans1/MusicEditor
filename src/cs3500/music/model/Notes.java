package cs3500.music.model;


/**
 * Created by natdempk on 11/3/15.
 */

/**
 * enumerable to represent notes as numeric values
 */
public enum Notes {
    C(0), D(2), E(4), F(5), G(7), A(9), B(11);

    private final int noteValue;
    Notes(int noteValue) {
        this.noteValue = noteValue;
    }

    /**
     * gets integer value of the note on the scale
     * @return integer value of the note
     */
    public int getNoteValue() {
        return this.noteValue;
    }
}
