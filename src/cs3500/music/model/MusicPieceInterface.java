package cs3500.music.model;

import java.util.ArrayList;

/**
 * Created by AviSion on 11/10/2015.
 */
public interface MusicPieceInterface {
  void addNote(MusicNote note);
  void deleteNote(MusicNote note);
  //gets notes that start at desired beats
  ArrayList<MusicNote> getNotesStartingOnBeat(Integer beat);
  //void Render();
}
