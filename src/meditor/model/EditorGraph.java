package meditor.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a piece of music with, editing functionality.
 */
public class EditorGraph implements Meditor {
  //Tried an ArrayList<ArrayList<Beat>> - it was a poor choice. This isn't much better, but
  //it's slightly more workable.
  //If two pieces of music exist, nothing is stopping methods from getting called on each piece
  //of music - I think.
  protected ArrayList<Note> mpiece;

  //Creates a blank editor that can be populated with Notes
  public EditorGraph() {
    mpiece = new ArrayList<Note>();
  }

  /**
   * Creates and adds a desired not to music piece, checking for conflicts through helper method.
   *
   * @param beats length of note
   * @param o     octave of note
   * @param p     pitch of note
   * @param start start beat of note
   */
  public void addNote(int beats, int o, String p, int start) {
    Note toAdd = new Note(beats, o, p, start);
    if (this.rangeConflict(toAdd)) {
      throw new IllegalArgumentException("There is a note conflict");
    } else {
      mpiece.add(toAdd);
    }
  }

  /**
   * If note is present, deletes desired note
   *
   * @param o     octave of note to be deleted
   * @param p     pitch of note to be deleted
   * @param start any beat of note to be deleted
   */
  public void delNote(int o, String p, int start) {
    boolean found = false;
    Pitch q = new Pitch(o, p);
    int a = q.getPitchID();
    Note toRemove = new Note(1, o, p, start);
    for (Note x : mpiece) {
      //pitchID is the same
      if ((x.pitch.getPitchID() == a) &&
              //given index is after start, but before end of current note
              (((start >= x.startTime) && (start <= x.startTime + x.beats.size())))) {
        toRemove = x;
        found = true;
      }
    }
    if (!found) {
      throw new IllegalArgumentException("Note wasn't found!");
    } else {
      mpiece.remove(toRemove);
    }
  }

  /**
   * Resizes the desired note to the new size. Checks if desired note to be resized is present,
   * deletes note to be resized, attempts to add new note  of new size. If this works, re-adds and
   * resizes actual note.
   */
  public void ediNote(int newSize, int o, String p, int start) {
    if (newSize > 0) {
      boolean found = false;
      Pitch q = new Pitch(o, p);
      int a = q.getPitchID();
      Note toRemove = new Note(1, o, p, start);
      Note toAdd = new Note(newSize, o, p, start);
      for (Note x : mpiece) {
        //pitchID is the same
        if ((x.pitch.getPitchID() == a) &&
                //given index is after start, but before end of current note
                (((start >= x.startTime) && (start <= x.startTime + x.beats.size())))) {
          toRemove = x;
          found = true;
        }
      }
      if (!found) {
        throw new IllegalArgumentException("Note wasn't found!");
      } else {
        mpiece.remove(toRemove);
      }
      try {
        mpiece.add(toAdd);
      } catch (IllegalArgumentException e) {
        System.out.println("Note enough room for resize!");
        mpiece.add(toRemove);
      }
    }
  }

  /**
   * Returns the ArrayList<Note> - ie the music
   *
   * @return Just returns safe copy of music - used a bit for testing, and seems useful.
   */
  public ArrayList<Note> getGraph() {
    ArrayList<Note> safe = new ArrayList<Note>();
    for (Note n : mpiece) {
      safe.add(n);
    }
    return safe;
  }

  /**
   * Checks if the given note conflicts with any notes in the current piece of music
   *
   * @param one Given note
   * @return true if it conflicts
   */
  protected boolean rangeConflict(Note one) {
    boolean a = false;
    for (Note two : mpiece) {
      if ((one.pitch.getPitchID() == two.pitch.getPitchID()) &&
              (((one.startTime >= two.startTime) && (one.startTime <= two.startTime + two.beats.size())) ||
                      ((one.startTime <= two.startTime) && (one.startTime + two.beats.size() >= two.startTime)))) {
        a = true;
      }
    }
    return a;
  }

  //There are a few minor rendering issues that I haven't been able to fix, but it's close
  public void Render() {
    //I think the sorting makes it faster.
    Collections.sort(mpiece);
    Collections.sort(mpiece, Note.NotePitchIDComparator);
    this.fillBlanks();
    Collections.sort(mpiece);
    Collections.sort(mpiece, Note.NotePitchIDComparator);
    int currPitch = mpiece.get(0).pitch.getPitchID();
    System.out.print(currPitch + ":  ");
    for (Note n : mpiece) {
      if (n.pitch.getPitchID() != currPitch) {
        System.out.println("");
        System.out.print(n.pitch.getPitchID() + ":");
        //fixes accounts for shift from large pitch values
        for (int i = 1; i <= 3 - String.valueOf(n.pitch.getPitchID()).length(); i++) {
          System.out.print(" ");
        }
        currPitch = n.pitch.getPitchID();
      }
      for (Beat b : n.beats) {
        System.out.print(b.toString());
      }

    }
  }

  //Fills the blanks in between notes for rendering with resting notes
  protected void fillBlanks() {
    int currPitch = mpiece.get(0).pitch.getPitchID();
    int prevStart = 0;

    ArrayList<Note> filler = new ArrayList<Note>();
    for (Note n : mpiece) {
      int currStart = n.startTime;
      if (n.pitch.getPitchID() != currPitch) {
        currPitch = n.pitch.getPitchID();
        prevStart = 0;
      }
      if ((prevStart != currStart) && (currStart - prevStart - 1 > 0)) {
        //Re-makes string for harmonic from pitch ID -> can be used for rendering, but
        //decided to go with numbers for simplicity
        Note fill = (new Note(currStart - prevStart - 1, n.pitch.getOct(),
                n.pitch.getPitchesClassic().get(currPitch % 12), prevStart));
        fill.makeBlank(fill);
        filler.add(fill);
        prevStart = currStart + mpiece.get(0).beats.size() - 1;
      }
    }
    mpiece.addAll(filler);
  }

}




