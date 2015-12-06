package cs3500.music.view2;

import java.util.ArrayList;
import java.util.HashMap;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.controller.KeyboardHandler;
import cs3500.music.controller.MouseHandler;
import cs3500.music.view2.Model;
import cs3500.music.model.Playable;

/**
 * Represents a view that can be printed in the console
 */
public class ConsoleView implements View  {
  String octave  = "|<---- Octave %d ----->|  ";
  String notes   = "C C D D E F F G G A A B  ";
  String sharps  = "  #   #     #   #   #    ";
  String line    = "-----------------------  ";

  public ConsoleView() {

  }

  ArrayList<Integer> interval;

  @Override
  public void createView(Model c, int tick) throws InvalidMidiDataException, InterruptedException{
    interval = c.getOctaveInterval();

    System.out.print("     ");
    for (int i = interval.get(0); i <= interval.get(interval.size() - 1); i += 1){
      System.out.format(octave, i);
    }

    System.out.println("");
    System.out.print("     ");
    for (int i = 0; i < interval.size(); i += 1) {
      System.out.print(notes);
    }

    System.out.println("");
    System.out.print("     ");
    for (int i = 0; i < interval.size(); i += 1) {
      System.out.print(sharps);
    }


    System.out.println("");
    System.out.print("     ");
    for (int i = 0; i < interval.size(); i += 1) {
      System.out.print(line);
    }

    int time = 0;
    int totalTime = c.getNumBeats();
    String timeMark = "%4d";

    for (int i = 0; i < totalTime; i += 1) {
      System.out.println("");
      System.out.format(timeMark, time);
      System.out.print(" " + this.createNotes(c.notesAtBeat(i)));
      time += 1;
    }
  }

  @Override
  public void redraw() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void addKeyHandler(KeyboardHandler kbh) {

  }

  @Override
  public void addMouseHandler(MouseHandler mh) {

  }

  @Override
  public void stepRight(int howMany) {

  }

  @Override
  public void stepLeft(int howMany) {

  }

  private String createNotes(ArrayList<Playable> notes) {
    String result = "";
    String blankOctave = "                       ";
    HashMap<Integer, String> allOctaves = new HashMap<>();

    for (int i = 0; i < interval.size(); i += 1) {
      allOctaves.put(interval.get(i), blankOctave);
    }

    for (int i = 0; i < notes.size(); i += 1) {
      int thisNotesOctave = notes.get(i).getOctave();
      String temp = allOctaves.get(thisNotesOctave);

      int place = notes.get(i).getPitch() * 2;
      if (notes.get(i).getIsAttack()) {
        temp = temp.substring(0, place) + "X" + temp.substring(place + 1);
      }
      else {
        temp = temp.substring(0, place) + "|" + temp.substring(place + 1);
      }

      allOctaves.put(thisNotesOctave, temp);
    }

    for (int i = 0; i < interval.size(); i += 1) {
      result = result + allOctaves.get(interval.get(i)) + "  ";
    }

    return result;

  }

}
