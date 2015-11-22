package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;

/**
 * Created by AviSion on 11/19/2015.
 */
public class KeyboardHandler extends JFrame implements KeyListener {
  Map<Integer, Runnable> typed;
  Map<Integer, Runnable> pressed;
  Map<Integer, Runnable> released;

  /**
    * TODO:
    * delete a note c5 starting on beat 64
    * c5 d 64
    * add a note in c5 on beat 64 lasting 5 beats
    * c5 a 64 5
    * move a note in c5 on beat c4 to d3 on beat 75 maintaining length
    * c5 m 64 d3 75
    * arrow keys to scroll through composition based on offset
    * space bar to play/pause/restart composition
    * home/end keys to move to the beginning/end of composition
    */

  public KeyboardHandler() {
    this.typed = new TreeMap<Integer, Runnable>();
    this.pressed = new TreeMap<Integer, Runnable>();
    this.released = new TreeMap<Integer, Runnable>();
  }

  // not really used
  @Override
  public void keyTyped(KeyEvent e) {
    //if (typed.containsKey(e.getKeyCode())) {
    //  typed.get(e.getKeyCode()).run();
    //}
  }

  @Override
  public void keyPressed(KeyEvent e) {
    //if (pressed.containsKey(e.getKeyCode())) {
    //  pressed.get(e.getKeyCode()).run();
    //}
  }

  // most of the code for keysequences lives here
  @Override
  public void keyReleased(KeyEvent e) {
    if (released.containsKey(e.getKeyCode())) {
      released.get(e.getKeyCode()).run();
    }

  }

  public void addRunnable(int keyCode, Runnable runnable) {
    this.released.put(keyCode, runnable);
  }

  //adds to maps
  protected void addAction(int x, Runnable r, String type) {
    //TODO: Need to figure out which type of runnable is passed in
    if (type.equals("typed")) {
      typed.put(x, r);
    } else if (type.equals("pressed")) {

    } else if (type.equals("released")) {

    } else {
      throw new IllegalArgumentException("Should be unreachable");
    }
  }
}
