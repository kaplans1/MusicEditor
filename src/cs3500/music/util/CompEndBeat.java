package cs3500.music.util;

import java.util.Comparator;

public class CompEndBeat implements Comparator<MusicNote> {
    /**
     * compares two musical notes by ending beat
     * @param a
     * @param b
     * @return comparison integer of a's ending beat compared to b's
     */
    public int compare(MusicNote a, MusicNote b) {
        return a.endBeat() - b.endBeat();
    }
}
