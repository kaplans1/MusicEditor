package meditor.model;

import java.util.ArrayList;

/**
 * Representation of a music editor
 */
public interface Meditor {
  /**
   * Adds supplied note in slightly modified form to main note collector
   *
   * @param beats  length of note
   * @param octave octave of note
   * @param pitch  pitch of note - accepts all harmonic equivalents
   * @param start  start beat of note
   */
  void addNote(int beats, int octave, String pitch, int start);

  /**
   * Deletes note of given octave and pitch. 'Start' need not be start note of note that is to be
   * deleted. Can be middle note.
   */
  void delNote(int octave, String pitch, int start);

  /**
   * Edits note of given octave and pitch. 'Start' need not be start note of note that is to be
   * edited. Can be middle note. Resizes to given new size. Creates temporary new note to check for
   * conflicts
   */
  void ediNote(int newSize, int octave, String pitch, int start);


  //Returns the editor for visualization, playback, etc.
  ArrayList<Note> getGraph();

  //Prints graph into console
  void Render();
}
