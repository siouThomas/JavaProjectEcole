package com.app.politcus.database;
import com.app.politcus.questions.*;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
/**
 * Created by Antoine on 11/10/2016.
 */

public class QuizzDAO {
  // Champs de la base de données
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;
  private String[] allColumnsQuizz = { MySQLiteHelper.COLUMN_ID,
    MySQLiteHelper.COLUMN_TITLE, MySQLiteHelper.COLUMN_ANSWER };
  private String[] allColumnsTest = { MySQLiteHelper.COLUMN_ID,
    MySQLiteHelper.COLUMN_TITLE, MySQLiteHelper.COLUMN_ORIENTATION };

  public QuizzDAO(Context context) {
    dbHelper = new MySQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  /*
  public QuestionQuizz createQuestionQuizz(String title, int answer) {
    ContentValues values = new ContentValues();
    values.put(MySQLiteHelper.COLUMN_TITLE, title);
    values.put(MySQLiteHelper.COLUMN_ANSWER, answer);
    long insertId = database.insert(MySQLiteHelper.TABLE_QUIZZ, null,
      values);
    Cursor cursor = database.query(MySQLiteHelper.TABLE_QUIZZ,
      allColumnsQuizz, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
      null, null, null);
    cursor.moveToFirst();
    QuestionQuizz newQuestionQuizz = cursorToQuestion(cursor);
    cursor.close();
    return newQuestionQuizz;
  }

  public void deleteQuestionQuizz(QuestionQuizz questionQuizz) {
    long id = questionQuizz.getId();
    System.out.println("Quizz question deleted with id: " + id);
    database.delete(MySQLiteHelper.TABLE_QUIZZ, MySQLiteHelper.COLUMN_ID
      + " = " + id, null);
  }
*/
  public ArrayList<QuestionQuizz> getAllQuestionQuizz() {

    ArrayList<QuestionQuizz> questionsQuizz = new ArrayList<QuestionQuizz>();

    //List<QuestionQuizz> questionsQuizz = new ArrayList<QuestionQuizz>();

    Cursor cursor = database.query(MySQLiteHelper.TABLE_QUIZZ,
      allColumnsQuizz, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      QuestionQuizz questionQuizz = cursorToQuestionQuizz(cursor);
      questionsQuizz.add(questionQuizz);
      cursor.moveToNext();
    }
    // assurez-vous de la fermeture du curseur
    cursor.close();
    return questionsQuizz;
  }

  public ArrayList<QuestionTest> getAllQuestionTest() {

    ArrayList<QuestionTest> questionsTest = new ArrayList<QuestionTest>();

    //List<QuestionQuizz> questionsQuizz = new ArrayList<QuestionQuizz>();

    Cursor cursor = database.query(MySQLiteHelper.TABLE_TEST,
      allColumnsTest, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      QuestionTest questionTest = cursorToQuestionTest(cursor);
      questionsTest.add(questionTest);
      cursor.moveToNext();
    }
    // assurez-vous de la fermeture du curseur
    cursor.close();
    return questionsTest;
  }

  private Question cursorToQuestionQuizz(Cursor cursor) {
    Question question = new Question();

    question.setId((int)cursor.getLong(0));
    question.setTitle(cursor.getString(1));

    question.setType(QuestionType.TrueFalse);

    Answer answer = Answer.None;

    if (cursor.getInt(2) == 0 )
      answer = Answer.False;
    else
      answer = Answer.True;

    try {
      question.addAnswers(answer);
    } catch (Exception e) {
      e.printStackTrace();
    }

    try {
      question.addChoices(Answer.True);
      question.addChoices(Answer.False);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return question;
  }

  private Question cursorToQuestionTest(Cursor cursor) {
    Question question = new Question();

    question.setId((int)cursor.getLong(0));
    question.setTitle(cursor.getString(1));

    question.setType(QuestionType.MultiAnswer);

    Answer answer = Answer.None;

    if (cursor.getString(2).equals("G") )
      answer = Answer.Gauche;
    if (cursor.getString(2).equals("D") )
      answer = Answer.Droite;
    if (cursor.getString(2).equals("C") )
      answer = Answer.Communautariste;
    if (cursor.getString(2).equals("L") )
      answer = Answer.Libertaire;

    try {
      question.addAnswers(answer);
    } catch (Exception e) {
      e.printStackTrace();
    }

    try {
      question.addChoices(Answer.True);
      question.addChoices(Answer.False);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return question;
  }
}
