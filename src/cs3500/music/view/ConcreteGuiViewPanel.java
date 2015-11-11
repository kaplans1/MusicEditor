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
  public static final int gridAllign = 50;
  public static final int beatCubeSize = 20;
  MusicPiece notes;

  public ConcreteGuiViewPanel(MusicPiece notes) {
    this.notes = notes;
  }
  public ConcreteGuiViewPanel() {
    this.notes = null;
  }

  @Override
  public void paint(Graphics g) {
    //TODO: Check if notes are not null
    //each beat 20 px wide
    //code to check that width of board is correct
    int width = notes.getlastBeat();
    int height = notes.getAllPitchIds().size();
    //draws all necessary rows
    for (int i = 0; i <= height; i++) {
      g.drawLine(gridAllign, gridAllign + i * beatCubeSize, width * (beatCubeSize + 1), gridAllign + i * beatCubeSize);
    }

    //draws all pitch names
    //TODO: have the list be sorted by pitchID
    for (int i = 0; i < notes.getAllPitches().size(); i++) {
      g.drawString(notes.getAllPitches().get(i), gridAllign - 2*beatCubeSize, gridAllign + i * beatCubeSize + 2 * beatCubeSize / 3);
    }
    //draws all columns based on tempo and adds beat labels
    for (int i = 0; i <= width / 4; i++) {
      g.drawLine(gridAllign + i * beatCubeSize * notes.getBPM(), gridAllign, gridAllign + i * beatCubeSize * notes.getBPM(), gridAllign + height * beatCubeSize);
      g.drawString((i * 4) + "", gridAllign + i * beatCubeSize * notes.getBPM(), gridAllign - beatCubeSize / 2);
    }

    //draws notes!
    for (int i = 0; i <= width; i++) {
      if (notes.getAllNotes().get(i) != null) {
        ArrayList<MusicNote> curr = notes.getAllNotes().get(i);
        for (MusicNote n : curr) {
          int noteSize = n.endBeat() - n.startBeat;
          int row = notes.getAllPitches().indexOf(n.noteName());
          for (int j = 0; j <= noteSize; j++) {
            if(j == 0) {
              g.setColor(Color.RED);
              g.fillRect(n.getStartBeat()*beatCubeSize + gridAllign + j * beatCubeSize + 1, gridAllign+ row*beatCubeSize + 1,
                      beatCubeSize - 2, beatCubeSize - 2);
            } else {
              g.setColor(Color.BLACK);
              g.fillRect(n.getStartBeat() * beatCubeSize + gridAllign + j * beatCubeSize + 1, gridAllign+ row*beatCubeSize + 1,
                      beatCubeSize - 2, beatCubeSize - 2);
            }
          }
        }
      }
    }
    //adds pitch ID to beginning of each row


  }


}
