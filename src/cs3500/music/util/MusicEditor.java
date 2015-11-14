package cs3500.music.util;

import cs3500.music.model.MusicNote;
import cs3500.music.model.Notes;
import cs3500.music.view.AbstractViewInterface;
import cs3500.music.view.ConcreteGuiViewPanel;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.model.MusicPiece;
import cs3500.music.view.ViewInterface;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;


public class MusicEditor {

  private MusicPiece musicPiece;

  public static void main(String[] args) throws IOException, InvalidMidiDataException, MidiUnavailableException, InterruptedException {
    MusicPiece mp = new MusicPiece();
//    MusicNote f = new MusicNote(Notes.F, 1, 2, 2, true, false, 0, 50);
//    mp.addNote(f);
//    MusicNote a = new MusicNote(Notes.A, 2, 2, 2, true, false, 0, 50);
//    mp.addNote(a);
//    MusicNote b = new MusicNote(Notes.B, 3, 2, 2, true, false, 0, 50);
//    mp.addNote(b);
//    MusicNote c = new MusicNote(Notes.C, 3, 7, 2, true, false, 0, 50);
//    mp.addNote(c);
    MusicNote c = new MusicNote(Notes.C, 4, 15, 5, false, false, 0, 80);
    mp.addNote(c);
    MusicNote d = new MusicNote(Notes.D, 4, 4, 2, true, false, 0, 80);
    mp.addNote(d);

    MusicNote c2 = new MusicNote(Notes.C, 50, 8, 3, false, false, 0, 80);
    mp.addNote(c2);
//    MusicNote c2 = new MusicNote(Notes.C, 1, 2, 0, true, false, 0, 50);
//    mp.addNote(c2);








  CompositionBuilder<MusicPiece> x = new MusicPiece.Builder();
    FileReader in = new FileReader("C:/Users/AviSion/IdeaProjects/MusicEditor/mary-little-lamb.txt");



    AbstractViewInterface view = new AbstractViewInterface("midi", mp);


    //
    // You probably need to connect these views to your model, too...
  }
}
