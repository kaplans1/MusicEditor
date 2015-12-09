package cs3500.music.model;

import cs3500.music.view2.Model;
import cs3500.music.view2.Playable;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by AviSion on 12/8/2015.
 */
public class MusicPieceModelAdapter implements Model {
    MusicPieceInterface m;

    //constructor that makes a Model, maybe...?
    public MusicPieceModelAdapter(MusicPieceInterface m){
       this.m=m;
    }


    @Override
    public void addNote(int startBeat, int endBeat, int instrument, int pitch, int volume) {
        MusicNote n = new MusicNote(pitch, startBeat, endBeat-startBeat,instrument, volume);
        m.addNote(n);
    }

    @Override
    public void removeNote(int startBeat, Playable note) {
        m.deleteNote(note.getPitch(), startBeat);
    }

    @Override
    public boolean doesNoteExist(Playable note, int beat) {
        return !(m.getNote(note.getPitch(), beat) == null);
    }

    @Override
    public ArrayList<Playable> notesAtBeat(int beat) {
        ArrayList<MusicNote> n=  m.getNotesStartingOnBeat(beat);


        ArrayList<Playable> x = new ArrayList<>();
        if(n!=null) {
            for (MusicNote a : n) {

                    x.add(a.toPlayable());

            }
        }
        if( n == null) {
            return new ArrayList<Playable>();
        } else {
            return x;
        }
    }

    @Override
    public ArrayList<Integer> getOctaveInterval() {
        return new ArrayList<>(m.getAllPitchIds());
    }

    @Override
    public void addMeasures(int num) {
        //not really needed?
    }

    //uncommented method - total length I think
    @Override
    public int getNumBeats() {
        return m.getLastBeat();
    }

    @Override
    public int getTempo() {
        return m.getTempo();
    }

    @Override
    public void setTempo(int tempo) {
        m.setTempo(tempo);
    }

    @Override
    public ArrayList<ArrayList<Playable>> getNotes() {
        return null;
    }
}
