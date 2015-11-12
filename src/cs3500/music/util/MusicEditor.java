package cs3500.music.util;

import cs3500.music.model.MusicNote;
import cs3500.music.model.Notes;
import cs3500.music.view.ConcreteGuiViewPanel;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.model.MusicPiece;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;


public class MusicEditor {

  private MusicPiece musicPiece;

  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    MusicPiece mp = new MusicPiece();
    MusicNote f = new MusicNote(Notes.F, 1, 2, 2, true, false, 1, 1);
    MusicNote a = new MusicNote(Notes.A, 2, 2, 2, true, false, 1, 1);
    MusicNote b = new MusicNote(Notes.B, 3, 2, 2, true, false, 1, 1);
    MusicNote c = new MusicNote(Notes.C, 3, 7, 2, true, false, 1, 1);
    MusicNote d = new MusicNote(Notes.D, 3, 12, 2, true, false, 1, 1);
    MusicNote g = new MusicNote(Notes.G, 4, 4, 3, false, false, 1, 1);
    MusicNote g2 = new MusicNote(Notes.G, 50, 4, 3, false, false, 1, 1);
    MusicNote c2 = new MusicNote(Notes.C, 1, 2, 0, true, false, 1, 1);
    mp.addNote(g);
    mp.addNote(g2);
    mp.addNote(f);
    mp.addNote(a);
    mp.addNote(b);
    mp.addNote(c);
    mp.addNote(d);
    mp.addNote(c2);

  CompositionBuilder<MusicPiece> x = new MusicPiece.Builder();
    FileReader in = new FileReader("C:/Users/AviSion/IdeaProjects/MusicEditor/mary-little-lamb.txt");


    GuiViewFrame view = new GuiViewFrame(mp);

    view.initialize();
    //MidiViewImpl midiView = new MidiViewImpl();
    // You probably need to connect these views to your model, too...
  }
}
