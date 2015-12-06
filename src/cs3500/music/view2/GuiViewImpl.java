package cs3500.music.view2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.sound.midi.InvalidMidiDataException;
import javax.swing.*;
import cs3500.music.controller.KeyboardHandler;
import cs3500.music.controller.MouseHandler;
import cs3500.music.view2.Model;

/**
 * An implementation of a visual view of music
 */
public class GuiViewImpl extends JFrame implements GuiView {
  private final GridPanel mainPanel;  // You may want to refine this to a subtype of JPanel
  private Model c;
  JScrollPane scrollPane;

  boolean isPaused = true;

  int currentTick = 0; //comes from get and set tick methods between midi composite and gui

  javax.swing.Timer t = new javax.swing.Timer(165 , new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      if (!isPaused) {
        stepRight(1);
        mainPanel.incrementLinePosition();
        mainPanel.revalidate();
        mainPanel.repaint();
      }
    }
  });


  public GuiViewImpl(Model c) {
    this.c = c;
    this.mainPanel = new GridPanel(c);
    scrollPane = new JScrollPane(mainPanel);
    //scrollPane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
    scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.getContentPane().add(scrollPane);
    this.pack();
    t.start();
  }


  // @Override
  public void drawView(){
    this.setVisible(true);
  }

  @Override
  public Dimension getPreferredSize(){
    int h = (c.getOctaveInterval().size() * 120) + 90;
    int w = (c.getNumBeats() * 10) + 90;
    return new Dimension(w, h);
  }

  @Override
  public void createView(Model c, int tick) throws InvalidMidiDataException, InterruptedException{
    this.setVisible(true);
  }


  @Override
  public void redraw() {
    SwingUtilities.updateComponentTreeUI(this);
  }


  @Override
  public void pause() {
    isPaused = !isPaused;
    scrollPane.getHorizontalScrollBar().setValue((mainPanel.linePosition * 10));
  }

  int stepRate = 10;

  public void stepRight(int howMany) {
    int val = scrollPane.getHorizontalScrollBar().getValue();
    int max = scrollPane.getHorizontalScrollBar().getMaximum();
    if (howMany == 1 && val != max) {
      scrollPane.getHorizontalScrollBar().setValue(val + stepRate);
    } else {
      scrollPane.getHorizontalScrollBar().setValue(max);
    }
  }

  public void stepLeft(int howMany) {
    int val = scrollPane.getHorizontalScrollBar().getValue();
    int min = scrollPane.getHorizontalScrollBar().getMinimum();
    if (howMany == 1 && val != 0) {
      scrollPane.getHorizontalScrollBar().setValue(val - stepRate);
    } else {
      scrollPane.getHorizontalScrollBar().setValue(min);
    }
  }


  public void addKeyHandler(KeyboardHandler kbh) {
    this.addKeyListener(kbh);
  }

  public void addMouseHandler(MouseHandler mh) {
    mainPanel.addMouseHandler(mh);
  }
}




