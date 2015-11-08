import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import meditor.model.EditorGraph;
import meditor.model.Meditor;
import meditor.model.Note;

import static org.junit.Assert.*;

import java.util.ArrayList;


public class ModelTest {
  //Testing console output - stackoverflow 1119559
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  @Before
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
  }
  @After
  public void cleanUpStreams() {
    System.setOut(null);
  }

  //throws exception, even though string values are the same. not sure why.
  @Test
  public void addNote() {
    Meditor a = new EditorGraph();
    a.addNote(7, 2, "F", 6);
    a.addNote(6, 0, "C#", 5);
    a.addNote(5, 0, "C#", 15);
    a.addNote(4, 0, "C#", 25);
    a.addNote(3, 0, "D", 2);
    a.addNote(1, 0, "F", 1);
    a.addNote(2, 0, "F", 20);
    a.Render();
    assertEquals("1:  ooooX-----ooooX----ooooX---\n" +
            "2:  oX--\n" +
            "5:  oooooooooooooooooooXX-\n" +
            "29: oooooX------", outContent.toString());
  }

  //throws exception, even though string values are the same. not sure why.
  @Test
  public void delNote() {
    Meditor a = new EditorGraph();
    a.addNote(5, 2, "F", 1);
    a.addNote(4, 0, "C#", 15);
    a.addNote(4, 0, "C#", 25);
    a.addNote(3, 0, "D", 2);
    a.addNote(2, 0, "F", 1);
    a.addNote(2, 0, "F", 20);
    a.delNote(0, "F", 20);
    a.delNote(0, "F", 1);
    a.Render();
    assertEquals("1:  ooooooooooooooX---ooooooX---\n" +
            "2:  oX--\n" +
            "29: X----", outContent.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void collision() {
    Meditor a = new EditorGraph();
    a.addNote(5, 0, "F", 0);
    a.addNote(5, 0, "F", 0);
    a.Render();
  }

  @Test(expected = IllegalArgumentException.class)
  public void collision1() {
    Meditor a = new EditorGraph();
    a.addNote(5, 0, "F", 0);
    a.addNote(5, 0, "F", 4);
    a.Render();
  }

  //deleting note in the middle of the note
  @Test
  public void del2() {
    Meditor a = new EditorGraph();
    a.addNote(5, 0, "F", 0);
    a.delNote(0, "F", 3);
    assertArrayEquals((new ArrayList<Note>()).toArray(), a.getGraph().toArray());
  }

  //deleting note that isn't there
  @Test(expected = IllegalArgumentException.class)
  public void del3() {
    Meditor a = new EditorGraph();
    a.addNote(5, 0, "F", 0);
    a.delNote(0, "C", 3);
  }

  //attempt to add a bad note
  @Test(expected = IllegalArgumentException.class)
  public void badNote() {
    Meditor a = new EditorGraph();
    a.addNote(0, 0, "F", 0);
  }

  //attempt to add a bad note
  @Test(expected = IllegalArgumentException.class)
  public void badNote1() {
    Meditor a = new EditorGraph();
    a.addNote(1, 0, "yolo", 0);
  }

  //attempt to add a bad note
  @Test(expected = IllegalArgumentException.class)
  public void badNote2() {
    Meditor a = new EditorGraph();
    a.addNote(1, 0, "C", -1);
  }

  //attempt to add a bad note
  @Test(expected = IllegalArgumentException.class)
  public void badNote3() {
    Meditor a = new EditorGraph();
    a.addNote(1, -3, "C", 0);
  }

  //editing note that isn't there
  @Test(expected = IllegalArgumentException.class)
  public void edi1() {
    Meditor a = new EditorGraph();
    a.addNote(5, 0, "F", 0);
    a.ediNote(1, 0, "C", 3);
  }

  //editing note with a poor resize option
  @Test(expected = IllegalArgumentException.class)
  public void edi2() {
    Meditor a = new EditorGraph();
    a.addNote(5, 0, "F", 0);
    a.ediNote(-1, 0, "C", 3);
  }

  //editing note with a poor resize option
  @Test
  public void edi3() {
    Meditor a = new EditorGraph();
    Meditor b = new EditorGraph();
    b.addNote(3, 0, "F", 3);
    a.addNote(5, 0, "F", 0);
    a.ediNote(3, 0, "F", 3);
    assertArrayEquals(a.getGraph().toArray(), b.getGraph().toArray());
  }

  //editing note with a poor resize option - I think the catch is catching the error
  @Test(expected = IllegalArgumentException.class)
  public void edi4() {
    Meditor a = new EditorGraph();
    a.addNote(5, 0, "F", 0);
    a.addNote(5, 0, "F", 7);
    a.ediNote(8, 0, "F", 0);
  }

}