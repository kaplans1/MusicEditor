package cs3500.music.view2;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlSchemaType;

import cs3500.music.util.CompositionBuilder;

/**
 * Represents a single composition or a piece of music
 */
public class Composition implements Model {
  /**
   * the tempo of this composition - set at the time of builder
   */
  private int tempo;
  /**
   * The outer arraylist is the beats The inner arrayList is the pitches sounding at that beat
   */
  public ArrayList<ArrayList<Playable>> notes;

  /**
   * Constructs a new Composition with the provided tempo, and the provided list of notes
   *
   * @param tempo the tempo of this Composition
   * @param notes the notes contained in this Composition
   */
  private Composition(int tempo, ArrayList<ArrayList<Playable>> notes) {
    this.tempo = tempo;
    this.notes = notes;
  }

  /**
   * Constructs a new Composition with the provided tempo
   *
   * @param tempo the tempo of this Composition
   */
  public Composition(int tempo) {
    this.tempo = tempo;
    this.notes = new ArrayList<>();
  }

  /**
   * public constructor for testing
   */
  public Composition() { notes = new ArrayList<>(); }

  @Override
  public boolean doesNoteExist(Playable note, int startBeat) {
    return (notes.get(startBeat).contains(note));
  }

  @Override
  public void addMeasures(int num) {
    ArrayList<Playable> a = new ArrayList<Playable>();
    for (int i = 0; i < num; i += 1) {
      notes.add(a);
      notes.add(a);
      notes.add(a);
      notes.add(a);
    }
  }

  @Override
  public void addNote(int startBeat, int endBeat, int instrument, int pitch, int volume) {
    if (pitch < 0 || pitch > 128) {
      throw new IllegalArgumentException("invalid midi pitch index");
    }

    if (endBeat > this.notes.size()) {
      int i = this.notes.size();
      while (i < endBeat) {
        this.notes.add(new ArrayList<>());
        i++;
      }
    }

    for (int i = startBeat + 1; i < endBeat; i = i + 1) {
      this.notes.get(i).add(new Note(pitch, false, 0, instrument, volume));
    }
    this.notes.get(startBeat).add(new Note(pitch, true, endBeat - startBeat, instrument, volume));
  }

  @Override
  public void addRepeat(int from, int to, int skipFrom) {
    //not our problem
  }


  @Override
  public void removeNote(int startBeat, Playable note) {
    Note decay = new Note(note.getMidiIndex(), false, note.getDuration(), note.getInstrument(),
            note.getVolume());
    if ((notes.get(startBeat).contains(note)) && note.getIsAttack()) {
      //remove the decay first
      int i = startBeat + 1;
      if (i < notes.size()) {
        while (notes.get(i).contains(decay)) {
          notes.get(i).remove(decay);
          i++;
        }
      }
      //remove attack first
      notes.get(startBeat).remove(note);
    }
  }

  @Override
  public ArrayList<Playable> notesAtBeat(int beat) {
    return notes.get(beat);
  }

  @Override
  public ArrayList<Integer> getOctaveInterval() {
    int start = 0;
    int end = 0;

    ArrayList<Integer> result = new ArrayList<Integer>();

    for (int i = 0; i < notes.size(); i += 1) {
      for (int j = 0; j < notes.get(i).size(); j += 1) {
        int temp = 0;
        temp = notes.get(i).get(j).getOctave();
        if (start == 0 && end == 0) {
          start = temp;
          end = temp;
        } else if (temp < start || temp > end) {
          if (temp < start) {
            start = temp;
          }
          if (temp > end) {
            end = temp;
          }
        }
      }
    }

    //populate arraylist with the range of octaves represented
    while (start != end + 1) {
      if (!(start == 0 && end == 0)) {
        result.add(start);
        start += 1;
      } else {
        start += 1;
      }
    }

    return result;
  }

  @Override
  public int getNumBeats() {
    return notes.size();
  }

  @Override
  public int getTempo() { return this.tempo; }



  //@Override
  public void setTempo(int tempo) {
    this.tempo = tempo;
  }

  @Override
  public ArrayList<ArrayList<Playable>> getNotes() {
    return this.notes;
  }

  public static final class Builder implements CompositionBuilder<Model> {
    private int tempo = DEFAULT_TEMPO;
    Model c = new Composition(tempo);

    @Override
    public CompositionBuilder<Model> setTempo(int tempo) {
      c.setTempo(tempo);

      return this;
    }

  //  @Override
    public Builder tempo(int tempo) {
      this.tempo = tempo;

      return this;
    }

    @Override
    public CompositionBuilder<Model> addNote(int start, int end,
                                                   int instrument, int pitch, int volume) {
      c.addNote(start, end, instrument, pitch, volume);

      return this;
    }

    @Override
    public CompositionBuilder<Model> addRepeat(int from, int to,
                                             int skipFrom) {
      c.addRepeat(from, to, skipFrom);

      return this;
    }

    @Override
    public Model build() {
      return c;
    }
  }
}
