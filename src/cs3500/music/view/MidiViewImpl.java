package cs3500.music.view;

import java.util.ArrayList;
import java.util.TreeMap;

import javax.sound.midi.*;

import cs3500.music.model.MusicNote;
import cs3500.music.model.MusicPieceInterface;

/**
 * To play a music piece as MIDI
 */
public class MidiViewImpl implements GuiView {
  MusicPieceInterface musicPiece;
  boolean playing;
  int currentBeat;
  private Synthesizer synth;
  public final int SCROLL_SPEED = 10;

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
   * @param synth      synthesizer to use to "play" the piece of music
   */

  public MidiViewImpl(MusicPieceInterface musicPiece, Synthesizer synth) {
    this.musicPiece = musicPiece;
    this.synth = synth;
    this.playing = false;
    this.currentBeat = 0;
    try {
      this.synth.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }

  }

  public void initialize() throws MidiUnavailableException, InterruptedException {
    this.playFromBeat(0);
  }

  public void playPause() throws InterruptedException, MidiUnavailableException {
    if (this.playing) {
      this.stopPlaying();
    } else {
      this.playFromBeat(this.currentBeat);
    }
  }

  public void scroll(boolean b) throws InterruptedException, MidiUnavailableException {
    if ((currentBeat + SCROLL_SPEED > musicPiece.getLastBeat()) ||
        (currentBeat - SCROLL_SPEED < 0)) {
      //do nothing
    } else {
      if (b) {
        currentBeat = currentBeat + SCROLL_SPEED;
        if (playing) {
          this.playFromBeat(currentBeat);
        }
      } else {
        currentBeat = currentBeat - SCROLL_SPEED;
        if (playing) {
          this.playFromBeat(currentBeat);
        }
      }
    }
  }


  public void stopPlaying() {
    this.synth.close();
    System.out.println("STOPPED PLAYING");
    this.playing = false;
  }

  public void playFromBeat(int beat) throws MidiUnavailableException, InterruptedException {
    this.synth.open();
    System.out.println("STARTED PLAYING");
    this.playing = true;
    MidiChannel[] channels = this.synth.getChannels();

    int lastBeat = this.musicPiece.getLastBeat();

    // map  beat to channels to turn off on beat to keep track of when to stop playing notes
    TreeMap<Integer, ArrayList<MusicNote>> turnOffOnBeats = new TreeMap<>();


    for (int i = beat; i <= lastBeat; i++) {
      this.currentBeat = i;
      System.out.print("current beat :");
      System.out.print(i);
      System.out.print("\n");
      if (!this.playing) {
        return;
      }
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

      this.synth.close();
      this.currentBeat = 0;
      this.playing = false;
    }

    this.synth.close();
    this.playing = false;
  }
}
