/**
 * Created by natdempk on 11/3/15.
 */

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

import javax.sound.midi.MidiUnavailableException;

import cs3500.music.mocks.MockLogger;
import cs3500.music.mocks.MockMidiSynthesizer;
import cs3500.music.model.MusicNote;
import cs3500.music.model.MusicPiece;
import cs3500.music.model.Notes;
import cs3500.music.view.MidiViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class MusicPieceTest {

  MusicPiece mp = new MusicPiece();
  MusicNote f = new MusicNote(Notes.F, 2, 2, 2, true, false, 1, 1);
  MusicNote a = new MusicNote(Notes.A, 2, 2, 2, true, false, 1, 1);
  MusicNote b = new MusicNote(Notes.B, 2, 2, 2, true, false, 1, 1);
  MusicNote g = new MusicNote(Notes.G, 4, 4, 3, false, false, 1, 1);
  MusicNote g2 = new MusicNote(Notes.G, 9, 4, 3, false, false, 1, 1);
  TreeMap currPiece = new TreeMap<>();
  ArrayList<MusicNote> startNotes = new ArrayList<MusicNote>();
  @Test
  public void testMusicPiece() {
    // tests that a basic music piece will be able to add notes
    // and render some output to the console

    mp.addNote(g);
    mp.addNote(g2);
    mp.addNote(f);
    mp.addNote(a);
    mp.addNote(b);

    assertEquals(mp.getNotesStartingOnBeat(2), new ArrayList<MusicNote>(Arrays.asList(f, a, b)));
    mp.deleteNote(b);
    assertEquals(mp.getNotesStartingOnBeat(2), new ArrayList<MusicNote>(Arrays.asList(f, a)));
    //mp.render();
    mp.deleteNote(f);
    mp.deleteNote(a);
    mp.deleteNote(g2);
  }

  @Test
  public void getAllnotes(){
    //can I get all the notes
    mp.addNote(g2);
    startNotes.add(g2);
    currPiece.put(g2.getStartBeat(), startNotes);
    assertEquals(mp.getAllNotes(), currPiece);
  }

  @Test
  public void getLastBeat(){
    //what's the last beat
    mp.addNote(g2);
    assertEquals(13, mp.getLastBeat());
  }

  @Test
  public void getPitchID(){
    //what's the last beat
    mp.addNote(g2);
    assertEquals(1, mp.getAllPitchIds().size());
    mp.addNote(g2);
    mp.addNote(g2);
    assertEquals(1, mp.getAllPitchIds().size());
    mp.addNote(a);
    mp.addNote(b);
    assertEquals(3, mp.getAllPitchIds().size());

  }

  @Test
  public void testMockMIDI() {
    mp.addNote(g2);
    MidiViewImpl impl = new MidiViewImpl(this.mp, new MockMidiSynthesizer());
    MockLogger logger = MockLogger.getInstance();
    try {
      impl.initialize();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertEquals("Created MockMidiChannel\n" +
            "Created MockMidiChannel\n" +
            "Created MockMidiChannel\n" +
            "Created MockMidiChannel\n" +
            "Created MockMidiChannel\n" +
            "Created MockMidiChannel\n" +
            "Created MockMidiChannel\n" +
            "Created MockMidiChannel\n" +
            "Created MockMidiChannel\n" +
            "Created MockMidiChannel\n" +
            "Created MockMidiSynthesizer\n" +
            "Opened MockMidiSynthesizer\n" +
            "Opened MockMidiSynthesizer\n" +
            "Channel received noteOn: 55 1\n" +
            "Channel received noteOff: 55 1\n" +
            "Closed MockMidiSynthesizer\n", logger.getLog());
  }
}
