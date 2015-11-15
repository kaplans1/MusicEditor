/**
 * Created by natdempk on 11/3/15.
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class MusicPieceTest {

  //Testing console output - stackoverflow 1119559
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  @Before
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
  }
  @After
  public void cleanUpStreams() {
    System.setOut(null);
  }

  MusicPiece mp = new MusicPiece();
  MusicNote f = new MusicNote(Notes.F, 2, 2, 2, true, false, 1, 1);
  MusicNote a = new MusicNote(Notes.A, 2, 2, 2, true, false, 1, 1);
  MusicNote c = new MusicNote(Notes.B, 2, 2, 2, true, false, 1, 1);
  MusicNote b = new MusicNote(Notes.B, 2, 2, 2, true, false, 1, 1);
  MusicNote g = new MusicNote(Notes.G, 4, 4, 3, false, false, 1, 1);
  MusicNote g2 = new MusicNote(Notes.G, 9, 4, 3, false, false, 1, 1);
  MusicNote d = new MusicNote(Notes.D, 32, 4, 2, true, false, 0, 80);
  TreeMap currPiece = new TreeMap<>();
  ArrayList<MusicNote> startNotes = new ArrayList<MusicNote>();

  @Test (expected = IllegalArgumentException.class)
  public void testStartBeat(){
    MusicNote f = new MusicNote(Notes.F, -2, 2, 2, true, false, 1, 1);
  }
  @Test (expected = IllegalArgumentException.class)
  public void testBeats(){
    MusicNote f = new MusicNote(Notes.F, 2, -2, 2, true, false, 1, 1);
  }
  @Test (expected = IllegalArgumentException.class)
  public void testInstrument(){
    MusicNote f = new MusicNote(Notes.F, 2, 2, 2, true, false, -1, 1);
  }
  @Test (expected = IllegalArgumentException.class)
  public void testVolume(){
    MusicNote f = new MusicNote(Notes.F, 2, 2, 2, true, false, -1, 1);
  }
  @Test (expected = IllegalArgumentException.class)
  public void testVolume2(){
    MusicNote f = new MusicNote(Notes.F, 2, 2, 2, true, false, 3, 128);
  }
  @Test (expected = IllegalArgumentException.class)
  public void testOctave(){
    MusicNote f = new MusicNote(Notes.F, 2, 2, -2, true, false, 2, 1);
  }
  @Test (expected = IllegalArgumentException.class)
  public void testOctave2(){
    MusicNote f = new MusicNote(Notes.F, 2, 2, 10, true, false, 2, 1);
  }
  @Test (expected = IllegalArgumentException.class)
  public void sharpAndFlat(){
    MusicNote f = new MusicNote(Notes.F, 2, 2, 10, true, true, 2, 1);
  }
  @Test
  public void absolutePitch(){
    MusicNote f = new MusicNote(Notes.F, 2, 2, 9, false, false, 2, 1);
    assertEquals(125, f.getPitchID());
  }
  @Test
  public void absolutePitch1(){
    MusicNote f = new MusicNote(Notes.F, 2, 2, 9, true, false, 2, 1);
    assertEquals(126, f.getPitchID());
  }
  @Test
  public void absolutePitch2(){
    MusicNote f = new MusicNote(Notes.F, 2, 2, 9, false, true, 2, 1);
    assertEquals(124, f.getPitchID());
  }
  @Test
  public void pitchToString(){
    MusicNote f = new MusicNote(Notes.F, 2, 2, 9, false, true, 2, 1);
    assertEquals("D1", f.pitchToString(14));
  }
  @Test
  public void pitchToString1(){
    MusicNote f = new MusicNote(Notes.F, 2, 2, 9, false, true, 2, 1);
    assertEquals("C0", f.pitchToString(0));
  }
  @Test (expected = IllegalArgumentException.class)
     public void pitchToString2(){
    MusicNote f = new MusicNote(Notes.F, 2, 2, 9, false, true, 2, 1);
    assertEquals("C0", f.pitchToString(-1));
  }
  @Test
  public void noteEquals(){
    MusicNote f = new MusicNote(Notes.F, 2, 2, 9, false, true, 2, 1);
    MusicNote f1 = new MusicNote(Notes.F, 2, 2, 9, false, true, 2, 1);
    assertTrue(f.equals(f1));
  }
  @Test
  public void noteEquals2(){
    MusicNote f = new MusicNote(Notes.F, 2, 2, 9, false, true, 2, 1);
    MusicNote f1 = new MusicNote(Notes.G, 2, 2, 9, false, true, 2, 1);
    assertFalse(f.equals(f1));
  }

  @Test
  public void musicConstruct(){
    MusicPiece mp = new MusicPiece();
    assertEquals(4, mp.getBPM());
    assertEquals(200000, mp.getTempo());
  }

  @Test  (expected = IllegalArgumentException.class)
  public void setTempo(){
    MusicPiece mp = new MusicPiece();
    mp.setTempo(-1);
  }

  @Test
  public void setTempo2(){
    MusicPiece mp = new MusicPiece();
    mp.setTempo(5);
    assertEquals(5, mp.getTempo());
  }

  @Test
  public void getNotesStartingOnBeat(){
    startNotes.add(f);
    startNotes.add(a);
    startNotes.add(b);
    startNotes.add(g);
    assertNull(mp.getNotesStartingOnBeat(0));
  }

  @Test
  public void addNote(){
    mp.addNote(f);
    mp.addNote(a);
    mp.addNote(c);
    mp.addNote(g);
    assertEquals("F#3", f.pitchToString(mp.getNotesStartingOnBeat(2).get(0).getPitchID()));
    assertEquals("A#3", f.pitchToString(mp.getNotesStartingOnBeat(2).get(1).getPitchID()));
    assertEquals("C4", f.pitchToString(mp.getNotesStartingOnBeat(2).get(2).getPitchID()));
  }

  @Test  (expected = IllegalArgumentException.class)
  public void delNote(){
    mp.addNote(f);
    mp.addNote(c);
    mp.deleteNote(a);
  }

  @Test
  public void delNote2(){
    mp.addNote(f);
    mp.addNote(a);
    mp.addNote(c);
    mp.deleteNote(a);
    assertEquals(mp.getNotesStartingOnBeat(2), new ArrayList<MusicNote>(Arrays.asList(f, c)));
  }

  @Test (expected = IllegalArgumentException.class)
  public void lastBeat(){
    mp.getLastBeat();
  }

  @Test (expected = IllegalArgumentException.class)
  public void lastBeat2(){
    mp.addNote(f);
    mp.deleteNote(f);
    mp.getLastBeat();
  }

  @Test
  public void render1(){
    assertEquals("", mp.render());
  }

  @Test
  public void render2(){
    mp.addNote(g);
    mp.addNote(g2);
    mp.addNote(f);
    mp.addNote(a);
    mp.addNote(b);
    mp.addNote(d);
    assertEquals("   D#3  F#3  A#3  C4   G4   \n" +
            "0                           \n" +
            "1                           \n" +
            "2       X    X    X         \n" +
            "3       |    |    |         \n" +
            "4                      X    \n" +
            "5                      |    \n" +
            "6                      |    \n" +
            "7                      |    \n" +
            "8                           \n" +
            "9                      X    \n" +
            "10                     |    \n" +
            "11                     |    \n" +
            "12                     |    \n" +
            "13                          \n" +
            "14                          \n" +
            "15                          \n" +
            "16                          \n" +
            "17                          \n" +
            "18                          \n" +
            "19                          \n" +
            "20                          \n" +
            "21                          \n" +
            "22                          \n" +
            "23                          \n" +
            "24                          \n" +
            "25                          \n" +
            "26                          \n" +
            "27                          \n" +
            "28                          \n" +
            "29                          \n" +
            "30                          \n" +
            "31                          \n" +
            "32 X                        \n" +
            "33 |                        \n" +
            "34 |                        \n" +
            "35 |                        \n", mp.render());
  }

  @Test ()
  public void render3(){
    mp.addNote(f);
    mp.addNote(a);
    mp.addNote(c);
    mp.deleteNote(f);
    mp.deleteNote(a);
    mp.deleteNote(c);
    assertEquals("", mp.render());
  }



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
