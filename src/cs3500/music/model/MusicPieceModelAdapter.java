package cs3500.music.model;

import cs3500.music.view2.Model;
import cs3500.music.view2.Playable;
import sun.jvm.hotspot.runtime.solaris_amd64.SolarisAMD64JavaThreadPDAccess;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Adapter to use a MusicPieceInterface as if it is a Model
 */
public class MusicPieceModelAdapter implements Model {
  MusicPieceInterface m;
  ArrayList<ArrayList<Playable>> assembly;

  /**
   * Creates an adapter to act as a Model given a MusicPieceInterface
   *
   * @param m MusicPieceInteface to adapt as model
   */
  public MusicPieceModelAdapter(MusicPieceInterface m) {
    this.m = m;
    // convert to new model type for midi playback
    ArrayList<ArrayList<Playable>> x = new ArrayList<>();

    for (int i = 0; i < m.getLastBeat(); i++) {
      x.add(new ArrayList<>());
    }

    for (int i = 0; i < m.getLastBeat(); i++) {
      ArrayList<Playable> y = x.get(i);
      if (m.getNotesStartingOnBeat(i) != null) {
        for (MusicNote n : m.getNotesStartingOnBeat(i)) {
          y.add(n.toPlayable(true));
          for (int b = i + 1; b <= n.getEndBeat(); b++) {
            x.get(b).add(n.toPlayable(false));
          }
        }
      }
    }

    this.assembly = x;
  }


  @Override
  public void addNote(int startBeat, int endBeat, int instrument, int pitch, int volume) {
    MusicNote n = new MusicNote(pitch, startBeat, endBeat - startBeat, instrument, volume);
    this.m.addNote(n);
  }

  @Override
  public void removeNote(int startBeat, Playable note) {
    this.m.deleteNote(note.getPitch(), startBeat);
  }

  @Override
  public boolean doesNoteExist(Playable note, int beat) {
    return !(this.m.getNote(note.getPitch(), beat) == null);
  }

  @Override
  public ArrayList<Playable> notesAtBeat(int beat) {
    return this.assembly.get(beat);
  }

  @Override
  public ArrayList<Integer> getOctaveInterval() { // this is fine
    TreeSet<Integer> octaves = new TreeSet<>();
    for (Integer pitchID : this.m.getAllPitchIds()) {
      octaves.add(pitchID / 12);
    }
    ArrayList<Integer> octaveList = new ArrayList<>();
    octaveList.addAll(octaves);
    return octaveList;
  }

  @Override
  public void addMeasures(int num) {
    // intentionally left empty, not needed due to implementation differences
  }

  /**
   * @return length of composition in beats
   */
  @Override
  public int getNumBeats() {
    return this.m.getLastBeat();
  }

  @Override
  public int getTempo() {
    return this.m.getTempo();
  }

  @Override
  public void setTempo(int tempo) {
    this.m.setTempo(tempo);
  }

  @Override
  public ArrayList<ArrayList<Playable>> getNotes() {
    return this.assembly;
  }
}
