package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import cs3500.music.model.MusicPieceInterface;
import cs3500.music.view.ComboView;

public class MusicController {
  ComboView comboView;
  KeyboardHandler keyListener;
  ArrayList<Integer> keySequence;

  protected static int[] addKeys = {
      KeyEvent.VK_0,
      KeyEvent.VK_1,
      KeyEvent.VK_2,
      KeyEvent.VK_3,
      KeyEvent.VK_4,
      KeyEvent.VK_5,
      KeyEvent.VK_6,
      KeyEvent.VK_7,
      KeyEvent.VK_8,
      KeyEvent.VK_9,
      KeyEvent.VK_A,
      KeyEvent.VK_B,
      KeyEvent.VK_C,
      KeyEvent.VK_D,
      KeyEvent.VK_E,
      KeyEvent.VK_F,
      KeyEvent.VK_G,
      KeyEvent.VK_NUMBER_SIGN,
  };

  public MusicController(MusicPieceInterface musicPiece, ComboView comboView) throws InterruptedException, MidiUnavailableException {
    this.comboView = comboView;
    this.keyListener = new KeyboardHandler();
    this.keySequence = new ArrayList<>();


    // add callbacks to keyboard handler
    for (int keyCode : this.addKeys) {
      this.keyListener.addRunnable(keyCode, new AddKeyRunnable(this, keyCode));
    }

    this.keyListener.addRunnable(KeyEvent.VK_ESCAPE, new ClearKeyRunnable(this));
    this.keyListener.addRunnable(KeyEvent.VK_ENTER, new EnterKeyRunnable(this));
    this.keyListener.addRunnable(KeyEvent.VK_SPACE, new PauseKeyRunnable(this));
    this.keyListener.addRunnable(KeyEvent.VK_HOME, new HomeKeyRunnable(this));
    this.keyListener.addRunnable(KeyEvent.VK_END, new EndKeyRunnable(this));
    this.keyListener.addRunnable(KeyEvent.VK_LEFT, new LeftKeyRunnable(this));
    this.keyListener.addRunnable(KeyEvent.VK_RIGHT, new LeftKeyRunnable(this));


    this.comboView.addGUIKeyListener(this.keyListener);
    //this.comboView.initialize();
  }

  /**
   * add a key to the tracked sequence of keys
   *
   * @param keyCode keycode to add to the sequence
   */
  public void addKey(int keyCode) {
    this.keySequence.add(keyCode);
  }

  /**
   * Clear out the tracked sequence of input keys
   */
  public void clearKeys() {
    this.keySequence.clear();
  }

  /**
   * TODO: delete a note c5 starting on beat 64 c5 d 64 add a note in c5 on beat 64 lasting 5 beats
   * c5 a 64 5 move a note in c5 on beat c4 to d3 on beat 75 maintaining length c5 m 64 d3 75 arrow
   * keys to scroll through composition based on offset space bar to play/pause/restart composition
   * home/end keys to move to the beginning/end of composition
   */

  public void processKeySequence() {
    // TODO: perform actions on music piece based on parsed key sequence
    // parse sequence of keys to either add, delete, or move a no
  }

  public void pausePlayback() throws InterruptedException, MidiUnavailableException {
    this.comboView.playPause();
  }

  public void moveToStart() {
    // TODO: move red line + position to start of music piece
  }

  public void moveToEnd() {
    // TODO: move red line + position to end of music piece
  }

  public void moveLeft() throws InterruptedException, MidiUnavailableException {
    this.comboView.scroll(false);
  }

  public void moveRight() throws InterruptedException, MidiUnavailableException {
    this.comboView.scroll(true);
  }


  // like a billion runnables
  public class AddKeyRunnable implements Runnable {
    MusicController musicController;
    int keyCode;

    AddKeyRunnable(MusicController musicController, int keyCode) {
      this.musicController = musicController;
      this.keyCode = keyCode;
    }

    @Override
    public void run() {
      this.musicController.addKey(this.keyCode);
    }
  }

  public class ClearKeyRunnable implements Runnable {
    MusicController musicController;

    ClearKeyRunnable(MusicController musicController) {
      this.musicController = musicController;
    }

    @Override
    public void run() {
      this.musicController.clearKeys();
    }
  }

  public class EnterKeyRunnable implements Runnable {
    MusicController musicController;

    EnterKeyRunnable(MusicController musicController) {
      this.musicController = musicController;
    }

    @Override
    public void run() {
      this.musicController.processKeySequence();
    }
  }

  public class PauseKeyRunnable implements Runnable {
    MusicController musicController;

    PauseKeyRunnable(MusicController musicController) {
      this.musicController = musicController;
    }

    @Override
    public void run() {
      try {
        this.musicController.pausePlayback();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (MidiUnavailableException e) {
        e.printStackTrace();
      }
    }
  }

  public class HomeKeyRunnable implements Runnable {
    MusicController musicController;

    HomeKeyRunnable(MusicController musicController) {
      this.musicController = musicController;
    }

    @Override
    public void run() {
      this.musicController.moveToStart();
    }
  }

  public class EndKeyRunnable implements Runnable {
    MusicController musicController;

    EndKeyRunnable(MusicController musicController) {
      this.musicController = musicController;
    }

    @Override
    public void run() {
      this.musicController.moveToEnd();
    }
  }

  public class LeftKeyRunnable implements Runnable {
    MusicController musicController;

    LeftKeyRunnable(MusicController musicController) {
      this.musicController = musicController;
    }

    @Override
    public void run() {
      try {
        this.musicController.moveLeft();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (MidiUnavailableException e) {
        e.printStackTrace();
      }
    }
  }

  public class RightKeyRunnable implements Runnable {
    MusicController musicController;

    RightKeyRunnable(MusicController musicController) {
      this.musicController = musicController;
    }

    @Override
    public void run() {
      try {
        this.musicController.moveRight();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (MidiUnavailableException e) {
        e.printStackTrace();
      }
    }
  }
}
