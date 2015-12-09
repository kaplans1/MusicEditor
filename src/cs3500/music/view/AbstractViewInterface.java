package cs3500.music.view;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import cs3500.music.controller.KeyboardHandler;
import cs3500.music.controller.MusicController;
import cs3500.music.mocks.MockLogger;
import cs3500.music.mocks.MockMidiSynthesizer;
import cs3500.music.model.MusicPiece;
import cs3500.music.model.MusicPieceInterface;
import cs3500.music.model.MusicPieceModelAdapter;
import cs3500.music.view2.*;

public class AbstractViewInterface {
  ComboInterface view;

  public AbstractViewInterface(String viewType, MusicPieceInterface mp)
      throws MidiUnavailableException, InterruptedException, InvalidMidiDataException {
    if (viewType.equals("our-midi")) {
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
    } else if (viewType.equals("our-visual")) {
      GuiViewImpl view = new GuiViewImpl(mp);
      //view.addKeyListener(new KeyboardHandler());
      view.initialize();
      //MusicController controller = new MusicController(mp);
    } else if (viewType.equals("our-console")) {
      mp.render();
    } else if (viewType.equals("our-combo")) {
      ComboView view = new ComboView(new GuiViewImpl(mp), new MidiViewImpl(mp), mp);
      this.view = view;
      MusicController musicController = new MusicController(mp, view);
      view.initialize();
    } else if (viewType.equals("their-midi")) {
      MusicPieceModelAdapter mpAdapter = new MusicPieceModelAdapter(mp);
      cs3500.music.view2.MidiViewImpl view = new cs3500.music.view2.MidiViewImpl();
      view.createView(mpAdapter, 0);
    } else if (viewType.equals("their-visual")) {
      MusicPieceModelAdapter mpAdapter = new MusicPieceModelAdapter(mp);
      cs3500.music.view2.GuiViewImpl view = new cs3500.music.view2.GuiViewImpl(mpAdapter);
      view.createView(mpAdapter, 0);
    } else if (viewType.equals("their-console")) {
      MusicPieceModelAdapter mpAdapter = new MusicPieceModelAdapter(mp);
      ConsoleView view = new ConsoleView();
      view.createView(mpAdapter, 0);
    } else if (viewType.equals("their-combo")) {
      MusicPieceModelAdapter mpAdapter = new MusicPieceModelAdapter(mp);
      CompositeImpl view = new CompositeImpl(new cs3500.music.view2.GuiViewImpl(mpAdapter),
          new cs3500.music.view2.MidiViewImpl());
      CompositeImplAdapter compositeAdapter = new CompositeImplAdapter(view, mpAdapter);
      this.view = compositeAdapter;
      MusicController musicController = new MusicController(mp, compositeAdapter);
      //view.createView(mpAdapter, 0);
      compositeAdapter.initialize();
    }
  }

  public void playPause(Boolean b, int x) {

  }

  public ComboInterface getComboView() {
    return this.view;
  }
}
