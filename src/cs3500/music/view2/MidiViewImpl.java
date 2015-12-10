package cs3500.music.view2;

import java.util.ArrayList;

import javax.sound.midi.*;

import cs3500.music.controller.KeyboardHandler;
import cs3500.music.controller.MouseHandler;
import cs3500.music.view2.Composition;
import cs3500.music.view2.Model;
import cs3500.music.view2.Playable;

/**
 * A skeleton for MIDI playback
 */
public class MidiViewImpl implements View {
  private Synthesizer synth;
  private Model composition = new Composition();

  private int tempo;

  Sequencer sequencer;

  public MidiViewImpl() {
    try {
      sequencer = MidiSystem.getSequencer();
      this.synth = MidiSystem.getSynthesizer();
      this.synth.open();
     // this.playNote(composition, 0);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Relevant classes and methods from the javax.sound.midi library: <ul> <li>{@link
   * MidiSystem#getSynthesizer()}</li> <li>{@link Synthesizer} <ul> <li>{@link
   * Synthesizer#open()}</li> <li>{@link Synthesizer#getReceiver()}</li> <li>{@link
   * Synthesizer#getChannels()}</li> </ul> </li> <li>{@link Receiver} <ul> <li>{@link
   * Receiver#send(MidiMessage, long)}</li> <li>{@link Receiver#close()}</li> </ul> </li> <li>
   * {@link MidiMessage}</li> <li>{@link ShortMessage}</li> <li>{@link MidiChannel} <ul> <li>{@link
   * MidiChannel#getProgram()}</li> <li>{@link MidiChannel#programChange(int)}</li> </ul> </li>
   * </ul>
   *
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI">
   *   https://en.wikipedia.org/wiki/General_MIDI
   * </a>
   */

  public void playNote(int tick) {
    try {
      sequencer.open();

      tempo = 600000 / composition.getTempo();
      System.out.println("tempo comp:  " + composition.getTempo());
      System.out.println("tempo tempo: " + tempo);

      Sequence sequence = new Sequence(Sequence.PPQ, tempo);
      Track track = sequence.createTrack();

      for (int i = 0; i < composition.getNumBeats()-1; i = i + 1) {
        ArrayList<Playable> playAtBeat = composition.getNotes().get(i);

        for (int j = 0; j < playAtBeat.size(); j = j + 1) {
          int instrument = playAtBeat.get(j).getInstrument();

          ShortMessage message1 = new ShortMessage();
          message1.setMessage(ShortMessage.NOTE_ON, instrument, playAtBeat.get(j).getMidiIndex(),
                  playAtBeat.get(j).getVolume());
          // added this if statement so that only notes that are starting play
          // rather than playing every continuation note on the first beat
          if (playAtBeat.get(j).getIsAttack()) {
            MidiEvent noteOn = new MidiEvent(message1, (i * playAtBeat.get(j).attackInt()));
            track.add(noteOn);
          }
        }

        for (int j = 0; j < playAtBeat.size(); j = j + 1) {
          ShortMessage message2 = new ShortMessage();
          message2.setMessage(ShortMessage.NOTE_OFF, playAtBeat.get(j).getMidiIndex(), 0);
          MidiEvent noteOff = new MidiEvent(message2,
                  playAtBeat.get(j).getDuration() + (i * playAtBeat.get(j).attackInt()));
          track.add(noteOff);
        }
      }

      sequencer.setSequence(sequence);
      sequencer.setTickPosition(tick);
      sequencer.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
    synth.close();
  }

  boolean isPaused = true;
  long pausedAt = 0;

  @Override
  public void pause() {
    if (isPaused) {
      playNote((int)pausedAt);
      isPaused = false;
    }
    else {
      try {
        sequencer.stop();
        pausedAt = sequencer.getTickPosition();
        isPaused = true;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

  }

  @Override
  public void addKeyHandler(KeyboardHandler kbh) {

  }

  @Override
  public void addMouseHandler(MouseHandler mh) {

  }

  @Override
  public void stepRight(int howMany) {}

  @Override
  public void stepLeft(int howMany) {}

  @Override
  public void createView(Model c, int tick) throws InvalidMidiDataException, InterruptedException {
    composition = c;
    //this.isPaused = false;
    if (!isPaused) {
      this.playNote(tick);
    }
  }

  @Override
  public void redraw() {

  }


  /**
   * Retrieves the tempo of this MIDIViewImpl
   *
   * @returns an int representing the tempo of this piece
   */
  public int getTempo() {
    return this.tempo;
  }

}
