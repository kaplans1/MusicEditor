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
  public static final int gridAllign = beatCubeSize*2;
  MusicPieceInterface notes;
  int startBeat;

  public ConcreteGuiViewPanel(MusicPieceInterface notes, int startBeat) {
    this.notes = notes;
    this.startBeat = startBeat;
  }
  public ConcreteGuiViewPanel() {
    this.notes = null;
  }

  @Override
  public void paint(Graphics g) {
    //TODO: Check if notes are not null
    //each beat 20 px wide
    //code to check that width of board is correct
    int width = 80;
            //notes.getLastBeat();
    System.out.println(width);
    int height = notes.getAllPitchIds().size();
    //draws all necessary rows
    for (int i = 0; i <= height; i++) {
      g.drawLine(gridAllign, gridAllign + i * beatCubeSize,
              gridAllign + width * (beatCubeSize + 1),
              gridAllign + i * beatCubeSize);
    }

    for (int i = 0; i < notes.getAllPitches().size(); i++) {
      g.drawString(notes.getAllPitches().get(i), gridAllign - 2*beatCubeSize,
              gridAllign + i * beatCubeSize + 2 * beatCubeSize / 3);
    }
    //draws all columns based on tempo and adds beat labels
    for (int i = 0; i <= width / 4; i++) {
      g.drawLine(gridAllign + i * beatCubeSize * notes.getBPM(), gridAllign,
              gridAllign + i * beatCubeSize * notes.getBPM(), gridAllign + height * beatCubeSize);
      g.drawString((i * 4) + "", gridAllign + i * beatCubeSize * notes.getBPM(),
              gridAllign - beatCubeSize / 2);
    }

    //draws notes from start beat to selected width
    for (int i = startBeat; i <= startBeat+ width; i++) {
      if (notes.getAllNotes().get(i) != null) {
        ArrayList<MusicNote> curr = notes.getAllNotes().get(i);
        for (MusicNote n : curr) {
          int noteLength = n.getEndBeat() - n.getStartBeat();
          int row = notes.getAllPitches().indexOf(n.noteName());
          for (int j = 0; j <=  noteLength; j++) {
            if(j == 0) {
              g.setColor(Color.BLACK);
            } else {
              g.setColor(Color.RED);
            }

            g.fillRect(n.getStartBeat() * beatCubeSize + gridAllign + j * beatCubeSize + 1 - startBeat * beatCubeSize,
                    gridAllign+ row*beatCubeSize + 1,
                    beatCubeSize - 2, beatCubeSize - 2);
          }
        }
      }
    }

  //  this.redLine(g, 0);
  }


  public void drawGraphic() {
    repaint();
  }

//  public void redLine(Graphics g, int time){
//    this.getGraphics().setColor(Color.RED);
//    this.getGraphics().drawLine(gridAllign + time, gridAllign,
//            gridAllign + time, gridAllign + notes.getAllPitchIds().size() * beatCubeSize);
//  }


}
