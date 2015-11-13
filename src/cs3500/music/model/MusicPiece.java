package cs3500.music.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

import cs3500.music.util.CompositionBuilder;


/**
 * Created by natdempk on 11/3/15.
 */

public class MusicPiece implements MusicPieceInterface {
  //ArrayList<MusicNote> notes;

  //ArrayList<Integer> pitchIds;
  TreeSet<Integer> pitchIds;

  //all pitches in string format
  ArrayList<String> pitches;

  // mapping start time to length
  static TreeMap<Integer, ArrayList<MusicNote>> notes;
  int beatsPerMeasure;
  int tempo;

  /**
   * represents a piece of music with a collection of notes
   *
   * @param beatsPerMeasure number of beats in a measure for this piece of music
   */
  MusicPiece(int beatsPerMeasure, int tempo) {

    if (beatsPerMeasure > 0) {
      this.beatsPerMeasure = beatsPerMeasure;
    } else {
      throw new IllegalArgumentException("beatsPerMeasure must be > 0.");
    }

    if (tempo > 0) {
      this.tempo = tempo;
    } else {
      throw new IllegalArgumentException("tempo must be > 0.");
    }

    //this.pitchIds = new ArrayList<>();
    // TODO: add comparator to make this work better
    this.pitchIds = new TreeSet<>();
    this.pitches = new ArrayList<String>();
    this.notes = new TreeMap<>();

    //this.notes = new ArrayList<MusicNote>();
  }

  public MusicPiece() {
    this(4, 100); // default to 4 beats per measure
  }

  public ArrayList<MusicNote> getNotesStartingOnBeat(Integer beat) {
    return this.notes.get(beat);
  }

  @Override
  public TreeMap<Integer, ArrayList<MusicNote>> getAllNotes() {
    return (TreeMap<Integer, ArrayList<MusicNote>>) notes.clone();
  }

  public TreeSet<Integer> getAllPitchIds() {
    return this.pitchIds;
  }

  public ArrayList<String> getAllPitches() {
    //need a way to sort before returning
    return this.pitches;
  }



  /**
   * adds a note to the piece of music
   *
   * @param note note to be added to the music
   */
  public void addNote(MusicNote note) {
    if (note.getNumericNote() > 127) {
      throw new IllegalArgumentException("Note must have a lower pitch between 0 and 127");
    }
    // usersByCountry.computeIfAbsent(user.getCountry(), v -> new ArrayList<>()).add(user);
    ArrayList<MusicNote> startNotes = this.notes.get(note.getStartBeat());
    if (startNotes == null) {
      startNotes = new ArrayList<>();
    }
    // TODO: deduplicate list of notes
    startNotes.add(note);
    this.pitchIds.add(note.getNumericNote());
    if (!pitches.contains(note.noteName())) {
      this.pitches.add(note.noteName());
    }
    this.notes.put(note.getStartBeat(), startNotes);
  }

  public void deleteNote(MusicNote note) {
    ArrayList<MusicNote> startNotes = this.notes.get(note.startBeat);
    if (startNotes != null) {
      startNotes.remove(note);
    }
  }

  public int getBPM(){
    return this.beatsPerMeasure;
  }


  //gets last beat of piece
  public int getlastBeat() {
    int last = 0;
    int y = notes.lastKey();
    for (int i = 0; i <= y; i++) {
      ArrayList<MusicNote> currNotes = notes.get(i);
      if (currNotes != null) {
        for (MusicNote x : currNotes) {
          if (x.endBeat() > last) {
            last = x.endBeat();
          }
        }
      }
    }
    return last+1;
  }

  public static final class Builder implements CompositionBuilder<MusicPiece> {

    public Builder(){

    }

    @Override
    public MusicPiece build() {
      MusicPiece mp = new MusicPiece();
      return mp;
    }

    @Override
    public CompositionBuilder<MusicPiece> setTempo(int tempo) {
      this.setTempo(tempo);
      return null;
    }

    @Override
    public CompositionBuilder<MusicPiece> addNote(int start, int end, int instrument, int pitch, int volume) {
      //Need to get the following from the given pitch:
      //  note
      //  octave
      //  sharp/flat
      //not sure how to convert pitch to a MusicNote with current implementation
      Notes n = Notes.F;
      int octave = 2;
      boolean sharp = false;
      boolean flat = false;
      MusicNote toAdd = new MusicNote(n, start, start-end, octave, sharp, flat, instrument, volume);
      return null;
    }

  }


  //temp code for compiling
  @Override
  public void render() {
    System.out.println("Console rendering.");
  }



  /**
   * renders the musical piece as text, also outputs the render it to the console
   * @return String representation of the musical piece
   */
    /*
    public String render() {
        String header = "";
        String lines = "";
        Collections.sort(this.notes, new CompStartBeat());
        int lastBeat = Collections.max(this.notes, new CompEndBeat()).endBeat();
        int numDisplayWidth = String.valueOf(lastBeat).length();

        header += String.format("%" + (numDisplayWidth + 1) + "s", "");

        // fixed number of displayable notes
        HashSet<MusicNote> displayNotes = new HashSet<MusicNote>(231);

        // add notes to set to see what notes we actually need to display
        for (MusicNote note : this.notes) {
            displayNotes.add(note);
        }

        for (MusicNote note : displayNotes) {
            header += String.format("%-4s ", note.noteName());
        }
        header += "\n";

        int curBeat = 0;
        while (curBeat <= lastBeat) {
            lines += String.format("%-" + numDisplayWidth + "d ", curBeat);
            for (MusicNote displayNote : displayNotes) {
                boolean found = false;
                for (MusicNote note : this.notes) {
                    if (note.getNumericNote() == displayNote.getNumericNote()
                            && note.activeOnBeat(curBeat)) {
                        if (note.startBeat == curBeat) {
                            lines += "X    ";
                        } else {
                            lines += "|    ";
                        }
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    lines += "     ";
                }
            }
            lines += "\n";
            curBeat++;
        }

        System.out.print(header + lines);
        return header + lines;
    }
    */
}
