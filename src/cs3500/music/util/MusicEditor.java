package cs3500.music.util;

import cs3500.music.model.MusicPiece.Builder;
import cs3500.music.model.MusicPieceInterface;
import cs3500.music.view.AbstractViewInterface;

import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;


public class MusicEditor {

    public static void main(String[] args) throws IOException,
            InvalidMidiDataException,
            MidiUnavailableException, InterruptedException {
//        if (args.length == 2) {
//            String fileName = args[0];
//            String argView = args[1];

            Builder b = new Builder();
            FileReader argFile = new FileReader("C:/Users/AviSion/IdeaProjects/MusicEditor/mystery-1.txt");
          //FileReader argFile = new FileReader("/Users/natdempk/Classwork/
          // cs3500/MusicEditor/mary-little-lamb.txt");
        //   FileReader argFile = new FileReader(fileName);
            MusicPieceInterface r = MusicReader.parseFile(argFile, b);

            // our-combo, our-visual, our-midi, or our-console
            // their-combo, their-visual, their-midi, or their-console
            AbstractViewInterface concreteView = new AbstractViewInterface("our-combo", r);
//        } else {
//            // display an error
//            System.out.println("Wrong number of command line arguments");
//        }
    }
}
