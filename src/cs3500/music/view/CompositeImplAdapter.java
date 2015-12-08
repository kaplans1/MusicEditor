package cs3500.music.view;

import com.sun.javaws.exceptions.InvalidArgumentException;
import cs3500.music.controller.KeyboardHandler;
import cs3500.music.controller.MouseHandler;
import cs3500.music.view2.CompositeImpl;
import cs3500.music.view2.Model;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.awt.event.MouseListener;

/**
 * Created by natdempk on 12/8/15.
 */
public class CompositeImplAdapter implements ComboInterface {
    CompositeImpl compositeImpl;
    Model m;

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
            // scroll to end
            x = scrollMax;
            //throw new IllegalArgumentException("Cannot scroll beyond end of composition");
        } else if (x < scrollMin) {
            // scroll to beginning
            x = scrollMin;
            //throw new IllegalArgumentException("Cannot scroll beyond beginning of composition");
        }

        if (scrollMove > 0) {
            this.compositeImpl.stepRight(x);
        } else if (scrollMove < 0) {
            this.compositeImpl.stepRight(Math.abs(x));
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
        // not sure they implemented playing again after pausing, so might just pause
        this.compositeImpl.pause();
    }

    @Override
    public void redraw() {
        this.compositeImpl.redraw();
    }

    @Override
    public void initialize() throws MidiUnavailableException, InterruptedException, InvalidMidiDataException {
        this.compositeImpl.createView(this.m, 0);
    }
}
