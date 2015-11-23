package cs3500.music.view;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import cs3500.music.controller.KeyboardHandler;
import cs3500.music.model.MusicPieceInterface;

/**
 * Created by AviSion on 11/22/2015.
 */
public class ComboView implements ViewInterface{
  GuiViewImpl gui;
  MidiViewImpl midi;
  public ComboView(GuiViewImpl gui, MidiViewImpl midi) {
    this.gui = gui;
    this.midi = midi;
  }

  @Override
  public void initialize() throws MidiUnavailableException, InterruptedException, InvalidMidiDataException {
    this.gui.initialize();
    this.midi.initialize();
  }

  public void playPause() throws MidiUnavailableException, InterruptedException {
    this.midi.playPause();
  }

  public void addGUIKeyListener(KeyboardHandler keyboardHandler) {
    this.gui.addKeyListener(keyboardHandler);
  }
}
