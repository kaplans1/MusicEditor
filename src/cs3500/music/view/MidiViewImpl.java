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
  public final int SCROLL_SPEED = 10;
  MusicPieceInterface musicPiece;
  boolean playing;
  int currentBeat;
  private Synthesizer synth;
  TreeMap<Integer, ArrayList<MusicNote>> turnOffOnBeats;

  /**
   * legitmate constructor to play a piece of music as midi
   *
   * @param musicPiece piece of music to be played as midi
   */
  public MidiViewImpl(MusicPieceInterface musicPiece) {
    try {
      this.synth = MidiSystem.getSynthesizer();
      this.synth.open();
      this.turnOffOnBeats = new TreeMap<>();
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
    this.turnOffOnBeats = new TreeMap<>();
    try {
      this.synth.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }

  public void initialize() throws MidiUnavailableException, InterruptedException {
    this.playing = true;
    this.playFromBeat(0);
  }

  public void playPause() throws InterruptedException, MidiUnavailableException {
    if (this.playing) {
      this.playing = false;
      this.stopPlaying();
    } else if (!this.playing) {
      this.playing = true;
      this.playFromBeat(this.currentBeat);
    }
  }

  public void openSynth() throws MidiUnavailableException {
    this.synth.open();
  }


  public boolean getPlaying() {
    return this.playing;
  }

  public void stopPlaying() {
    this.playing = false;
    while(!this.playing){
      this.currentBeat++;
      this.currentBeat--;
    }
    //this.synth.close();
    System.out.println("STOPPED PLAYING");
  }

  public void playNotesOnBeat(int beat) throws MidiUnavailableException {
    MidiChannel[] channels = this.synth.getChannels();
    ArrayList<MusicNote> currentBeatNotes = this.musicPiece.getNotesStartingOnBeat(beat);

    if (currentBeatNotes != null) {
      for (MusicNote n : currentBeatNotes) {
        // 0 is a piano, 9 is percussion, other channels are for other instruments
        channels[n.getInstrument()].noteOn(n.getPitchID(), n.getVolume());
        // record beats to turn off notes on
        turnOffOnBeats.computeIfAbsent(n.getEndBeat(), v -> new ArrayList<>()).add(n);
      }
    }
  }

  public void stopNotesOnBeat(int beat) {
    ArrayList<MusicNote> turnOffOnBeat = turnOffOnBeats.get(beat);
    MidiChannel[] channels = this.synth.getChannels();

    if (turnOffOnBeat != null) {
      for (MusicNote n : turnOffOnBeat) {
        channels[n.getInstrument()].noteOff(n.getPitchID(), n.getVolume());
      }
    }

    turnOffOnBeats.remove(beat);
  }

  /**
   * used to play a piece of music via midi starting from a certain beat
   *
   * @param beat beat to start playing the music on
   * @throws MidiUnavailableException
   * @throws InterruptedException
   */
  public void playFromBeat(int beat) throws MidiUnavailableException, InterruptedException {
 //   this.synth.open();
    System.out.println("STARTED PLAYING");
    this.playing = true;

    int lastBeat = this.musicPiece.getLastBeat();
    for (int currentBeat = beat; currentBeat <= lastBeat; currentBeat++) {
      this.stopNotesOnBeat(currentBeat);
      if (!this.playing) {
        this.stopPlaying();
        break;
      }
      this.playNotesOnBeat(currentBeat);

      Thread.sleep(this.musicPiece.getTempo() / 1000); // convert beat length to milliseconds
    }

//      this.synth.close();
//      this.currentBeat = 0;
    this.playing = false;
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

  public void scrollTo(int x) throws InterruptedException, MidiUnavailableException {
    //synth.close();
    //synth.open();
    x = x - 40; //account for shift
    int y = x / 20; //number of beats into the current section
    //click to left of current beat
    if ((20 * currentBeat % 80) > x) {
      currentBeat = currentBeat - (currentBeat - y);
      this.playFromBeat(currentBeat);
    } else {
      //click to right of current beat
      currentBeat = currentBeat + (y - currentBeat);
      this.playFromBeat(currentBeat);
    }
  }

  public void goTo(String loc) {
    if (loc.equals("end")) {
      currentBeat = this.musicPiece.getLastBeat();
    } else if (loc.equals("start")) {
      currentBeat = 0;
    }
  }
}
