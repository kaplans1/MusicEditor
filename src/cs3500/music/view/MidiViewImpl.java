package cs3500.music.view;

import java.util.ArrayList;
import java.util.TreeMap;

import javax.sound.midi.*;

import cs3500.music.model.MusicNote;
import cs3500.music.model.MusicPieceInterface;

/**
 * A skeleton for MIDI playback
 */
public class MidiViewImpl implements ViewInterface {
    MusicPieceInterface musicPiece;
    private Synthesizer synth;
    private Receiver receiver;

    public MidiViewImpl(MusicPieceInterface musicPiece) {
        try {
            this.synth = MidiSystem.getSynthesizer();
            this.receiver = synth.getReceiver();
            this.synth.open();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        this.musicPiece = musicPiece;
    }

    /**
     * Relevant classes and methods from the javax.sound.midi library: <ul> <li>{@link
     * MidiSystem#getSynthesizer()}</li> <li>{@link Synthesizer} <ul> <li>{@link
     * Synthesizer#open()}</li> <li>{@link Synthesizer#getReceiver()}</li> <li>{@link
     * Synthesizer#getChannels()}</li> </ul> </li> <li>{@link Receiver} <ul> <li>{@link
     * Receiver#send(MidiMessage, long)}</li> <li>{@link Receiver#close()}</li> </ul> </li>
     * <li>{@link MidiMessage}</li> <li>{@link ShortMessage}</li> <li>{@link MidiChannel} <ul>
     * <li>{@link MidiChannel#getProgram()}</li> <li>{@link MidiChannel#programChange(int)}</li>
     * </ul> </li> </ul>
     *
     * @see <a href="https://en.wikipedia.org/wiki/General_MIDI"> https://en.wikipedia.org/wiki/General_MIDI
     * </a>
     */

    public void initialize() throws MidiUnavailableException, InterruptedException {
        this.synth.open();
        MidiChannel[] channels = this.synth.getChannels();

        int lastBeat = this.musicPiece.getLastBeat();

        // map  beat to channels to turn off on beat to keep track of when to stop playing notes
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
