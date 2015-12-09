package cs3500.music.view2;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.font.GraphicAttribute;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;

import cs3500.music.controller.MouseHandler;
import cs3500.music.view2.Composition;
import cs3500.music.view2.Model;
import cs3500.music.view2.Note;
import cs3500.music.view2.Playable;

/**
 *  Concrete panels for creating
 */
public class GridPanel extends JPanel {
  Model c;
  int numOctaves;
  int numBeats;
  int numMeasures;
  ArrayList<Integer> octaves;


  public GridPanel(Model c) {
    this.c = c;
    this.numOctaves = c.getOctaveInterval().size();
    this.numBeats = c.getNumBeats();
    this.numMeasures = numBeats / 4;
    octaves = c.getOctaveInterval();

//    MouseListener m = new MouseHandler();
//    this.addMouseHandler(m);
  }
  //MEOW
  public void cats() {
    this.numOctaves = c.getOctaveInterval().size();
    this.numBeats = c.getNumBeats();
    this.numMeasures = numBeats / 4;
    octaves = c.getOctaveInterval();
  }

  int startX = 40;
  int startY = 40;

  int linePosition = 0;

  @Override
  public void paint(Graphics g){
    this.cats();
    super.paintComponent(g);
    this.setBackground(new Color(180, 250, 217));
    drawAllNotes(g);
    g.setColor(Color.BLACK);
    drawGrid(g);
    drawBeatNums(g);
    g.setColor(Color.RED);
    drawLine(g);
  }

  int h;
  int w;

  @Override
  public Dimension getPreferredSize() {
    h = (c.getOctaveInterval().size() * 120) + 100;
    w = (c.getNumBeats() * 10) + 100;
    return new Dimension(w, h);
  }


  /**
   * increments the position of the line which marks the current beat
   */
  public void incrementLinePosition() {
    if (linePosition < c.getNumBeats()) {
      linePosition += 1;
    }
  }

  public int getH() {
    return this.h;
  }

  public int getW() {
    return this.w;
  }

  public void addMouseHandler(MouseHandler mh) {
    this.addMouseListener(mh);
  }

  private void drawLine(Graphics g) {
    g.drawLine((linePosition * 10) + 40, 40, (linePosition * 10) + 40, h - 40);
  }

  /**
   * draws the grid
   */
  private void drawGrid(Graphics g) {
    for (int i = 0; i < numOctaves; i++) {
      drawOctave(g, i);
    }
  }

  //draws a single octave which is the octNum of the one specified
  private void drawOctave(Graphics g, int octNum) {
    for (int i = 1; i <= 12; i++) {
      drawRow(g, i + (octNum * 12), octNum, i);
    }
  }

  //draws a single row at the line specified by rowNum
  private void drawRow (Graphics g, int rowNum, int octNum, int pitch) {
    String pitchString = "";

    ArrayList<Integer> tempOctave = new ArrayList<>();

    for (int i = octaves.size() - 1; i >= 0; i--) {
      tempOctave.add(octaves.get(i));
    }

    int oct = tempOctave.get(octNum);


    switch (pitch) {
      case 1: pitchString = String.format("B %d", oct);
        break;
      case 2: pitchString = String.format("A# %d", oct);
        break;
      case 3: pitchString = String.format("A %d", oct);
        break;
      case 4: pitchString = String.format("G# %d", oct);
        break;
      case 5: pitchString = String.format("G %d", oct);
        break;
      case 6: pitchString = String.format("F# %d", oct);
        break;
      case 7: pitchString = String.format("F %d", oct);
        break;
      case 8: pitchString = String.format("E %d", oct);
        break;
      case 9: pitchString = String.format("D# %d", oct);
        break;
      case 10: pitchString = String.format("D %d", oct);
        break;
      case 11: pitchString = String.format("C# %d", oct);
        break;
      case 12: pitchString = String.format("C %d", oct);
        break;


    }
    //draws a single row
    for (int i = 1; i <= numMeasures; i++) {
      g.drawRect(startX * i, startY + (10 * rowNum), 40, 10);
      g.drawString(pitchString, 5, 48 + (rowNum * 10));
    }
  }


  private void drawBeatNums(Graphics g) {
    for (int i = 0; i <= numMeasures; i++) {
      if (i % 4 == 0) {
        g.drawString((String.format("%d", i * 4)), 40 + (i * 40), 40);
      }
    }
  }

  /**
   * Draws the every note of this composition into it's proper spot
   */
  private void drawAllNotes(Graphics g) {
    for (int i = 0; i < c.getNumBeats(); i++) {
      ArrayList<Playable> notes = c.notesAtBeat(i);
      for (int j = 0; j < notes.size(); j++) {
        drawANote(g, (Note) notes.get(j), i);
      }
    }
  }

  private void drawANote(Graphics g, Note note, int beatNum) {
    int lowestOctave = octaves.get(0);
    int noteX = 40 + (beatNum * 10);
    int noteY = this.getSize().height - 60 - ((note.getMidiIndex() - (lowestOctave * 12))*10);

    boolean isAttack = note.getIsAttack();
    Color noteColor;

    if (isAttack) {
      noteColor = new Color(0, 0, 0);
    }
    else {
      noteColor = new Color(224, 130, 131);
    }

    g.drawRect(noteX, noteY, 10, 10);
    g.setColor(noteColor);
    g.fillRect(noteX , noteY, 10, 10);
  }
}
