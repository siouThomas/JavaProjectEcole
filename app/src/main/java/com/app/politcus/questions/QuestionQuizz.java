package com.app.politcus.questions;


/**
 * Created by Antoine on 13/10/2016.
 */

public class QuestionQuizz extends Question {

  private boolean answer;

  public QuestionQuizz(boolean answer) {
    this.answer = answer;
  }

  public boolean isAnswer() {
    return answer;
  }

  public void setAnswer(boolean answer) {
    this.answer = answer;
  }
}
