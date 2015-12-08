package cs3500.music.view;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import cs3500.music.controller.KeyboardHandler;
import cs3500.music.controller.MusicController;
import cs3500.music.mocks.MockLogger;
import cs3500.music.mocks.MockMidiSynthesizer;
import cs3500.music.model.MusicPiece;
import cs3500.music.model.MusicPieceInterface;
import cs3500.music.view2.Composition;
import cs3500.music.view2.Model;

public class AbstractViewInterface {
  ComboView view;
  public AbstractViewInterface(String viewType, MusicPieceInterface mp)
          throws MidiUnavailableException, InterruptedException, InvalidMidiDataException {
    
    if(viewType.equals("midi")){
      MidiViewImpl midiView = new MidiViewImpl(mp);
      midiView.initialize();
      /*
      / used to print output of playing mary file for testing
      MidiViewImpl impl = new MidiViewImpl(mp, new MockMidiSynthesizer());
      MockLogger logger = MockLogger.getInstance();
      impl.initialize();
      String log = logger.getLog();
      System.out.println(log);
      */
    } else if(viewType.equals("visual")){
      GuiViewImpl view = new GuiViewImpl(mp);
      //view.addKeyListener(new KeyboardHandler());
      view.initialize();
      //MusicController controller = new MusicController(mp);
    } else if (viewType.equals("console")){
      mp.render();
    } else if (viewType.equals("combo")){
      ComboView view = new ComboView(new GuiViewImpl(mp), new MidiViewImpl(mp), mp);
      this.view=view;
      MusicController musicController = new MusicController(mp, view);
      view.initialize();
    }
  }

  public void playPause(Boolean b, int x) {

  }
  public ComboView getComboView(){
    return this.view;
  }
}
