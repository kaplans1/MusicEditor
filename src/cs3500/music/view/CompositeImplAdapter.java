package cs3500.music.view;

import cs3500.music.controller.KeyboardHandler;
import cs3500.music.controller.MouseHandler;
import cs3500.music.view2.CompositeImpl;
import cs3500.music.view2.Model;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 * Adapts a CompositeImpl and Model to act as a ComboInterface to be used with
 * our controller
 */
public class CompositeImplAdapter implements ComboInterface {
  CompositeImpl compositeImpl;
  Model m;

  /**
   *
   *
   * @param compositeImpl CompositeImpl to adapt as a ComboInterface
   * @param m Model to use with CompositImpl
   */
  CompositeImplAdapter(CompositeImpl compositeImpl, Model m) {
    this.compositeImpl = compositeImpl;
    this.m = m;
  }

  @Override
  public void addGUIKeyListener(KeyboardHandler keyboardHandler) {
    this.compositeImpl.addKeyHandler(keyboardHandler);
  }

  @Override
  public void addGUIMouseListener(MouseHandler mouseListener) {
    this.compositeImpl.addMouseHandler(mouseListener);
  }

  @Override
  public void scrollTo(int x) {
    int scrollPosition = this.compositeImpl.getScrollPosition();
    int scrollMove = x - scrollPosition;
    int scrollMin = 0;
    int scrollMax = this.compositeImpl.getScrollMaximum();

    if (x > scrollMax) {
      // scroll to end if we try to scroll too far
      x = scrollMax;
    } else if (x < scrollMin) {
      // scroll to beginning if we try to scroll too far
      x = scrollMin;
    }

    // move left or right as necessary when scrolling
    if (scrollMove > 0) {
      this.compositeImpl.stepRight(x);
    } else if (scrollMove < 0) {
      this.compositeImpl.stepLeft(Math.abs(x));
    }
  }

  @Override
  public void goTo(String loc) {
    if (loc.equals("start")) {
      this.scrollTo(0);
    } else if (loc.equals("end")) {
      this.scrollTo(this.compositeImpl.getScrollMaximum());
    }

    this.redraw();
  }

  @Override
  public void scroll(boolean b) {
    // with their impl we can't change midi position so we just update the gui
    if (b) {
      this.compositeImpl.stepRight(1);
      this.redraw();
    } else {
      this.compositeImpl.stepLeft(1);
      this.redraw();
    }
  }

  @Override
  public void playPause() {
    this.compositeImpl.pause();
  }

  @Override
  public void redraw() {
    this.compositeImpl.redraw();
  }

  @Override
  public void initialize() throws MidiUnavailableException,
          InterruptedException, InvalidMidiDataException {
    this.compositeImpl.createView(this.m, 0);
  }
}
