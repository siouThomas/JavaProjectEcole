package com.app.politcus.database;
import com.app.politcus.questions.QuestionType;
import com.app.politcus.questions.Question;
import com.app.politcus.questions.Answer;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
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
  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
    MySQLiteHelper.COLUMN_TITLE, MySQLiteHelper.COLUMN_ANSWER };

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
      allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
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
  public ArrayList<Question> getAllQuestionQuizz() {

    ArrayList<Question> questions = new ArrayList<Question>();

    //List<QuestionQuizz> questionsQuizz = new ArrayList<QuestionQuizz>();

    Cursor cursor = database.query(MySQLiteHelper.TABLE_QUIZZ,
      allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Question question = cursorToQuestion(cursor);
      questions.add(question);
      cursor.moveToNext();
    }
    // assurez-vous de la fermeture du curseur
    cursor.close();
    return questions;
  }

  private Question cursorToQuestion(Cursor cursor) {
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
}
