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
public class GuiViewFrame extends javax.swing.JFrame implements GuiView {

  private final JPanel displayPanel; // You may want to refine this to a subtype of JPanel

  private MusicPieceInterface piece;

  /**
   * Creates new GuiView
   */
  public GuiViewFrame() {
    this.displayPanel = new ConcreteGuiViewPanel();
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.getContentPane().add(displayPanel);
    this.pack();
    this.piece = null;
  }

  public GuiViewFrame(MusicPieceInterface n) {
    this.piece = n;
    this.displayPanel = new ConcreteGuiViewPanel(piece);
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.getContentPane().add(displayPanel);
    this.pack();
    this.redLine(displayPanel.getGraphics(), 0);
  }


  public void redLine(Graphics g, int time){
    CustomComponents0 component = new CustomComponents0();
    displayPanel.add(component);
//    rootPane.getContentPane().validate();
//    rootPane.getContentPane().repaint();
    repaint(40, 40, 500, 500);
  }

//stackoverflow: 8853397
  static class CustomComponents0 extends JLabel {
    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.setColor(Color.red);
      g.drawLine(50, 50, 50, 400);
    }
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

}
