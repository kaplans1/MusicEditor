package cs3500.music.view2;

import cs3500.music.model.Composition;

/**
 * Represents a factory of all the possible view types
 */
public class ViewFactory {
    //use getShape method to get object of type shape
    public View getView(String viewType, Composition c){
      if (viewType == null){
        return null;
      }
      if (viewType.equalsIgnoreCase("console")){
        return new ConsoleView();

      } else if (viewType.equalsIgnoreCase("visual")){
        return new GuiViewImpl(c);

      } else if (viewType.equalsIgnoreCase("midi")) {
        return new MidiViewImpl();

      } else if (viewType.equalsIgnoreCase("audio-visual")) {
        return new CompositeImpl(new GuiViewImpl(c), new MidiViewImpl());
      }


      return null;
    }
}
