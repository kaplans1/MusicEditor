/**
 * Created by natdempk on 11/3/15.
 */

import org.junit.Test;

import cs3500.music.util.MusicNote;
import cs3500.music.util.MusicPiece;
import cs3500.music.util.Notes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class MusicPieceTest {

    MusicPiece mp = new MusicPiece();

    @Test
    public void testMusicPiece() {
        // tests that a basic music piece will be able to add notes
        // and render some output to the console
        MusicNote f = new MusicNote(Notes.F, 2, 2, 2, true, false);
        MusicNote g = new MusicNote(Notes.G, 4, 4, 3, false, false);
        MusicNote g2 = new MusicNote(Notes.G, 9, 4, 3, false, false);
        mp.addNote(g);
        mp.addNote(g2);
        mp.addNote(f);
        mp.render();
    }
}
