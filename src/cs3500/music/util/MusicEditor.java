package cs3500.music.util;

import cs3500.music.model.MusicNote;
import cs3500.music.model.Notes;
import cs3500.music.view.ConcreteGuiViewPanel;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.model.MusicPiece;

import java.awt.*;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;


public class MusicEditor {

  private MusicPiece musicPiece;

  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    MusicPiece mp = new MusicPiece();
    MusicNote f = new MusicNote(Notes.F, 2, 2, 2, true, false, 1, 1);
    MusicNote a = new MusicNote(Notes.A, 4, 2, 2, true, false, 1, 1);
    MusicNote b = new MusicNote(Notes.B, 6, 2, 2, true, false, 1, 1);
    MusicNote g = new MusicNote(Notes.G, 8, 4, 3, false, false, 1, 1);
    MusicNote g2 = new MusicNote(Notes.G, 10, 4, 3, false, false, 1, 1);
    mp.addNote(g);
    mp.addNote(g2);
    mp.addNote(f);
    mp.addNote(a);
    mp.addNote(b);


    GuiViewFrame view = new GuiViewFrame(mp);

    view.initialize();
    //MidiViewImpl midiView = new MidiViewImpl();
    // You probably need to connect these views to your model, too...
  }
}
