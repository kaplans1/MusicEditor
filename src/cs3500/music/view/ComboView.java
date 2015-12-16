package cs3500.music.view;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import cs3500.music.controller.KeyboardHandler;
import cs3500.music.controller.MouseHandler;
import cs3500.music.model.MusicPieceInterface;

/**
 * Created by AviSion on 11/22/2015.
 */
public class ComboView implements ComboInterface {
  GuiViewOursImpl gui;
  MidiViewImplOurs midi;
  MusicPieceInterface musicPiece;
  public int currentBeat;

  public ComboView(GuiViewOursImpl gui, MidiViewImplOurs midi, MusicPieceInterface musicPiece) {
    this.gui = gui;
    this.midi = midi;
    this.musicPiece = musicPiece;
    this.currentBeat = 0;
  }

  @Override
  public void initialize() throws MidiUnavailableException, InterruptedException,
          InvalidMidiDataException {
    this.gui.initialize();
    //this.midi.initialize();
    this.midi.openSynth();
    this.playPause();
  }

  public int getCurrentBeat() {
    return this.midi.getTickPosition();
  }


  public void playPause() throws MidiUnavailableException, InterruptedException {
    if(!this.midi.isPaused) {
      this.midi.stopPlaying();
    }else {
      this.midi.stopPlaying();
    }
    this.gui.redraw();
  }

  public void scroll(boolean b) throws MidiUnavailableException, InterruptedException {
    this.midi.scroll(b);
    if (b) {
      this.gui.updateStartDisplayBeat(midi.currentBeat + midi.SCROLL_SPEED);
      this.midi.scroll(b);
      this.gui.redraw();
    } else {
      this.gui.updateStartDisplayBeat(midi.currentBeat - midi.SCROLL_SPEED);
      this.midi.scroll(b);
      this.gui.redraw();
    }
  }


  public void addGUIKeyListener(KeyboardHandler keyboardHandler) {
    this.gui.addKeyListener(keyboardHandler);
    this.gui.redraw();
  }

  @Override
  public void addGUIMouseListener(MouseHandler mouseListener) {
    this.gui.addMouseListener(mouseListener);
    this.gui.redraw();
  }

  public void scrollTo(int x) throws MidiUnavailableException, InterruptedException {
    this.gui.updateStartDisplayBeat(x);
    try {
      this.midi.playNotesOnBeat(x);
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
    this.gui.redraw();
  }

  public void goTo(String loc) throws MidiUnavailableException {
    if (loc.equals("start")) {
      this.gui.updateStartDisplayBeat(0);
      this.gui.redraw();
      this.midi.playNotesOnBeat(0);
    } else if (loc.equals("end")) {
      this.gui.updateStartDisplayBeat(this.gui.getPiece().getLastBeat() - 80);
      this.gui.redraw();
      this.midi.stopPlaying();
    }
    this.gui.redraw();
    midi.goTo(loc);
    this.gui.redraw();
  }

  public void redraw() {
    this.gui.redraw();
  }
}
