package cs3500.music.view;

import java.util.ArrayList;

import javax.sound.midi.*;

import cs3500.music.model.MusicNote;
import cs3500.music.model.MusicPiece;
import cs3500.music.model.MusicPieceInterface;

/**
 * A skeleton for MIDI playback
 */
public class MidiViewImpl implements ViewInterface {
  MusicPieceInterface mp;
  private Synthesizer synth;
  private Receiver receiver;

  public MidiViewImpl(MusicPieceInterface mp) {
    try {
      this.synth = MidiSystem.getSynthesizer();
      this.receiver = synth.getReceiver();
      this.synth.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
    this.mp = mp;
  }

  /**
   * Relevant classes and methods from the javax.sound.midi library: <ul> <li>{@link
   * MidiSystem#getSynthesizer()}</li> <li>{@link Synthesizer} <ul> <li>{@link
   * Synthesizer#open()}</li> <li>{@link Synthesizer#getReceiver()}</li> <li>{@link
   * Synthesizer#getChannels()}</li> </ul> </li> <li>{@link Receiver} <ul> <li>{@link
   * Receiver#send(MidiMessage, long)}</li> <li>{@link Receiver#close()}</li> </ul> </li> <li>{@link
   * MidiMessage}</li> <li>{@link ShortMessage}</li> <li>{@link MidiChannel} <ul> <li>{@link
   * MidiChannel#getProgram()}</li> <li>{@link MidiChannel#programChange(int)}</li> </ul> </li>
   * </ul>
   *
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI"> https://en.wikipedia.org/wiki/General_MIDI
   * </a>
   */

  public void playNote() throws InvalidMidiDataException {
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, 0, 60, 64);
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, 0, 60, 64);
    this.receiver.send(start, -1);
    this.receiver.send(stop, this.synth.getMicrosecondPosition() + 200000);
    this.receiver.close(); // Only call this once you're done playing *all* notes
  }

  //doesn't play concurrent notes - only sequential notes
  @Override
  public void initialize() throws MidiUnavailableException, InterruptedException {

    Synthesizer synth = MidiSystem.getSynthesizer();
    synth.open();
    for (int i = 0; i <= mp.getAllNotes().lastKey(); i++) {
      ArrayList<MusicNote> curr = mp.getAllNotes().get(i);
      if (curr != null) {
        try {
          int channel = 0;
          int volume = 0;
          int duration = 0;
          for (MusicNote n : curr) {
            MidiChannel[] channels = synth.getChannels();
            channel = n.getInstrument(); // 0 is a piano, 9 is percussion, other channels are for other instruments
            volume = n.getVolume(); // between 0 et 127
            duration = (n.getEndBeat()-n.getStartBeat()) * 1000 * this.mp.getBPM()/60; // in milliseconds


              channels[channel].noteOn(n.getPitchID(), volume);
              Thread.sleep(duration);
              channels[channel].noteOff(n.getPitchID(), volume);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        //sleeps until next note
        Thread.sleep(1000 * this.mp.getBPM()/60);
      }
    }
    synth.close();
  }
}
