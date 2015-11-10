package cs3500.music.model;

import java.util.ArrayList;
import java.util.TreeMap;

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
  //void Render();
}
