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
  public final int SCROLL_SPEED = 10;
  MusicPieceInterface musicPiece;
  boolean playing;
  int currentBeat;
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
   * @param synth      synthesizer to use to "play" the piece of music
   */
  public MidiViewImpl(MusicPieceInterface musicPiece, Synthesizer synth) {
    this.musicPiece = musicPiece;
    this.synth = synth;
    this.playing = true;
    this.currentBeat = 0;
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
    } else if (!this.playing){
      this.playing = true;
      this.playFromBeat(this.currentBeat);
    }
  }

  public void stopPlaying() {
    this.playing = false;
    this.synth.close();
    System.out.println("STOPPED PLAYING");
  }

  public void playFromBeat(int beat) throws MidiUnavailableException, InterruptedException {
    if(!this.playing){
      this.stopPlaying();
      return;
    }
      this.synth.open();
      System.out.println("STARTED PLAYING");
      this.playing = true;
      MidiChannel[] channels = this.synth.getChannels();

      int lastBeat = this.musicPiece.getLastBeat();

      // map  beat to channels to turn off on beat to keep track of when to stop playing notes
      TreeMap<Integer, ArrayList<MusicNote>> turnOffOnBeats = new TreeMap<>();
    if(!this.playing){
      return;
    }
        for (int i = beat; i <= lastBeat; i++) {
          if(!this.playing){
            this.stopPlaying();
            break;
          }
          this.currentBeat = i;
          System.out.print("current beat :");
          System.out.print(i);
          System.out.print("\n");

          // get notes to play on this beat
          ArrayList<MusicNote> currentBeatNotes = this.musicPiece.getNotesStartingOnBeat(i);
          // get notes to turn off on this beat
          ArrayList<MusicNote> turnOffOnBeat = turnOffOnBeats.get(i);
          if(!this.playing){
            this.stopPlaying();
            break;
          }
          // turn off notes as necessary
          if (turnOffOnBeat != null) {
            if(!this.playing){
              this.stopPlaying();
              break;
            }
            for (MusicNote n : turnOffOnBeat) {
              if(!this.playing){
                this.stopPlaying();
                break;
              }
              channels[n.getInstrument()].noteOff(n.getPitchID(), n.getVolume());
            }
          }

          // play notes as necessary
          if (currentBeatNotes != null) {
            if(!this.playing){
              this.stopPlaying();
              break;
            }
            try {
              if(!this.playing){
                this.stopPlaying();
                break;
              }
              for (MusicNote n : currentBeatNotes) {
                if(!this.playing){
                  this.stopPlaying();
                  break;
                }
                // 0 is a piano, 9 is percussion, other channels are for other instruments
                channels[n.getInstrument()].noteOn(n.getPitchID(), n.getVolume());
                // record beats to turn off notes on
                turnOffOnBeats.computeIfAbsent(n.getEndBeat(),
                        v -> new ArrayList<>()).add(n);
                currentBeat++;
                if(!this.playing){
                  this.stopPlaying();
                  break;
                }
              }
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
          if(!this.playing){
            this.stopPlaying();
            break;
          }
          Thread.sleep(this.musicPiece.getTempo() / 1000); // convert beat length to milliseconds
          if(!this.playing){
            this.stopPlaying();
            break;
          }
        }
    if(!this.playing){
      this.stopPlaying();
      return;
    }
//      this.synth.close();
//      this.currentBeat = 0;
//      this.playing = false;
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
    synth.close();
    synth.open();
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
    if(loc.equals("end")){
      currentBeat = this.musicPiece.getLastBeat();
    } else if (loc.equals("start")){
      currentBeat = 0;
    }
  }
}
