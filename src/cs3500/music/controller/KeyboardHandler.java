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
