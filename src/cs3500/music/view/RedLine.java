package cs3500.music.view;

import java.awt.*;

import javax.swing.*;

/**
 * Created by AviSion on 11/22/2015.
 */
public class RedLine extends JLabel {
  int time;

  public RedLine(int time){
    this.time = time;
  }

  @Override
  public void paint(Graphics g){
    g.setColor(Color.RED);
    g.drawLine(50, 50, 50, 400);
  }

}
