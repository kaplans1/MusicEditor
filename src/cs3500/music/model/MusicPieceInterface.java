package cs3500.music.model;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by AviSion on 11/10/2015.
 */
public interface MusicPieceInterface {
  void addNote(MusicNote note);
  void deleteNote(MusicNote note);
  //gets notes that start at desired beats - for MidiView, uses TreeMap
  ArrayList<MusicNote> getNotesStartingOnBeat(Integer beat);
  //gets notes that start at desired beats - for MidiView
  TreeMap<Integer, ArrayList<MusicNote>> getAllNotes();

  //gets all PitchIDs
  TreeSet<Integer> getAllPitchIds();


  //gets size of largest note at the end of the piece
  int getlastBeat();
  //void Render();
}
