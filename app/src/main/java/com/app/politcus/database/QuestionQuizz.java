package com.app.politcus.database;

/**
 * Created by Antoine on 11/10/2016.
 */
public class QuestionQuizz {
  private long id;
  private String title;
  private int answer;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public long getAnswer() {
    return answer;
  }

  public void setAnswer(int answer) {
    this.answer = answer;
  }
  // Sera utilisée par ArrayAdapter dans la ListView
  @Override
  public String toString() {
    return title;
  }
}
