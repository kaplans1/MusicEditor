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
        if (args.length == 2) {
            String fileName = args[0];
            String argView = args[1];

            Builder b = new Builder();
            FileReader argFile = new FileReader(fileName);
            MusicPieceInterface r = MusicReader.parseFile(argFile, b);

            // visual, midi, or console
            AbstractViewInterface concreteView = new AbstractViewInterface(argView, r);
        } else {

            // error out or something
            System.out.println("Wrong number command line arguments");
        }

        Builder builder = new Builder();
        FileReader in = new FileReader("/Users/natdempk/Classwork/cs3500/MusicEditor/mary-little-lamb.txt");
        MusicPieceInterface r = MusicReader.parseFile(in, builder);

        // visual, midi, or console
        AbstractViewInterface view = new AbstractViewInterface("midi", r);
    }
}
