package cs3500.music.view;

import java.awt.event.MouseListener;

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

  public void scroll(boolean b) throws MidiUnavailableException, InterruptedException {
    this.midi.scroll(b);
  }

  public void addGUIKeyListener(KeyboardHandler keyboardHandler) {
    this.gui.addKeyListener(keyboardHandler);
    this.gui.reDraw();
  }

  public void addGUIMouseListener(MouseListener mouseListener) {
    this.gui.addMouseListener(mouseListener);
  }

  public void scrollTo(int x) throws MidiUnavailableException, InterruptedException {
    try {
      this.midi.scrollTo(x);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }

  public void goTo(String loc) {
    midi.goTo(loc);
  }

  public void repaint() {
    this.gui.repaint();
  }
}
