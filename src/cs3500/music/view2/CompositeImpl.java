package cs3500.music.view2;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.controller.KeyboardHandler;
import cs3500.music.controller.MouseHandler;
import cs3500.music.model.Model;

/**
 * Represents an audio-visual composite view
 */
public class CompositeImpl implements View {

  GuiViewImpl gui;  // the gui of this composite view
  MidiViewImpl midi; // the audio of this composite view

  public CompositeImpl(GuiViewImpl g, MidiViewImpl m) {
    this.gui = g;
    this.midi = m;
  }

  @Override
  public void createView(Model m, int tick) throws InvalidMidiDataException, InterruptedException {
    gui.createView(m, 0);  //start both views from the beginning
    midi.createView(m, 0);
  }

  @Override
  public void redraw() {
    gui.redraw();
  }

  @Override
  public void pause() {
    midi.pause();
    gui.pause();
  }

  @Override
  public void addKeyHandler(KeyboardHandler kbh) {
    gui.addKeyHandler(kbh);
  }

  @Override
  public void addMouseHandler(MouseHandler mh) { gui.addMouseHandler(mh); }

  @Override
  public void stepRight(int howMany) { gui.stepRight(howMany); }

  @Override
  public void stepLeft(int howMany) {
    gui.stepLeft(howMany);
  }
}
