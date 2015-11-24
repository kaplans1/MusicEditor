package cs3500.music.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

import cs3500.music.util.CompositionBuilder;

public class MusicPiece implements MusicPieceInterface {
  //ArrayList<MusicNote> notes;

  // Sorted set of pitchIds
  TreeSet<Integer> pitchIds;

  //all pitches in string format
  ArrayList<String> pitches;

  // mapping starting beat -> List of MusicNote starting on that beat
  TreeMap<Integer, ArrayList<MusicNote>> notes;
  int beatsPerMeasure;
  int tempo; // microseconds per beat

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

    // TODO: add comparator to make this work better
    this.pitchIds = new TreeSet<>();
    this.pitches = new ArrayList<>();
    this.notes = new TreeMap<>();
  }

  public MusicPiece() {
    this(4, 200000); // default to 4 beats per measure
  }

  public ArrayList<MusicNote> getNotesStartingOnBeat(Integer beat) {
    return this.notes.get(beat);
  }

  @Override
  public TreeMap<Integer, ArrayList<MusicNote>> getAllNotes() {
    return this.notes;
    //return (TreeMap<Integer, ArrayList<MusicNote>>) notes.clone();

  }

  public TreeSet<Integer> getAllPitchIds() {
    return this.pitchIds;
  }

  public ArrayList<String> getAllPitches() {
    ArrayList<String> pitches = new ArrayList<>();
    for (int pitchID : this.pitchIds) {
      pitches.add(MusicNote.pitchToString(pitchID));
    }

    //System.out.println(pitches);
    return pitches;
  }


  /**
   * adds a note to the piece of music
   *
   * @param note note to be added to the music
   */
  public void addNote(MusicNote note) {
    if (note.getPitchID() > 127) {
      throw new IllegalArgumentException("Note must have a lower pitch between 0 and 127");
    }
    // usersByCountry.computeIfAbsent(user.getCountry(), v -> new ArrayList<>()).add(user);
        /*ArrayList<MusicNote> startNotes = this.notes.get(note.getStartBeat());
        if (startNotes == null) {
            startNotes = new ArrayList<>();
        }
        // TODO: deduplicate list of notes
        startNotes.add(note);*/

    this.notes.computeIfAbsent(note.getStartBeat(), v -> new ArrayList<>()).add(note);

    this.pitchIds.add(note.getPitchID());
    if (!pitches.contains(note.noteName())) {
      this.pitches.add(note.noteName());
    }
    //this.notes.put(note.getStartBeat(), startNotes);
  }

  public MusicNote getNote(int pitchID, int beat) {
    ArrayList<MusicNote> startNotes = this.notes.get(beat);
    MusicNote foundNote = null;
    if (startNotes != null) {
      for (MusicNote note : startNotes) {
        if (note.getPitchID() == pitchID) {
          foundNote = note;
          break;
        }
      }
    }

    if (foundNote == null) {
      throw new IllegalArgumentException("Couldn't find note with given pitchID and beat");
    }

    return foundNote;
  }

  public void deleteNote(MusicNote note) {
    ArrayList<MusicNote> startNotes = this.notes.get(note.getStartBeat());
    if (startNotes != null) {
      if (startNotes.contains(note)) {
        startNotes.remove(note);
      } else {
        throw new IllegalArgumentException("Note to delete isn't there");
      }
    }
  }

  public void deleteNote(int deleteNotePitchID, int deleteBeat) {
    ArrayList<MusicNote> startNotes = this.notes.get(deleteBeat);
    if (startNotes != null) {
      for (MusicNote note : startNotes) {
        if (note.getPitchID() == deleteNotePitchID) {
          startNotes.remove(note);
          break;
        }
      }
    }
  }

  public int getBPM() {
    return this.beatsPerMeasure;
  }

  public int getTempo() {
    return this.tempo;
  }

  public void setTempo(int tempo) {
    if (tempo > 0) {
      this.tempo = tempo;
    } else {
      throw new IllegalArgumentException("tempo must be > 0.");
    }
  }

  //gets last beat of piece
  public int getLastBeat() {
    if (!this.notes.isEmpty()) {
      int last = 0;
      int y = notes.lastKey();
      for (int i = 0; i <= y; i++) {
        ArrayList<MusicNote> currNotes = notes.get(i);
        if (currNotes != null) {
          if (!currNotes.isEmpty()) {
            for (MusicNote x : currNotes) {
              if (x.getEndBeat() > last) {
                last = x.getEndBeat();
              }
            }
          } else {
            throw new IllegalArgumentException("Notes were deleted, no last key!");
          }
        }
      }
      return last + 1;
    } else {
      throw new IllegalArgumentException("Empty piece, no last beat!");
    }
  }

  /**
   * renders the musical piece as text, also outputs the render it to the console
   *
   * @return String representation of the musical piece
   */
  public String render() {
    if (this.notes.isEmpty() || this.notes.get(notes.lastKey()).isEmpty()) {
      return("");
    } else {
      StringBuilder output = new StringBuilder();
      int lastBeat = this.getLastBeat();
      int numDisplayWidth = String.valueOf(lastBeat).length();

      // get sorted sets of pitch IDs and Names
      TreeSet<Integer> pitchIDs = this.getAllPitchIds();
      ArrayList<String> pitchNames = this.getAllPitches();
      HashMap<Integer, Integer> pitchIDIndices = new HashMap<>();
      // construct dumb mapping from IDs to indices because TreeSet doesn't provide this
      int i = 0;
      for (Integer pitch : pitchIDs) {
        pitchIDIndices.put(pitch, i);
        i++;
      }
      // make a dumb char array to keep track of output strings
      // this gets converted to a better string representation later
      char[][] beatChars = new char[pitchNames.size()][this.getLastBeat()];

      // construct header of sorted pitches
      // padding for numbers on first line
      output.append(String.format("%" + (numDisplayWidth + 1) + "s", ""));
      // pitch names in header
      for (String pitch : pitchNames) {
        output.append(String.format("%-4s ", pitch));
      }
      output.append("\n");

        /*
        System.out.println(pitchIDs);
        System.out.println(pitchNames);
        System.out.println(pitchIDIndices);

        for (char[] row : beatChars)
        {
            //Arrays.fill(row, 0);
            System.out.println(Arrays.toString(row));
        }
        */

      int currentBeat = 0;
      while (currentBeat < lastBeat) {
        // for each beat get notes that start on the beat and fill in their spa
        ArrayList<MusicNote> notesOnBeat = this.getNotesStartingOnBeat(currentBeat);
        if (notesOnBeat != null) {
          for (MusicNote note : notesOnBeat) {
            int pitch = pitchIDIndices.get(note.getPitchID());
            beatChars[pitch][currentBeat] = 'X';

            for (int beat = currentBeat + 1; beat <= note.getEndBeat(); beat++) {
              beatChars[pitch][beat] = '|';
            }
          }
        }
        currentBeat++;
      }

        /*
        System.out.println("FILLED");
        for (char[] row : beatChars)
        {
            System.out.println(Arrays.toString(row));
        }
        System.out.println(lastBeat);
        */

      currentBeat = 0;
      while (currentBeat < lastBeat) {
        // add beat number to side
        output.append(String.format("%-" + numDisplayWidth + "d ", currentBeat));
        // add appropriate beat characters or empty space
        for (i = 0; i < beatChars.length; i++) {
          char pitchBeatChar = beatChars[i][currentBeat];
          if (pitchBeatChar != 0) {
            output.append(pitchBeatChar + "    ");
          } else {
            output.append("     ");
          }
        }

        output.append("\n");
        currentBeat++;
      }

      System.out.print(output.toString());
      return output.toString();
    }
  }


  public static final class Builder implements CompositionBuilder<MusicPieceInterface> {

    private MusicPieceInterface musicPiece;

    public Builder() {
      this.musicPiece = new MusicPiece();
    }

    public MusicPieceInterface build() {
      return this.musicPiece;
    }

    public CompositionBuilder<MusicPieceInterface> setTempo(int tempo) {
      this.musicPiece.setTempo(tempo);
      return this;
    }

    public CompositionBuilder<MusicPieceInterface> addNote(int start, int end, int instrument,
                                                           int pitch, int volume) {
      this.musicPiece.addNote(new MusicNote(pitch, start, end - start, instrument - 1, volume));
      return this;
    }
  }
}
