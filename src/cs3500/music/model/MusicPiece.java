package cs3500.music.model;

import java.util.ArrayList;
import java.util.Collections;
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
        //need a way to sort before returning
        return this.pitches;
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
        ArrayList<MusicNote> startNotes = this.notes.get(note.getStartBeat());
        if (startNotes == null) {
            startNotes = new ArrayList<>();
        }
        // TODO: deduplicate list of notes
        startNotes.add(note);
        this.pitchIds.add(note.getPitchID());
        if (!pitches.contains(note.noteName())) {
            this.pitches.add(note.noteName());
        }
        this.notes.put(note.getStartBeat(), startNotes);
    }

    public void deleteNote(MusicNote note) {
        ArrayList<MusicNote> startNotes = this.notes.get(note.getStartBeat());
        if (startNotes != null) {
            startNotes.remove(note);
        }
    }

    public int getBPM() {
        return this.beatsPerMeasure;
    }

    public int getTempo() {
        return this.tempo;
    }


    //gets last beat of piece
    public int getLastBeat() {
        int last = 0;
        int y = notes.lastKey();
        for (int i = 0; i <= y; i++) {
            ArrayList<MusicNote> currNotes = notes.get(i);
            if (currNotes != null) {
                for (MusicNote x : currNotes) {
                    if (x.getEndBeat() > last) {
                        last = x.getEndBeat();
                    }
                }
            }
        }
        return last + 1;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }



/*
  public static final class Builder
*/

    //temp code for compiling
    @Override
    public void render() {
        System.out.println("Console rendering.");
    }


    /**
     * renders the musical piece as text, also outputs the render it to the console
     *
     * @return String representation of the musical piece
     */
    // TODO: fix this up, use a stringbuilder
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

        public CompositionBuilder<MusicPieceInterface> addNote(int start, int end, int instrument, int pitch, int volume) {
            this.musicPiece.addNote(new MusicNote(pitch, start, end - start, instrument - 1, volume));
            return this;
        }
    }
}
