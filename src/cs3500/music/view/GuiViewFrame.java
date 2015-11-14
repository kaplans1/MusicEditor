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
public class GuiViewFrame extends javax.swing.JFrame implements ViewInterface {

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
