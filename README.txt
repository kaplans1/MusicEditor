Controller Notes:

Notes can be added, deleted, and moved through keyboard input.

At any point in the process the escape key will clear the input string. Inputting a poorly
formatted string will result in nothing happening.

The process for adding a note is started by selecting the note (a-g) on a keyboard.
This followed by whether or not it is a sharp note using (or not using) the pound symbol.
Next the octave is selected using a number 0-9. Next one presses the letter 'n' for the
note to be added. Then one presses the beat number to signify where to place the note.
Then one presses the letter 'l' to signify that one is ready to input the
length of the desired note. Then one inputs the desired length of the new note. Finally, one
presses enter and the command is executed.

The process for deleting a note is started by selecting the note (a-g) on a keyboard.
This followed by whether or not it is a sharp note using (or not using) the pound symbol.
Next the octave is selected using a number 0-9. Next one presses the letter 'x' for the
note to be deleted. Then one presses the beat number to signify where then note should be deleted
from. Finally, one presses enter and the command is executed.

The process for moving a note is started by selecting the note (a-g) on a keyboard.
This followed by whether or not it is a sharp note using (or not using) the pound symbol.
Next the octave is selected using a number 0-9. Next one presses the letter 'm' for the
note to be moved. Then one presses the beat number to signify where the note is to be moved from.
The process for moving a note is continued by selecting the note (a-g) on a keyboard which
will signify where the note will be moved to. This followed by whether or not it is a sharp
note using (or not using) the pound symbol.
Next the octave is selected using a number 0-9. Next one presses the letter 'o' for the
note to be moved. Then one presses the beat number to signify where the note is to be moved to.
Finally, one presses enter and the command is executed.

The process for adding a third is started by selecting the note (a-g) on a keyboard.
This followed by whether or not it is a sharp note using (or not using) the pound symbol.
Next the octave is selected using a number 0-9. Next one presses the letter 'p' for the
third to be added. Then one presses the beat number to signify where then third should be added
Finally, one presses enter and the command is executed.

While complex, it allows great extensibility in command processing. Additionally, the view
can be scrolled right using the right arrow, and left using the left arrow. Additionally, pressing
the space bar will pause or play the music. The home and end keys bring the view and sound to
 the beginning, and end of the piece, respectively.


From Homework 06:
Changes:

Model changed from 'bag o' notes' to something that is faster for viewing.

For the notes, a TreeMap works well because it keeps the notes in order by the key. Making
the key the start time of the note, we have all the notes in order as they are going to be
played. Additionally, returning all notes starting at a given time allows for fast rendering,
since not that many notes can start at the same time, and log time access of treemap is better
than iterating over an array looking at all notes.

Separate representations of pitches and pitch identifications were added to facilitate viewing.

Main method can use a builder to make a music piece from a text file of 'notes' in the format:
note start beat end beat instrument pitch volume

User can choose among different views using 8 unique strings:
"our-visual" or "their-visual" - returns a gui view
"our-console" or "their-console" - prints out a console view, returns string copy of view
"our-midi" or "their-midi" - plays music of music piece
"our-combo" or "their-combo" - displays a gui view and plays the piece of music simultaneously

