package cs3500.music.view2;

/**
 * Represents a playable part of a piece of music
 */
public abstract class Playable {
  //mainly used as a layer to hide implementation details


  public abstract int getMidiIndex();
  public abstract boolean getIsAttack();
  public abstract int getPitch();
  public abstract int getOctave();
  public abstract int getVolume();
  public abstract int getInstrument();
  public abstract int getDuration();
  public abstract int attackInt();
}
