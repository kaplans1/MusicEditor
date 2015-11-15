/**
 * Created by natdempk on 11/3/15.
 */

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

import cs3500.music.model.MusicNote;
import cs3500.music.model.MusicPiece;
import cs3500.music.model.Notes;

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
}
