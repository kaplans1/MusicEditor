package cs3500.music.view;

import java.util.ArrayList;
import java.util.TreeMap;

import javax.sound.midi.*;

import cs3500.music.model.MusicNote;
import cs3500.music.model.MusicPieceInterface;

/**
 * To play a music piece as MIDI
 */
public class MidiViewImpl implements ViewInterface {
    MusicPieceInterface musicPiece;
    private Synthesizer synth;

    /**
     * legitmate constructor to play a piece of music as midi
     *
     * @param musicPiece piece of music to be played as midi
     */
    public MidiViewImpl(MusicPieceInterface musicPiece) {
        try {
            this.synth = MidiSystem.getSynthesizer();
            this.synth.open();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        this.musicPiece = musicPiece;
    }

    /**
     * Constructor for providing a specific synth mock for testing
     *
     * @param musicPiece piece of music to be "played" as midi
     * @param synth synthesizer to use to "play" the piece of music
     */

    public MidiViewImpl(MusicPieceInterface musicPiece, Synthesizer synth) {
        this.musicPiece = musicPiece;
        this.synth = synth;
        try {
            this.synth.open();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void initialize() throws MidiUnavailableException, InterruptedException {
        this.synth.open();
        MidiChannel[] channels = this.synth.getChannels();

        int lastBeat = this.musicPiece.getLastBeat();

        // map beat to channels to turn off on beat to keep track of when to stop playing notes
        TreeMap<Integer, ArrayList<MusicNote>> turnOffOnBeats = new TreeMap<>();


        for (int i = 0; i <= lastBeat; i++) {
            // get notes to play on this beat
            ArrayList<MusicNote> currentBeatNotes = this.musicPiece.getNotesStartingOnBeat(i);
            // get notes to turn off on this beat
            ArrayList<MusicNote> turnOffOnBeat = turnOffOnBeats.get(i);

            // turn off notes as necessary
            if (turnOffOnBeat != null) {
                for (MusicNote n : turnOffOnBeat) {
                    channels[n.getInstrument()].noteOff(n.getPitchID(), n.getVolume());
                }
            }

            // play notes as necessary
            if (currentBeatNotes != null) {
                try {
                    for (MusicNote n : currentBeatNotes) {
                        // 0 is a piano, 9 is percussion, other channels are for other instruments
                        channels[n.getInstrument()].noteOn(n.getPitchID(), n.getVolume());
                        // record beats to turn off notes on
                        turnOffOnBeats.computeIfAbsent(n.getEndBeat(),
                                v -> new ArrayList<>()).add(n);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Thread.sleep(this.musicPiece.getTempo() / 1000); // convert beat length to milliseconds
        }

        this.synth.close();
    }
}
