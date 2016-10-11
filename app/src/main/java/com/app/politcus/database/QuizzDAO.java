package com.app.politcus.database;
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
    QuestionQuizz newQuestionQuizz = cursorToQuestionQuizz(cursor);
    cursor.close();
    return newQuestionQuizz;
  }

  public void deleteQuestionQuizz(QuestionQuizz questionQuizz) {
    long id = questionQuizz.getId();
    System.out.println("Quizz question deleted with id: " + id);
    database.delete(MySQLiteHelper.TABLE_QUIZZ, MySQLiteHelper.COLUMN_ID
      + " = " + id, null);
  }

  public List<QuestionQuizz> getAllQuestionQuizz() {
    List<QuestionQuizz> questionsQuizz = new ArrayList<QuestionQuizz>();

    Cursor cursor = database.query(MySQLiteHelper.TABLE_QUIZZ,
      allColumns, null, null, null, null, null);

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

  private QuestionQuizz cursorToQuestionQuizz(Cursor cursor) {
    QuestionQuizz questionQuizz = new QuestionQuizz();
    questionQuizz.setId(cursor.getLong(0));
    questionQuizz.setTitle(cursor.getString(1));
    questionQuizz.setAnswer(cursor.getInt(2));

    return questionQuizz;
  }
}
