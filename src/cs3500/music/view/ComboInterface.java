package cs3500.music.view;

import cs3500.music.controller.KeyboardHandler;
import cs3500.music.controller.MouseHandler;

import javax.sound.midi.MidiUnavailableException;
import java.awt.event.MouseListener;

/**
 * Created by natdempk on 12/8/15.
 */
public interface ComboInterface extends ViewInterface {

    void addGUIKeyListener(KeyboardHandler keyboardHandler);

    void addGUIMouseListener(MouseHandler mouseListener);

    void goTo(String loc) throws MidiUnavailableException;

    void scroll(boolean b) throws MidiUnavailableException, InterruptedException;

    void scrollTo(int x) throws MidiUnavailableException, InterruptedException;

    void playPause() throws MidiUnavailableException, InterruptedException;

    void redraw();
}