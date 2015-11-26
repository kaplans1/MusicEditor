package cs3500.music.view;

import java.awt.*;

import javax.swing.*;

/**
 * Created by AviSion on 11/22/2015.
 */
public class BlankSquare extends JLabel {
  int time;

  public BlankSquare(){
  }

  @Override
  public void paint(Graphics g){
    g.fillRect(0, 0, 1000, 1000);
  }

}
