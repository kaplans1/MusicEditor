package cs3500.music.util;

import cs3500.music.model.MusicNote;
import cs3500.music.model.MusicPiece.Builder;
import cs3500.music.model.MusicPieceInterface;
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
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;


public class MusicEditor {

    public static void main(String[] args) throws IOException, InvalidMidiDataException, MidiUnavailableException, InterruptedException {
        // for testing
        MusicPiece mp = new MusicPiece();
        //MusicNote f = new MusicNote(Notes.F, 1, 2, 2, true, false, 0, 50);
        //mp.addNote(f);
        //MusicNote a = new MusicNote(Notes.A, 2, 2, 2, true, false, 0, 50);
        //mp.addNote(a);
        //MusicNote b = new MusicNote(Notes.B, 3, 2, 2, true, false, 0, 50);
        //mp.addNote(b);
        MusicNote c = new MusicNote(Notes.C, 3, 7, 2, true, false, 0, 50);
        mp.addNote(c);
        //MusicNote c = new MusicNote(Notes.C, 4, 15, 5, false, false, 0, 80);
        //mp.addNote(c);
        MusicNote d = new MusicNote(Notes.D, 4, 4, 2, true, false, 0, 80);
        mp.addNote(d);
        //MusicNote c2 = new MusicNote(Notes.C, 50, 8, 3, false, false, 0, 80);
        //mp.addNote(c2);
        //MusicNote c2 = new MusicNote(Notes.C, 1, 2, 0, true, false, 0, 50);
        //mp.addNote(c2);


        Builder builder = new Builder();
      //  FileReader in = new FileReader("/Users/natdempk/Classwork/cs3500/MusicEditor/mary-little-lamb.txt");
      FileReader in = new FileReader("C:/Users/AviSion/IdeaProjects/MusicEditor/mystery-1.txt");

        MusicPieceInterface r = MusicReader.parseFile(in, builder);

        // visual, midi, or console
        AbstractViewInterface view = new AbstractViewInterface("visual", mp);
    }
}
