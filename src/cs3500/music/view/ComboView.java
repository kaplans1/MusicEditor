package cs3500.music.view;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import cs3500.music.model.MusicPieceInterface;

/**
 * Created by AviSion on 11/22/2015.
 */
public class ComboView implements ViewInterface{
  private MusicPieceInterface piece;
  AbstractViewInterface visual;
  AbstractViewInterface midi;
  public ComboView(MusicPieceInterface m){
    this.piece=m;
  }

  @Override
  public void initialize() throws MidiUnavailableException, InterruptedException, InvalidMidiDataException {
    AbstractViewInterface concreteView = new AbstractViewInterface("visual", this.piece);
    this.visual = concreteView;
    AbstractViewInterface concreteView2 = new AbstractViewInterface("midi", this.piece);
    this.midi = concreteView2;
  }

  public void playPause(Boolean b, int x) {
    this.midi.playPause(b, x);
  }
}
