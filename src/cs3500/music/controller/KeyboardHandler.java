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
  Map<Integer, Runnable> released;

  public KeyboardHandler() {
    this.released = new TreeMap<Integer, Runnable>();
  }

  // not really used
  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
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

}
