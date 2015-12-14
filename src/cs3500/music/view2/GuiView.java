package cs3500.music.view2;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * Interface for the gui specific methods
 */
public interface GuiView extends View {
  /**
   * Adds the specified key listener to receive key events from this component. If l is null,
   * no exception is thrown and no action is performed.
   * @param l the key listener
   */
  public void addKeyListener(KeyListener l);

  /**
   * Adds the specified mouse listener to receive mouse events from this component. If listener l
   * is null, no exception is thrown and no action is performed.
   * @param l the mouse listener
   */
  public void addMouseListener(MouseListener l);

  /**
   * Tell the GuiViewOurs to redraw itself
   */
  public void redraw();
}
