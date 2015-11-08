package meditor.model;
import java.util.Comparator;
import java.util.ArrayList;

/**
 * Represents a group of beats. A group of beats has the same pitch. The first beat in the group is
 * a Starting beat. A Note also has a pitch and a start time.e sec
 *
 */
public class Note implements Comparable<Note>{

  protected Pitch pitch;
  protected ArrayList<Beat> beats;
  protected int startTime;

  /**
   *    * Sets the pitch of the note in the given octave and pitch, makes note given number of beats
   * long
   *
   * @param o given octave
   * @param p given pitch
   * @param beats number of beats for note
   * @param start start beat of note
   */
  public Note(int beats, int o, String p, int start) {
    this.pitch = new Pitch(o, p);
    this.beats = this.makeBeats(beats);
    //makes the first beat of a note a starting beat
    this.beats.get(0).setStatus('s');
    if (start>=0) {
      this.startTime = start;
    } else {
      throw new IllegalArgumentException("Start time can never be negative!");
    }
  }

  private ArrayList<Beat> makeBeats(int beats) {
    ArrayList<Beat> a = new ArrayList<Beat>();
    if (beats > 0) {
      for (int i = 0; i < beats; i++) {
        a.add(new Beat('n'));
      }
    } else {
      throw new IllegalArgumentException("Number of beats has to be bigger than 0!");
    }
    return a;
  }
  @Override
  public int compareTo(Note o) {
    return startTime - o.startTime;
  }
  public static Comparator<Note> NotePitchIDComparator = new Comparator<Note>() {
    public int compare(Note note1, Note note2) {
      int note1ID = note1.pitch.getPitchID();
      int note2ID = note2.pitch.getPitchID();
      //ascending order
      return note1ID - note2ID;
    }
  };

  protected void makeBlank(Note n){
    for(Beat b: n.beats){
      b.setStatus('r');
    }
  }

}
