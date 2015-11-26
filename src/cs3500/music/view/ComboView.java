package cs3500.music.view;

import java.awt.event.MouseListener;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import cs3500.music.controller.KeyboardHandler;
import cs3500.music.model.MusicPiece;
import cs3500.music.model.MusicPieceInterface;

/**
 * Created by AviSion on 11/22/2015.
 */
public class ComboView implements ViewInterface {
  GuiViewImpl gui;
  MidiViewImpl midi;
  MusicPieceInterface musicPiece;
  int currentBeat;

  public ComboView(GuiViewImpl gui, MidiViewImpl midi, MusicPieceInterface musicPiece) {
    this.gui = gui;
    this.midi = midi;
    this.musicPiece = musicPiece;
    this.currentBeat = 0;
  }

  @Override
  public void initialize() throws MidiUnavailableException, InterruptedException, InvalidMidiDataException {
    this.gui.initialize();
    //this.midi.initialize();
    this.midi.openSynth();
    this.playPause();
  }

  public void playMidiFromBeat(int beat) throws MidiUnavailableException, InterruptedException {
    while (this.currentBeat <= this.musicPiece.getLastBeat()) {
      this.midi.stopNotesOnBeat(this.currentBeat);
      if (!this.midi.getPlaying()) {
        //this.midi.stopPlaying();
        break;
      }
      this.midi.playNotesOnBeat(this.currentBeat);
      this.gui.setCurrentBeat(this.currentBeat);


      Thread.sleep(this.musicPiece.getTempo() / 1000); // convert beat length to milliseconds
      this.currentBeat++;
      this.gui.redraw();
    }
  }

  public void playPause() throws MidiUnavailableException, InterruptedException {

    if (this.midi.getPlaying()) {
      System.out.println("stopping playing");
      this.midi.stopPlaying();
    } else if (!this.midi.getPlaying()) {
      this.midi.playing = true;
      System.out.println("starting playing");
      this.playMidiFromBeat(this.currentBeat);
    }
    this.gui.redraw();
  }

  public void scroll(boolean b) throws MidiUnavailableException, InterruptedException {
    this.midi.scroll(b);
    if (b) {
      this.gui.updateStartDisplayBeat(midi.currentBeat + midi.SCROLL_SPEED);
      this.gui.redraw();
    } else {
      this.gui.updateStartDisplayBeat(midi.currentBeat - midi.SCROLL_SPEED);
      this.gui.redraw();
    }
  }


  public void addGUIKeyListener(KeyboardHandler keyboardHandler) {
    this.gui.addKeyListener(keyboardHandler);
    this.gui.redraw();
  }

  public void addGUIMouseListener(MouseListener mouseListener) {
    this.gui.addMouseListener(mouseListener);
    this.gui.redraw();
  }

  public void scrollTo(int x) throws MidiUnavailableException, InterruptedException {
    this.gui.updateStartDisplayBeat(x);
    try {
      this.midi.scrollTo(x);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
    this.gui.redraw();
  }

  public void goTo(String loc) {
    if (loc.equals("start")) {
      this.gui.updateStartDisplayBeat(0);
    } else if (loc.equals("end")) {
      this.gui.updateStartDisplayBeat(this.gui.getPiece().getLastBeat() - 80);
    }
    midi.goTo(loc);
    this.gui.redraw();

  }

  public void redraw() {
    this.gui.redraw();
  }
}
