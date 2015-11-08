package cs3500.music.util;

import java.util.Comparator;

public class CompStartBeat implements Comparator<MusicNote> {
    /**
     * compares two musical notes by starting beat
     * @param a
     * @param b
     * @return comparison integer of a's first beat compared to b's
     */
    public int compare(MusicNote a, MusicNote b) {
        return a.startBeat - b.startBeat;
    }
}
