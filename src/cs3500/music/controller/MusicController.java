package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.sound.midi.MidiUnavailableException;

import cs3500.music.model.MusicPieceInterface;
import cs3500.music.view.ComboView;

public class MusicController  {

    //GuiViewFrame guiView; // TODO: I think this should be an interface instead
    //MidiViewImpl midiView;
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

    public MusicController(MusicPieceInterface musicPiece) throws InterruptedException, MidiUnavailableException {
        this.comboView = new ComboView(musicPiece);
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
        this.comboView.initialize();

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

    public void processKeySequence() {
        // TODO: perform actions on music piece based on parsed key sequence
        // parse sequence of keys to either add, delete, or move a note

    }

    public void pausePlayback() {
        // TODO: perform actions on music piece based on parsed key sequence
    }

    public void moveToStart() {
        // TODO: move red line + position to start of music piece
    }

    public void moveToEnd() {
        // TODO: move red line + position to end of music piece
    }

    public void moveLeft() {
        // TODO: move red line + position to left of current pos in music piece
    }

    public void moveRight() {
        // TODO: move red line + position to left of current pos in music piece
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
            this.musicController.pausePlayback();
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
            this.musicController.moveLeft();
        }
    }

    public class RightKeyRunnable implements Runnable {
        MusicController musicController;

        RightKeyRunnable(MusicController musicController) {
            this.musicController = musicController;
        }

        @Override
        public void run() {
            this.musicController.moveRight();
        }
    }
}