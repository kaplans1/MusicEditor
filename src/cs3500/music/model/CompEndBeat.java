package cs3500.music.model;

import java.util.Comparator;

public class CompEndBeat implements Comparator<MusicNote> {
    /**
     * compares two musical notes by ending beat
     * @param a
     * @param b
     * @return comparison integer of a's ending beat compared to b's
     */
    public int compare(MusicNote a, MusicNote b) {
        return a.getEndBeat() - b.getEndBeat();
    }
}
