package cs3500.music.view;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import cs3500.music.model.MusicPiece;

/**
 * Created by AviSion on 11/8/2015.
 */
public interface ViewInterface  {

    public void initialize() throws MidiUnavailableException,
            InterruptedException, InvalidMidiDataException;
}
