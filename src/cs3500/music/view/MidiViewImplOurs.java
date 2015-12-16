package cs3500.music.view;

import java.util.ArrayList;
import java.util.TreeMap;

import javax.sound.midi.*;

import cs3500.music.model.MusicNote;
import cs3500.music.model.MusicPieceInterface;
import cs3500.music.view2.Playable;

/**
 * To play a music piece as MIDI
 */
public class MidiViewImplOurs implements ViewInterface {
  public final int SCROLL_SPEED = 10;
  MusicPieceInterface musicPiece;
  boolean isPaused = true;
  long pausedAt = 0;
  int currentBeat;
  private Synthesizer synth;
  Sequencer sequencer;
  TreeMap<Integer, ArrayList<MusicNote>> turnOffOnBeats;
  private int tempo;

  public void stopPlaying() throws MidiUnavailableException {
    if (this.isPaused) {
      this.currentBeat = (int) pausedAt;
      playNotesOnBeat((int) pausedAt);
      this.isPaused = false;
      System.out.println("started playing");
    }
    else {
      try {
        sequencer.stop();
        pausedAt = sequencer.getTickPosition();
        this.isPaused = true;
      } catch (Exception e) {
        e.printStackTrace();
      }
      System.out.println("stopped playing");
    }

  }


  /**
   * legitmate constructor to play a piece of music as midi
   *
   * @param musicPiece piece of music to be played as midi
   */
  public MidiViewImplOurs(MusicPieceInterface musicPiece) {
    try {
      this.synth = MidiSystem.getSynthesizer();
      this.sequencer= MidiSystem.getSequencer();
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
  public MidiViewImplOurs(MusicPieceInterface musicPiece, Synthesizer synth) {
    this.musicPiece = musicPiece;
    this.synth = synth;
    this.isPaused = true;
    this.currentBeat = 0;
    this.turnOffOnBeats = new TreeMap<>();
    try {
      this.synth.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }

  public void initialize() throws MidiUnavailableException, InterruptedException {
    if (!isPaused) {
      this.playNotesOnBeat(0);
    }
  }

  public int getTickPosition() {
    return (int)this.sequencer.getTickPosition();
  }

  public void openSynth() throws MidiUnavailableException {
    this.synth.open();
  }

  public boolean getPlaying() {
    return this.isPaused;
  }
//TODO: change comments
  public void playNotesOnBeat(int tick) throws MidiUnavailableException {
    System.out.println(currentBeat);
    try {
      sequencer.open();

      tempo = 600000 / musicPiece.getTempo();
      //System.out.println("tempo comp:  " + composition.getTempo());
      //System.out.println("tempo tempo: " + tempo);

      Sequence sequence = new Sequence(Sequence.PPQ, tempo);
      Track track = sequence.createTrack();

      for (int i = 0; i < musicPiece.getLastBeat()-1; i = i + 1) {
        this.currentBeat = i;
        //an attempt at basic looping when it hits repeats, but even that doesn't work
//        if(musicPiece.getAllRepeats().get(i) !=null){
//          this.currentBeat = 0;
//          this.playNotesOnBeat(0);
//
//        }
        if(musicPiece.getNotesStartingOnBeat(i) != null) {
          ArrayList<MusicNote> playAtBeat = musicPiece.getNotesStartingOnBeat(i);

          for (int j = 0; j < playAtBeat.size(); j = j + 1) {
            int instrument = playAtBeat.get(j).getInstrument();

            ShortMessage message1 = new ShortMessage();
            message1.setMessage(ShortMessage.NOTE_ON, instrument, playAtBeat.get(j).getPitchID(),
                    playAtBeat.get(j).getVolume());
            // added this if statement so that only notes that are starting play
            // rather than playing every continuation note on the first beat
            //'true' replaced playAtBeat.get(j).getIsAttack()
            //'1' replaced playAtBeat.get(j).attackInt()
            if (true) {
              MidiEvent noteOn = new MidiEvent(message1, (i * 1));
              track.add(noteOn);
            }
          }

          for (int j = 0; j < playAtBeat.size(); j = j + 1) {
            ShortMessage message2 = new ShortMessage();
            message2.setMessage(ShortMessage.NOTE_OFF, playAtBeat.get(j).getPitchID(), 0);
            MidiEvent noteOff = new MidiEvent(message2,
                    playAtBeat.get(j).getLength() + (i * 1));
            track.add(noteOff);
          }
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

  public void scroll(boolean b) throws InterruptedException, MidiUnavailableException {
      if (b) {
          this.scrollTo(this.currentBeat+ SCROLL_SPEED);
     //     this.currentBeat = this.currentBeat + SCROLL_SPEED;

      } else {
          this.scrollTo(this.currentBeat- SCROLL_SPEED);
       //   this.currentBeat = this.currentBeat - SCROLL_SPEED;

      }
  }

  public void scrollTo(int x) throws InterruptedException, MidiUnavailableException {
    //synth.close();
    //synth.open();
    this.stopPlaying();
    this.playNotesOnBeat(x);
//    x = x - 40; //account for shift
//    int y = x / 20; //number of beats into the current section
//    //click to left of current beat
//    if ((20 * currentBeat % 80) > x) {
//      currentBeat = currentBeat - (currentBeat - y);
//      this.playNotesOnBeat(currentBeat);
//    } else {
//      //click to right of current beat
//      currentBeat = currentBeat + (y - currentBeat);
//      this.playNotesOnBeat(currentBeat);
//    }
  }

  public void goTo(String loc) {
    if (loc.equals("end")) {
      //currentBeat = this.musicPiece.getLastBeat();
    } else if (loc.equals("start")) {
      currentBeat = 0;
    }
  }
}
