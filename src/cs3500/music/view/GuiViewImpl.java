package cs3500.music.view;

import java.awt.*;
import java.awt.event.MouseListener; // Possibly of interest for handling mouse events
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.*;

import cs3500.music.model.MusicNote;
import cs3500.music.model.MusicPiece;
import cs3500.music.model.MusicPieceInterface;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewImpl extends javax.swing.JFrame implements GuiView {

  private ConcreteGuiViewPanel displayPanel; // You may want to refine this to a subtype of JPanel
  JFrame frame = new JFrame();
  private MusicPieceInterface piece;
  private int startBeat;

  /**
   * Creates new GuiView
   */

  public MusicPieceInterface getPiece(){
    return this.piece;
  }

  public void setCurrentBeat(int beat) {this.displayPanel.setCurrentBeat(beat);}

  public GuiViewImpl() {
    this.displayPanel = new ConcreteGuiViewPanel();
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.getContentPane().add(displayPanel);
    this.pack();
    this.piece = null;
  }

  public GuiViewImpl(MusicPieceInterface n) {
    this.piece = n;
    this.startBeat = 0;
    this.displayPanel = new ConcreteGuiViewPanel(piece, startBeat);
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.getContentPane().add(displayPanel);
    this.pack();

  }

  @Override
  public void initialize() {
    this.setVisible(true);

  }

  @Override
  public Dimension getPreferredSize() {

    return new Dimension(piece.getLastBeat() * 22 + ConcreteGuiViewPanel.gridAllign,
            piece.getAllPitches().size() * 22 + 100);

  }

  public void updateStartDisplayBeat(int beat){
    this.displayPanel.invalidate();
    this.displayPanel.getGraphics().dispose();
    this.displayPanel.updateUI();
    this.displayPanel.setVisible(false);


    this.displayPanel = new ConcreteGuiViewPanel(piece, beat);
    this.displayPanel.setVisible(true);
    this.getContentPane().add(displayPanel);
    this.pack();
  }

  public void redraw() {
    this.displayPanel.revalidate();
    this.displayPanel.repaint();
  }
}