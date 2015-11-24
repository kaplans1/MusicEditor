package cs3500.music.model;


import com.sun.javaws.exceptions.InvalidArgumentException;

public class MusicNote {
  private int startBeat;
  private int pitchID;
  private int beats;
  private int octave;
  private int instrument;
  private int volume;

  /**
   * Represents a musical note in a piece of music
   *
   * @param note      musical note
   * @param startBeat starting beat of the note
   * @param beats     number of beats to hold the note
   * @param octave    octave the note is present in
   * @param sharp     if the note is sharp
   * @param flat      if the note is flat
   */
  public MusicNote(Notes note, int startBeat, int beats, int octave, boolean sharp, boolean flat,
                   int instrument, int volume) {
    if (startBeat < 0) {
      throw new IllegalArgumentException("startBeat must be >= 0.");
    }
    this.startBeat = startBeat;


    if (beats <= 0) {
      throw new IllegalArgumentException("beats must be > 0.");
    }
    this.beats = beats;

    if (instrument < 0) {
      throw new IllegalArgumentException("instrument must be >= 0.");
    }
    this.instrument = instrument;

    if (volume < 0 || volume >= 128) {
      throw new IllegalArgumentException("volume must be between 0 and 127.");
    }
    this.volume = volume;


    if (octave < -1 || octave > 9) {
      throw new IllegalArgumentException("octave must be between 1 and 9.");
    }
    this.octave = octave;

    if (sharp && flat) {
      throw new IllegalArgumentException("A MusicNote cannot be both sharp and flat.");
    }

    int absolutePitch = ((this.octave + 1) * 12) + note.getNoteValue();
    if (sharp) {
      absolutePitch++;
    } else if (flat) {
      absolutePitch--;
    }

    this.pitchID = absolutePitch;
  }

  /**
   * Represents a musical note in a piece of music
   *
   * @param pitchID    absolute pitch of the note
   * @param startBeat  starting beat for note to be played on
   * @param beats      number of beats to play note for
   * @param instrument instrument to play note with
   * @param volume     volume to play note at
   */
  public MusicNote(int pitchID, int startBeat, int beats, int instrument, int volume) {
    this.pitchID = pitchID;
    this.startBeat = startBeat;
    this.beats = beats;
    this.instrument = instrument;
    this.volume = volume;
  }

  public static int pitchIDfromString(String note, String octave) {
    String noteString = note.toUpperCase();
    int octaveNum = Integer.parseInt(octave);
    int noteValue = 0; // temp value

    switch (noteString) {
      case "C":
        noteValue = 0;
        break;
      case "C#":
        noteValue = 1;
        break;
      case "D":
        noteValue = 2;
        break;
      case "D#":
        noteValue = 3;
        break;
      case "E":
        noteValue = 4;
        break;
      case "F":
        noteValue = 5;
        break;
      case "F#":
        noteValue = 6;
        break;
      case "G":
        noteValue = 7;
        break;
      case "G#":
        noteValue = 8;
        break;
      case "A":
        noteValue = 9;
        break;
      case "A#":
        noteValue = 10;
        break;
      case "B":
        noteValue = 11;
        break;
      default:
        throw new IllegalArgumentException("Note string did not match any known note.");
    }

    return (octaveNum * 12) + noteValue;
  }

  /**
   * Generates the note name and octave of a certain pitch
   *
   * @param pitchID integer ID of pitch to convert to name of note and octave
   * @return string name of note and octave eg. "C#3"
   */
  public static String pitchToString(int pitchID) {
    if (pitchID >= 0) {
      String name = "";
      int value = pitchID;

      value %= 12;

      switch (value) {
        case 0:
          name = "C";
          break;
        case 1:
          name = "C#";
          break;
        case 2:
          name = "D";
          break;
        case 3:
          name = "D#";
          break;
        case 4:
          name = "E";
          break;
        case 5:
          name = "F";
          break;
        case 6:
          name = "F#";
          break;
        case 7:
          name = "G";
          break;
        case 8:
          name = "G#";
          break;
        case 9:
          name = "A";
          break;
        case 10:
          name = "A#";
          break;
        case 11:
          name = "B";
          break;
      }

      name += String.valueOf(Math.floorDiv(pitchID, 12));

      return name;
    } else {
      throw new IllegalArgumentException("Wrong PitchID");
    }
  }

  /**
   * to check if the note is being played on a given beat
   *
   * @param beat beat to check if note is being played on
   * @return boolean of whether the note is being played
   */
  public boolean activeOnBeat(int beat) {
    return (this.startBeat <= beat && beat < this.startBeat + this.beats);
  }

  /**
   * gets the last beat the note is being played on
   *
   * @return last beat note is being played on
   */
  public int getEndBeat() {
    return this.startBeat + this.beats - 1;
  }

  public int getStartBeat() {
    return this.startBeat;
  }

  public int getInstrument() {
    return this.instrument;
  }

  public int getVolume() {
    return this.volume;
  }

  public int getLength() { return this.getEndBeat() - this.getStartBeat(); }

  /**
   * gets the true number representing the note across scales octaves, handling sharps flats etc.
   *
   * @return int value of the note from 0 to 256
   */
  public int getPitchID() {
    return this.pitchID;
  }

  /**
   * gets the note name and octave
   *
   * @return the string name of the note and octave eg. "C#3"
   */
  public String noteName() {
    return this.pitchToString(this.pitchID);
  }

  /**
   * To compare a note to another for equality based on note value only not beats
   *
   * @param o object to compare note with
   * @return boolean indicating whether note is the same as another
   */
  @Override
  public boolean equals(Object o) {
    return o instanceof MusicNote && this.getPitchID() == ((MusicNote) o).getPitchID();
  }


}
