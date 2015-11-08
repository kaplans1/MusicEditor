package cs3500.music;

import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.model.MusicPiece;

import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;


public class MusicEditor {

  private MusicPiece musicPiece;

  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    GuiViewFrame view = new GuiViewFrame();
    MidiViewImpl midiView = new MidiViewImpl();
    // You probably need to connect these views to your model, too...
  }
}
