package cs3500.music.view2;

/**
 * Represents a single quarter note
 *
 * There is no concrete representation of a full note, full notes are composed from multiple Note
 * class items. (ex: [G: X--] would consist of three G Note items the first with isAttack as true
 */
public class Note extends Playable {
  /**
   * Constructs a new note with the provided spec
   *
   * NOTE: our Note class does not keep track of start or end it is simply an indication of the note
   * that is present at a particular beat!
   *
   * @param midiIndex  refers to the midi table number of this Note
   * @param isAttack   whether this Note object is the attack/start of a ntoe
   * @param instrument the instrument that will be playing this note
   * @param volume     the volume to play this note at
   */
  public Note(int midiIndex, boolean isAttack, int duration, int instrument, int volume) {
    this.midiIndex = midiIndex;
    this.pitch = midiIndex % 12;  //mod 12 to get the pitch of this note based on midi table
    this.octave = midiIndex / 12; //divide by 12 to get octave ...
    this.isAttack = isAttack;
    this.volume = volume;
    this.instrument = instrument;
    this.duration = duration;
  }

  protected final int pitch;
  protected final int octave;
  protected final boolean isAttack;
  protected final int volume;
  protected final int instrument;
  protected final int midiIndex;
  protected final int duration;

  /**
   * getters for fields of Note
   *
   * @return fields of Note
   */
  public int getMidiIndex() {
    return this.midiIndex;
  }

  public int getPitch() {
    return this.pitch;
  }

  public int getOctave() {
    return this.octave;
  }

  public boolean getIsAttack() {
    return isAttack;
  }

  public int getVolume() {
    return this.volume;
  }

  public int getInstrument() {
    return this.instrument;
  }

  public int getDuration() {
    return this.duration;
  }

  public int attackInt() {
    if (this.isAttack) {
      return 1;
    }
    else return 0;

  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Note)) {
      return false;
    }

    return (this.pitch == (((Note) o).pitch) &&
            this.octave == (((Note) o).octave) &&
            this.isAttack == (((Note) o).isAttack));
  }
}
