package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.TreeMap;

import javax.sound.midi.MidiUnavailableException;

import cs3500.music.model.MusicPieceInterface;
import cs3500.music.view.ComboView;

/**
 * Created by AviSion on 11/19/2015.
 */
public class MouseHandler implements MouseListener {
  Map<Integer, Runnable> released;
  //variable to store where a click happened
  int x;

  public MouseHandler(){
    this.released = new TreeMap<Integer, Runnable>();
  }

  public void addRunnable2(int mouseClicked, Runnable runnable) {
    this.released.put(mouseClicked, runnable);
  }


  @Override
  public void mouseClicked(MouseEvent e) {
    if (released.containsKey(e.getID())) {
      released.get(e.getID()).run();
    }
    this.x = e.getX();
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (released.containsKey(e.getID())) {
      released.get(e.getID()).run();
    }
    this.x = e.getX();

  }

  @Override
  public void mouseReleased(MouseEvent e)
  {
    if (released.containsKey(e.getID())) {
      released.get(e.getID()).run();
    }
    this.x = e.getX();
  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }




}
