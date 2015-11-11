package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

import javax.swing.*;

import cs3500.music.model.MusicNote;
import cs3500.music.model.MusicPiece;

/**
 * A dummy view that simply draws a string 
 */
public class ConcreteGuiViewPanel extends JPanel {
   MusicPiece notes;

  public ConcreteGuiViewPanel(MusicPiece notes){
    this.notes=notes;
  }
  public ConcreteGuiViewPanel(){
    this.notes=null;
  }
  @Override
  public void paint(Graphics g){
  //TODO: Check if notes are not null
    //each beat 20 px wide
    //code to check that width of board is correct
    g.drawLine(5, 5, notes.getlastBeat() * 20, 5);
    g.drawLine(5, 10, 14 * 20, 10);
  }



}
