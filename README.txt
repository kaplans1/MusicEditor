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

User can choose among different views using 3 unique strings:
"visual" - returns a gui view
"console" - prints out a console view, returns string copy of view
"midi" - plays music of music piece