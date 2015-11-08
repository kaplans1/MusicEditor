package meditor.model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A representation of a pitch, that has an octave
 */
public class Pitch {
  //Pitch at index of each array is the same C == B# == Dbb
  //# -> sharp
  //b -> flat
  //bb -> double flat
  //x -> double sharp
  private final ArrayList<String> pitchesClassic = new ArrayList<String>(Arrays.asList(
          "C", "C#", "D", "D#", "E", "F",
          "F#", "G", "G#", "A", "A#", "B"));
  private final ArrayList<String> pitches1 = new ArrayList<String>(Arrays.asList(
          "B#", "Bx", "Cx", "Eb", "Dx", "E#",
          "Ex", "Fx", "Ab", "Gx", "Bb", "Ax"));
  private final ArrayList<String> pitches2 = new ArrayList<String>(Arrays.asList(
          "Dbb", "Db", "Ebb", "Fbb", "Fb", "Gbb",
          "Gb", "Abb", "Ab", "Bbb", "Cbb", "Cb"));
  private int oct;
  private int pitch;

  /**
   * @param o inputted octave parameter  must be above 0
   * @param p inputted pitch must be among known pitches, and known enharmonic equivalents
   */
  public Pitch(int o, String p) {
    if (o >= 0) {
      this.oct = o;
    } else {
      throw new IllegalArgumentException("Bad octave!");
    }
    if (pitchesClassic.contains(p)) {
      this.pitch = pitchesClassic.indexOf(p);
    } else if (pitches1.contains(p)) {
      this.pitch = pitches1.indexOf(p);
    } else if (pitches2.contains(p)) {
      this.pitch = pitches2.indexOf(p);
    } else {
      throw new IllegalArgumentException("Passed pitch is invalid!");
    }
  }
  //returns unique ID of pitch
  //C1 -> 0
  //G9 -> 128
  protected int getPitchID(){
    int a = oct;
    return (12*(a))+pitch;
  }

  protected int getOct() {
    int a = oct;
    return a;
  }

  protected ArrayList<String> getPitchesClassic() {
    ArrayList<String> a = (ArrayList<String>) pitchesClassic.clone();
    return a;
  }
}
