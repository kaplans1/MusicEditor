package cs3500.music.model;

/**
 * To represent a repeat in the music.
 */
public class Repeat {
    //beat to skip from
    int from;
    //beat to skip to
    int to;
    //finds the beat to skip forward from
    //once beat is hit, skips to from + 1
    int skipfrom;


    //skips from one place, to some place in piece, skips over from skipfrom
    //beat to from+1 beat once skipfrom beat is hit.
    public Repeat(int from, int to, int skipfrom){
        //skips from one place to beginning of piece
        if( (to == -1) && (skipfrom==-1)) {
            this.from = from;
            this.to=0;
            this.skipfrom = from;
        //skips from one place to some place in piece
        // - no jumping over played part
        } else if( (to != -1) && skipfrom==-1) {
            this.from = from;
            this.to=to;
            this.skipfrom = from;
            //skips from one place to some place in piece
            // - jumping over something already played
        } else if( (to == -1) && skipfrom!=1) {
            this.from = from;
            this.to=0;
            this.skipfrom = skipfrom;
            //all arguments present
        } else if( (to != -1) && skipfrom!=1) {
            this.from = from;
            this.to=to;
            this.skipfrom = skipfrom;
        } else {
            throw new IllegalArgumentException("Shouldn't be here");
        }
    }
}
