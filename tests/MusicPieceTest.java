/**
 * Created by natdempk on 11/3/15.
 */

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import cs3500.music.model.MusicNote;
import cs3500.music.model.MusicPiece;
import cs3500.music.model.Notes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class MusicPieceTest {

    MusicPiece mp = new MusicPiece();

    @Test
    public void testMusicPiece() {
        // tests that a basic music piece will be able to add notes
        // and render some output to the console
        MusicNote f = new MusicNote(Notes.F, 2, 2, 2, true, false, 1, 1);
        MusicNote a = new MusicNote(Notes.A, 2, 2, 2, true, false, 1, 1);
        MusicNote b = new MusicNote(Notes.B, 2, 2, 2, true, false, 1, 1);
        MusicNote g = new MusicNote(Notes.G, 4, 4, 3, false, false, 1, 1);
        MusicNote g2 = new MusicNote(Notes.G, 9, 4, 3, false, false, 1, 1);
        mp.addNote(g);
        mp.addNote(g2);
        mp.addNote(f);
        mp.addNote(a);
        mp.addNote(b);

        assertEquals(mp.getNotesStartingOnBeat(2), new ArrayList<MusicNote>(Arrays.asList(f, a, b)));
        mp.deleteNote(b);
        assertEquals(mp.getNotesStartingOnBeat(2), new ArrayList<MusicNote>(Arrays.asList(f, a)));
        //mp.render();
    }
}
