package com.app.politcus.database;
import com.app.politcus.App;
import com.app.politcus.questions.*;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class QuizzDAO {

  // Champs de la base de données
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;
  private String[] allColumnsQuizz = { MySQLiteHelper.COLUMN_ID,
    MySQLiteHelper.COLUMN_TITLE, MySQLiteHelper.COLUMN_ANSWER };
  private String[] allColumnsTest = { MySQLiteHelper.COLUMN_ID,
    MySQLiteHelper.COLUMN_TITLE, MySQLiteHelper.COLUMN_ORIENTATION };

  private static QuizzDAO instance = null;

  public static final QuizzDAO getInstance(){
    if (instance == null) {
      synchronized (QuizzDAO.class) {
        if (instance == null) {
          instance = new QuizzDAO();
        }
      }
    }
    return instance;
  }

  private QuizzDAO() {
    dbHelper = new MySQLiteHelper(App.get().getApplicationContext());
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

  private QuestionQuizz cursorToQuestionQuizz(Cursor cursor) {
    QuestionQuizz questionQuizz = new QuestionQuizz();

    questionQuizz.setId((int)cursor.getLong(0));
    questionQuizz.setTitle(cursor.getString(1));

    if (cursor.getInt(2) == 0 )
      questionQuizz.setAnswer(false);
    else
      questionQuizz.setAnswer(true);

    return questionQuizz;
  }

  private QuestionTest cursorToQuestionTest(Cursor cursor) {
    QuestionTest questionTest = new QuestionTest();

    questionTest.setId((int)cursor.getLong(0));
    questionTest.setTitle(cursor.getString(1));

    if (cursor.getString(2).equals("G") )
      questionTest.setOrientation(Orientation.Gauche);
    if (cursor.getString(2).equals("D") )
      questionTest.setOrientation(Orientation.Droite);
    if (cursor.getString(2).equals("C") )
      questionTest.setOrientation(Orientation.Communautariste);
    if (cursor.getString(2).equals("L") )
      questionTest.setOrientation(Orientation.Libertaire);

    return questionTest;
  }
}
