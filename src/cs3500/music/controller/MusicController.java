package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import cs3500.music.model.MusicNote;
import cs3500.music.model.MusicPieceInterface;
import cs3500.music.view.ComboView;

public class MusicController {
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
          KeyEvent.VK_X,
          KeyEvent.VK_N,
          KeyEvent.VK_M,
          KeyEvent.VK_L,
          KeyEvent.VK_O,
          KeyEvent.VK_P,
          KeyEvent.VK_NUMBER_SIGN,
  };
  MusicPieceInterface musicPiece;
  ComboView comboView;
  public KeyboardHandler keyListener;
  ArrayList<Integer> keySequence;
  int x;
  MouseHandler mouseHandler;

  public MusicController(MusicPieceInterface musicPiece, ComboView comboView) throws InterruptedException, MidiUnavailableException {
    this.musicPiece = musicPiece;
    this.comboView = comboView;
    this.keyListener = new KeyboardHandler();
    this.keySequence = new ArrayList<>();
    this.mouseHandler = new MouseHandler();


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
    this.keyListener.addRunnable(KeyEvent.VK_RIGHT, new RightKeyRunnable(this));

    this.comboView.addGUIKeyListener(this.keyListener);

    this.mouseHandler.addRunnable2(MouseEvent.MOUSE_CLICKED, new MouseClickRunnable(this));

    this.comboView.addGUIMouseListener(this.mouseHandler);

  }

  /**
   * add a key to the tracked sequence of keys
   *
   * @param keyCode keycode to add to the sequence
   */
  public void addKey(int keyCode) {
    System.out.println("added key");
    System.out.println(keyCode);
    this.keySequence.add(keyCode);
  }

  /**
   * Clear out the tracked sequence of input keys
   */
  public void clearKeys() {
    this.keySequence.clear();
  }

  /**
   * TODO: delete a note c5 starting on beat 64 c5 x 64 add a note in c5 on beat 64 lasting 5 beats
   * c5 n 64 l 5 move a note in c5 on beat c4 to d3 on beat 75 maintaining length c5 m 64 d3 o 75
   * arrow keys to scroll through composition based on offset space bar to play/pause/restart
   * composition home/end keys to move to the beginning/end of composition
   */

  public void processKeySequence() {
    // parse sequence of keys to either add, delete, or move a note

    // build string sequence from arraylist
    String keySequence = "";

    for (int keyCode : this.keySequence) {
      keySequence += (char) keyCode;
    }

    keySequence = keySequence.toLowerCase();

    System.out.println(keySequence);


    Pattern addNotePattern = Pattern.compile("([abcdefg]#?)([0-9])n([0-9]+)l([0-9]+)");
    Pattern deleteNotePattern = Pattern.compile("([abcdefg]#?)([0-9])x([0-9]+)");
    Pattern moveNotePattern = Pattern.compile("([abcdefg]#?)([0-9])m([0-9]+)([abcdefg]#?)([0-9])o([0-9]+)");

    Pattern addThirdPattern = Pattern.compile("([abcdefg]#?)([0-9])p([0-9]+)");


    Matcher addMatcher = addNotePattern.matcher(keySequence);
    Matcher deleteMatcher = deleteNotePattern.matcher(keySequence);
    Matcher moveMatcher = moveNotePattern.matcher(keySequence);

    Matcher thirdMatcher = addThirdPattern.matcher(keySequence);

    boolean addMatch = addMatcher.matches();
    boolean deleteMatch = deleteMatcher.matches();
    boolean moveMatch = moveMatcher.matches();

    boolean thirdMatch = thirdMatcher.matches();

    System.out.println(addMatch);
    System.out.println(deleteMatch);
    System.out.println(moveMatch);

    System.out.println(thirdMatch);

    if (addMatch) {
      System.out.println("add match");
      String addNote = addMatcher.group(1);
      String addOctave = addMatcher.group(2);
      int addBeat = Integer.parseInt(addMatcher.group(3));
      int addLength = Integer.parseInt(addMatcher.group(4));

      System.out.println(addNote);
      System.out.println(addOctave);
      System.out.println(addBeat);
      System.out.println(addLength);

      int addNoteID = MusicNote.pitchIDFromString(addNote, addOctave);

      // TODO: volume and instrument?
      this.musicPiece.addNote(new MusicNote(addNoteID, addBeat, addLength, 0, 100));
    } else if (deleteMatch) {
      System.out.println("delete match");
      String deleteNote = deleteMatcher.group(1);
      String deleteOctave = deleteMatcher.group(2);
      int deleteBeat = Integer.parseInt(deleteMatcher.group(3));
      int deleteNoteID = MusicNote.pitchIDFromString(deleteNote, deleteOctave);

      this.musicPiece.deleteNote(deleteNoteID, deleteBeat);
    } else if (moveMatch) {
      System.out.println("move match");
      String moveFromNote = moveMatcher.group(1);
      String moveFromOctave = moveMatcher.group(2);
      int moveFromBeat = Integer.parseInt(moveMatcher.group(3));
      int moveFromNoteID = MusicNote.pitchIDFromString(moveFromNote, moveFromOctave);
      MusicNote note = this.musicPiece.getNote(moveFromNoteID, moveFromBeat);
      int moveFromLength = note.getLength();
      int moveFromInstrument = note.getInstrument();
      int moveFromVolume = note.getVolume();
      String moveToNote = moveMatcher.group(4);
      String moveToOctave = moveMatcher.group(5);
      int moveToNoteID = MusicNote.pitchIDFromString(moveToNote, moveToOctave);
      int moveToBeat = Integer.parseInt(moveMatcher.group(6));

      this.musicPiece.deleteNote(moveFromNoteID, moveFromBeat);
      this.musicPiece.addNote(new MusicNote(moveToNoteID, moveToBeat, moveFromLength,
              moveFromInstrument, moveFromVolume));
    } else if (thirdMatch) {
      System.out.println("third match");
      String addNote = thirdMatcher.group(1);
      String addOctave = thirdMatcher.group(2);
      int addBeat = Integer.parseInt(thirdMatcher.group(3));

      int moveFromBeat = Integer.parseInt(thirdMatcher.group(3));
      int moveFromNoteID = MusicNote.pitchIDFromString(addNote, addOctave);
      MusicNote note = this.musicPiece.getNote(moveFromNoteID, moveFromBeat);
      int addLength = note.getLength();

      System.out.println(addNote);
      System.out.println(addOctave);
      System.out.println(addBeat);
      System.out.println(addLength);

      int addNoteID = MusicNote.pitchIDFromString(addNote, addOctave) + 2;

      // TODO: volume and instrument?
      this.musicPiece.addNote(new MusicNote(addNoteID, addBeat, addLength, 0, 100));
    }


    this.clearKeys();
    this.comboView.redraw();
  }

  public void pausePlayback() throws InterruptedException, MidiUnavailableException {
    this.comboView.playPause();
  }

  public void moveToStart() throws MidiUnavailableException, InterruptedException, InvalidMidiDataException {
    this.comboView.goTo("start");
    // TODO: move red line + position to start of music piece
  }

  public void moveToEnd() {
    this.comboView.goTo("end");
    // TODO: move red line + position to end of music piece
  }

  public void moveLeft() throws InterruptedException, MidiUnavailableException {
    this.comboView.scroll(false);
  }

  public void moveRight() throws InterruptedException, MidiUnavailableException {
    this.comboView.scroll(true);
  }

  public void clicked() throws InterruptedException, MidiUnavailableException {
    //scroll to e.x();
    this.comboView.scrollTo(0);
  }

  // like two billion runnables
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
      try {
        this.musicController.moveToStart();
      } catch (MidiUnavailableException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (InvalidMidiDataException e) {
        e.printStackTrace();
      }
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

  public class MouseClickRunnable implements Runnable {
    MusicController musicController;

    MouseClickRunnable(MusicController musicController) {
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



  public void processKeySequenceTest(String input) {
    // parse sequence of keys to either add, delete, or move a note

    // build string sequence from arraylist
    String keySequence = "";
//
//    for (int keyCode : this.keySequence) {
//      keySequence += (char) keyCode;
//    }

    keySequence = input.toLowerCase();

    System.out.println(keySequence);


    Pattern addNotePattern = Pattern.compile("([abcdefg]#?)([0-9])n([0-9]+)l([0-9]+)");
    Pattern deleteNotePattern = Pattern.compile("([abcdefg]#?)([0-9])x([0-9]+)");
    Pattern moveNotePattern = Pattern.compile("([abcdefg]#?)([0-9])m([0-9]+)([abcdefg]#?)([0-9])o([0-9]+)");

    Pattern addThirdPattern = Pattern.compile("([abcdefg]#?)([0-9])p([0-9]+)");


    Matcher addMatcher = addNotePattern.matcher(keySequence);
    Matcher deleteMatcher = deleteNotePattern.matcher(keySequence);
    Matcher moveMatcher = moveNotePattern.matcher(keySequence);

    Matcher thirdMatcher = addThirdPattern.matcher(keySequence);

    boolean addMatch = addMatcher.matches();
    boolean deleteMatch = deleteMatcher.matches();
    boolean moveMatch = moveMatcher.matches();

    boolean thirdMatch = thirdMatcher.matches();

    System.out.println(addMatch);
    System.out.println(deleteMatch);
    System.out.println(moveMatch);

    System.out.println(thirdMatch);

    if (addMatch) {
      System.out.println("add match");
      String addNote = addMatcher.group(1);
      String addOctave = addMatcher.group(2);
      int addBeat = Integer.parseInt(addMatcher.group(3));
      int addLength = Integer.parseInt(addMatcher.group(4));

      System.out.println(addNote);
      System.out.println(addOctave);
      System.out.println(addBeat);
      System.out.println(addLength);

      int addNoteID = MusicNote.pitchIDFromString(addNote, addOctave)+12;

      // TODO: volume and instrument?
      this.musicPiece.addNote(new MusicNote(addNoteID, addBeat, addLength, 0, 100));
    } else if (deleteMatch) {
      System.out.println("delete match");
      String deleteNote = deleteMatcher.group(1);
      String deleteOctave = deleteMatcher.group(2);
      int deleteBeat = Integer.parseInt(deleteMatcher.group(3));
      int deleteNoteID = MusicNote.pitchIDFromString(deleteNote, deleteOctave)+3;

      this.musicPiece.deleteNote(deleteNoteID, deleteBeat);
    } else if (moveMatch) {
      System.out.println("move match");
      String moveFromNote = moveMatcher.group(1);
      String moveFromOctave = moveMatcher.group(2);
      int moveFromBeat = Integer.parseInt(moveMatcher.group(3));
      int moveFromNoteID = MusicNote.pitchIDFromString(moveFromNote, moveFromOctave)+13;
      MusicNote note = this.musicPiece.getNote(moveFromNoteID, moveFromBeat);
      int moveFromLength = note.getLength();
      int moveFromInstrument = note.getInstrument();
      int moveFromVolume = note.getVolume();
      String moveToNote = moveMatcher.group(4);
      String moveToOctave = moveMatcher.group(5);
      int moveToNoteID = MusicNote.pitchIDFromString(moveToNote, moveToOctave);
      int moveToBeat = Integer.parseInt(moveMatcher.group(6));

      this.musicPiece.deleteNote(moveFromNoteID, moveFromBeat);
      this.musicPiece.addNote(new MusicNote(moveToNoteID, moveToBeat, moveFromLength,
          moveFromInstrument, moveFromVolume));
    } else if (thirdMatch) {
      System.out.println("third match");
      String addNote = thirdMatcher.group(1);
      String addOctave = thirdMatcher.group(2);
      int addBeat = Integer.parseInt(thirdMatcher.group(3));

      int moveFromBeat = Integer.parseInt(thirdMatcher.group(3));
      int moveFromNoteID = MusicNote.pitchIDFromString(addNote, addOctave);
      MusicNote note = this.musicPiece.getNote(moveFromNoteID, moveFromBeat);
      int addLength = note.getLength();

      System.out.println(addNote);
      System.out.println(addOctave);
      System.out.println(addBeat);
      System.out.println(addLength);

      int addNoteID = MusicNote.pitchIDFromString(addNote, addOctave) + 2;

      // TODO: volume and instrument?
      this.musicPiece.addNote(new MusicNote(addNoteID, addBeat, addLength, 0, 100));
    }


    this.clearKeys();
    this.comboView.redraw();
  }

  public MusicPieceInterface getMusicPiece(){
    return this.musicPiece;
  }
}
