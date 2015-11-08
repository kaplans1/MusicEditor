package cs3500.music.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;


/**
 * Created by natdempk on 11/3/15.
 */

public class MusicPiece {
    ArrayList<MusicNote> notes;
    int beatsPerMeasure;

    /**
     * represents a piece of music with a collection of notes
     * @param beatsPerMeasure number of beats in a measure for this piece of music
     */
    MusicPiece(int beatsPerMeasure) {

        if (beatsPerMeasure > 0) {
            this.beatsPerMeasure = beatsPerMeasure;
        } else {
            throw new IllegalArgumentException("beatsPerMeasure must be > 0.");
        }

        this.notes = new ArrayList<MusicNote>();
    }

    MusicPiece() {
        this(4); // default to 4 beats per measure
    }

    /**
     * adds a note to the piece of music
     * @param note note to be added to the music
     */
    public void addNote(MusicNote note) {
        this.notes.add(note);
    }

    /**
     * renders the musical piece as text, also outputs the render it to the console
     * @return String representation of the musical piece
     */
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
}
