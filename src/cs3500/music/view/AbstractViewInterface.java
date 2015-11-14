package cs3500.music.view;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import cs3500.music.model.MusicPiece;
import cs3500.music.model.MusicPieceInterface;

/**
 * Created by AviSion on 11/11/2015.
 */
public class AbstractViewInterface {

  //for switching among multiple views, hw 3.2

  //will implement viewinterface
  //guiviewframe and midiviewimpl will extend this, I think
  public AbstractViewInterface(String viewType, MusicPieceInterface mp) throws MidiUnavailableException, InterruptedException, InvalidMidiDataException {
    if(viewType.equals("midi")){
      MidiViewImpl midiView = new MidiViewImpl(mp);
      midiView.initialize();
    } else  if(viewType.equals("visual")){
      GuiViewFrame view = new GuiViewFrame(mp);
      view.initialize();
    } else if (viewType.equals("console")){
      mp.render();
    }
  }

  //this one is for making things from music pieces from text files
  public AbstractViewInterface(String viewType, Readable r){

//    if(viewType.equals("midi")){
//      MidiViewImpl midiView = new MidiViewImpl();
//      midiView.initialize();
//    } else  if(viewType.equals("visual")){
//      GuiViewFrame view = new GuiViewFrame(mp);
//      view.initialize();
//    } else if (viewType.equals("console")){
//      mp.render();
//    }
  }



}
