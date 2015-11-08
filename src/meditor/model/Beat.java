package meditor.model;

/**
 * Smallest time unit for now. Can be resting, starting, or regular.
 */
 class Beat {
  //set using Beat.Status.Resting
  enum Status {
    Resting,
    Starting,
    Regular;
  }
  protected Status status;
  //default Note is a resting note
  public Beat() {
    this.status = Status.Resting;
  }

  /**
   * A beat can be resting ('r'), the starting beat ('s'), or just a regular beat ('n').
   * @param w note status
   */
  public Beat(char w) {
    this.setStatus(w);
  }

  public void setStatus(char w){
    if (w=='r'){
      this.status = Status.Resting;
    } else if (w=='s'){
      this.status = Status.Starting;
    } else if (w=='n'){
      this.status = Status.Regular;
    } else {
      throw new IllegalArgumentException("Invalid passed beat status!");
    }
  }

  @Override
  public String toString(){
    if(status == status.Resting){
      return "o";
    } else if(status == status.Starting){
      return "X";
    }  else {
      return "-";
    }
  }
}
