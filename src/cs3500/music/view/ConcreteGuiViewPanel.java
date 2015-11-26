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
import cs3500.music.model.MusicPieceInterface;

/**
 * A dummy view that simply draws a string
 */
public class ConcreteGuiViewPanel extends JPanel {

  public static final int beatCubeSize = 20;
  public static final int gridAllign = beatCubeSize * 2;
  MusicPieceInterface notes;
  int startBeat;
  int currentBeat;
  public static final int width = 80;

  public ConcreteGuiViewPanel(MusicPieceInterface notes, int startBeat) {
    this.notes = notes;
    this.startBeat = startBeat;
    this.currentBeat = startBeat;
  }


  @Override
  public void paint(Graphics g) {
    //TODO: Check if notes are not null
    //each beat 20 px wide
    //code to check that width of board is correct

    //this.notes.getLastBeat();
    g.fillRect(0, 0, 5000, 5000);
    if(this.currentBeat-this.startBeat>80) {
      if(this.startBeat%2 !=0){
        this.startBeat = this.currentBeat;
      } else {
        this.startBeat = this.currentBeat-1;
      }
    }


    int height = this.notes.getAllPitchIds().size();
    //draws all necessary rows
    for (int i = 0; i <= height; i++) {
      g.setColor(Color.white);
      g.drawLine(gridAllign, gridAllign + i * beatCubeSize,
          gridAllign + width * (beatCubeSize + 1),
          gridAllign + i * beatCubeSize);
    }
    //draws pitches
    for (int i = 0; i < this.notes.getAllPitches().size(); i++) {
      g.drawString(this.notes.getAllPitches().get(i), gridAllign - 2 * beatCubeSize,
          gridAllign + i * beatCubeSize + 2 * beatCubeSize / 3);
    }
    //draws all columns based on tempo
    for (int i = 0; i <= width / 4; i++) {
      g.setColor(Color.white);
      g.drawLine(gridAllign + i * beatCubeSize * this.notes.getBPM(), gridAllign,
          gridAllign + i * beatCubeSize * this.notes.getBPM(), gridAllign + height * beatCubeSize);
    }
    //beat labels
    int k = 0;
    for (int i = startBeat; i <= startBeat + width; i++) {
      g.drawString(startBeat+ k +  "",
          gridAllign + (k/4) * beatCubeSize * this.notes.getBPM(),
          gridAllign - beatCubeSize / 2);
      k+=4;
    }
    //draws notes from start beat to selected width
    for (int i = startBeat; i <= startBeat + width; i++) {
      if (this.notes.getAllNotes().get(i) != null) {
        ArrayList<MusicNote> curr = this.notes.getAllNotes().get(i);
        for (MusicNote n : curr) {
          int noteLength = n.getEndBeat() - n.getStartBeat();
          int row = this.notes.getAllPitches().indexOf(n.noteName());
          for (int j = 0; j <= noteLength; j++) {
            if (j == 0) {
              g.setColor(Color.GREEN);
            } else {
              g.setColor(Color.RED);
            }

            g.fillRect(n.getStartBeat() * beatCubeSize + gridAllign + j * beatCubeSize + 1 - startBeat * beatCubeSize,
                gridAllign + row * beatCubeSize + 1, beatCubeSize - 2, beatCubeSize - 2);
          }
        }
      }
    }
    this.redLine(g, this.currentBeat);
  }

  public void redLine(Graphics g, int beat) {
    this.invalidate();
    this.revalidate();
    this.repaint();
    int x = beat;
    g.setColor(Color.RED);
    if (beat >= this.width) {
      x = beat % this.width;
    }
    g.drawLine(gridAllign + (x * beatCubeSize), gridAllign,
        gridAllign + (x * beatCubeSize),
        gridAllign + this.notes.getAllPitchIds().size() * beatCubeSize);
  }


  public void setStartBeat(int startBeat) {
    this.startBeat = startBeat;
  }

  public ConcreteGuiViewPanel() {
    this.notes = null;
  }

  public void setCurrentBeat(int currentBeat) {
    this.currentBeat = currentBeat;
  }
}
