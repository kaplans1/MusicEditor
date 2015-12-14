package cs3500.music.model;

import cs3500.music.view2.Playable;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

public interface MusicPieceInterface {
  /**
   * adds a note to the music piece
   *
   * @param note musical note to be added
   */
  void addNote(MusicNote note);

  /**
   * removes a note from the music piece
   *
   * @param note note to be removed from the piece
   */
  void deleteNote(MusicNote note);

  /**
   *
   * @param pitchID
   * @param startBeat
   * @return
   */
  MusicNote getNote(int pitchID, int startBeat);

  /**
   * removes a note from the music piece
   *
   * @param deleteNotePitchID pitch id of note to delete
   * @param deleteBeat        starting beat of note to delete
   */
  void deleteNote(int deleteNotePitchID, int deleteBeat);

  /**
   * gets music notes starting on a given beat
   *
   * @param beat beat to get notes starting on
   * @return notes starting on the given beat
   */
  ArrayList<MusicNote> getNotesStartingOnBeat(Integer beat);

  // gets notes that start at desired beats - for MidiView
  TreeMap<Integer, ArrayList<MusicNote>> getAllNotes();

  /**
   * gets all of the pitch ids present in a piece of music
   *
   * @return set of pitch ids
   */
  TreeSet<Integer> getAllPitchIds();

  /**
   * gets all of the pitch labelings present in a piece of music
   *
   * @return all unique pitch labelings in the piece of music
   */
  ArrayList<String> getAllPitches();

  /**
   * outputs a the piece of music's textual representation to the console
   *
   * @return textual representation of a piece of music
   */
  String render();

  /**
   * gets the last numeric beat a note is being played on in the piece of music
   *
   * @return number of last beat a note is being played on
   */
  int getLastBeat();

  /**
   * gets number of beats per measure
   *
   * @return beats per measure of piece of music
   */
  int getBPM();

  /**
   * gets the tempo of the piece in microseconds per beat
   *
   * @return numeric tempo of the piece (microseconds per beat)
   */
  int getTempo();

  /**
   * sets the tempo of the piece of music
   *
   * @param tempo tempo to set music piece to be played at
   */
  void setTempo(int tempo);

  TreeMap<Integer, Repeat> getAllRepeats();

  void addRepeat(Repeat n);
}
