package cs3500.music.view;

import cs3500.music.model.MusicPiece;

/**
 * Created by AviSion on 11/11/2015.
 */
public class AbstractViewInterface {

  //for switching among multiple views, hw 3.2

  //will implement viewinterface
  //guiviewframe and midiviewimpl will extend this, I think
  public AbstractViewInterface(String viewType, MusicPiece mp){
    if(viewType.equals("midi")){
      MidiViewImpl midiView = new MidiViewImpl();
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
