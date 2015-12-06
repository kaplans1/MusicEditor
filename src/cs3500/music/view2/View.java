package cs3500.music.view2;
import java.awt.event.MouseListener;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.controller.KeyboardHandler;
import cs3500.music.controller.MouseHandler;
import cs3500.music.model.Composition;
import cs3500.music.view2.Model;

/**
 * Represents an audio or visual view of a composition
 */
public interface View {
  /**
   * Creates the audio or visual representation of this view
   */
  public void createView(Model m, int tick) throws InvalidMidiDataException, InterruptedException;

  /**
   *
   */
  public void redraw();


  /**
   * Pauses
   */
  public void pause();

  /**
   * @param kbh
   */
  public void addKeyHandler(KeyboardHandler kbh);

  public void addMouseHandler(MouseHandler mh);

  /**
   *
   * @param howMany
   */
  public void stepRight(int howMany);

  /**
   *
   * @param howMany
   */
  public void stepLeft(int howMany);


}