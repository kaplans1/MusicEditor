package cs3500.music.view;

import java.awt.*;

import javax.swing.*;

import cs3500.music.model.MusicPieceInterface;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewOursImpl extends javax.swing.JFrame implements GuiViewOurs {

  private ConcreteGuiViewPanel displayPanel; // You may want to refine this to a subtype of JPanel
  JFrame frame = new JFrame();
  private MusicPieceInterface piece;
  private int startBeat;

  /**
   * Creates new GuiViewOurs
   */

  public MusicPieceInterface getPiece(){
    return this.piece;
  }

  public void setCurrentBeat(int beat) {this.displayPanel.setCurrentBeat(beat);}

  public GuiViewOursImpl() {
    this.displayPanel = new ConcreteGuiViewPanel();
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.getContentPane().add(displayPanel);
    this.pack();
    this.piece = null;
  }

  public GuiViewOursImpl(MusicPieceInterface n) {
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
    this.displayPanel.revalidate();
    this.displayPanel.getGraphics().dispose();
    this.displayPanel.updateUI();
    this.displayPanel.setVisible(false);
    this.displayPanel.removeAll();
    this.displayPanel.setStartBeat(beat);
    this.displayPanel.setVisible(true);
    this.redraw();
   // this.displayPanel = new ConcreteGuiViewPanel(piece, beat);
//    this.displayPanel.setVisible(true);
//    this.getContentPane().add(displayPanel);
//    this.pack();
  }

  public void redraw() {
//    BlankSquare blank = new BlankSquare();
//    this.displayPanel.add(blank);
    this.displayPanel.revalidate();
    this.displayPanel.repaint();
  }
}