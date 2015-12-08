package cs3500.music.view2;
import java.util.ArrayList;

/**
 * An interface for manipulating and editing a piece of music
 */
public interface Model {

  /**
   * Adds a note to the composition
   *
   * @param startBeat the starting beat for this note (inclusive)
   * @param endBeat the note at which the beat ends   (exclusive)
   * @param instrument the instrument which plays this note
   * @param pitch the pitch of this Note (also determines octave - based on midi table)
   * @param volume the volume of this note
   */
  void addNote(int startBeat, int endBeat, int instrument, int pitch, int volume);
  int DEFAULT_TEMPO = 200000;
  /**
   * Removes the Note starting at the provided beat with the provided pitch
   *
   * @param startBeat The beat at which the note starts (inclusive)
   * @param note the note to be removed
   */
  void removeNote(int startBeat, Playable note);

  /**
   * Determines if the given note exists at the given beat
   *
   * @param note The pitch of the note to check for
   * @param beat The beat at which the note is present
   *
   * @return whether the Note exists at the provided beat
   */
  boolean doesNoteExist(Playable note, int beat);

  /**
   * Gets all of the notes sounding at a given beat
   *
   * @param beat the beat for which you want to know what Pitches sound
   *
   * @return a list of all the notes found at this beat
   */
  ArrayList<Playable> notesAtBeat(int beat);

  /**
   * Gets the octave intervals represented by this Composition
   *
   * @return a list of the octaves represented
   */
  ArrayList<Integer> getOctaveInterval();

  /**
   * adds measures
   */
  void addMeasures(int num);

  int getNumBeats();

  /**
   * gets the tempo of the piece
   * @return integer value of the tempo in microseconds
   */
  int getTempo();

  void setTempo(int tempo);

  /**
   *
   */
  //void addOctave();

  /**
   * gets the notes
   * @return the array list of array list of notes
   */
  ArrayList<ArrayList<Playable>> getNotes();
}
