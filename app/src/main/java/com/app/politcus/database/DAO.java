package com.app.politcus.database;
import com.app.politcus.App;
import com.app.politcus.questions.*;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class DAO {

  // Champs de la base de données
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;
  private String[] allColumnsQuizz = { MySQLiteHelper.COLUMN_ID,
    MySQLiteHelper.COLUMN_TITLE, MySQLiteHelper.COLUMN_ANSWER };
  private String[] allColumnsTest = { MySQLiteHelper.COLUMN_ID,
    MySQLiteHelper.COLUMN_TITLE, MySQLiteHelper.COLUMN_ORIENTATION };

  private static DAO instance = null;

  public static final DAO getInstance(){
    if (instance == null) {
      synchronized (DAO.class) {
        if (instance == null) {
          instance = new DAO();
        }
      }
    }
    return instance;
  }

  private DAO() {
    dbHelper = new MySQLiteHelper(App.getContext());
    if(dbHelper == null){
      Log.i("Error", "il s'est passé une merde");
    } else {
      Log.i("Error", "db helper initialisé");
    }
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    database.close();
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

    this.open();
    Cursor cursor = database.query(dbHelper.TABLE_QUIZZ,
      allColumnsQuizz, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      QuestionQuizz questionQuizz = cursorToQuestionQuizz(cursor);
      questionsQuizz.add(questionQuizz);
      cursor.moveToNext();
    }
    // assurez-vous de la fermeture du curseur
    cursor.close();
    //this.close();
    return questionsQuizz;
  }

  public ArrayList<QuestionTest> getAllQuestionTest() {

    ArrayList<QuestionTest> questionsTest = new ArrayList<QuestionTest>();

    //List<QuestionQuizz> questionsQuizz = new ArrayList<QuestionQuizz>();
    this.open();
    Cursor cursor = database.query(dbHelper.TABLE_TEST,
      allColumnsTest, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      QuestionTest questionTest = cursorToQuestionTest(cursor);
      questionsTest.add(questionTest);
      cursor.moveToNext();
    }
    // assurez-vous de la fermeture du curseur
    cursor.close();
    //this.close();
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

  public void launchAsync(){
    AsyncUpdateBDD asyncUpdateBDD = new AsyncUpdateBDD();
    asyncUpdateBDD.execute();
  }

  public void updateDb(ArrayList<QuestionTest> questionsTestOnline, ArrayList<QuestionQuizz> questionsQuizzOnline){

    this.open();
    int nbQuestionsTestOnline = questionsTestOnline.size();
    int nbQuestionsQuizzOnline = questionsQuizzOnline.size();

    ArrayList<QuestionTest> questionsTest = getAllQuestionTest();
    int nbQuestionsTest = questionsTest.size();

    ArrayList<QuestionQuizz> questionsQuizz = getAllQuestionQuizz();
    int nbQuestionsQuizz = questionsQuizz.size();

    if (nbQuestionsTestOnline > nbQuestionsTest){
      for (int i =nbQuestionsTest + 1 ; i<=nbQuestionsTestOnline; i++){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TITLE, questionsTestOnline.get(i-1).getTitle());
        if (questionsTestOnline.get(i-1).getOrientation()==Orientation.Gauche )
          values.put(MySQLiteHelper.COLUMN_ORIENTATION, "G");
        if (questionsTestOnline.get(i-1).getOrientation()==Orientation.Droite )
          values.put(MySQLiteHelper.COLUMN_ORIENTATION, "D");
        if (questionsTestOnline.get(i-1).getOrientation()==Orientation.Communautariste )
          values.put(MySQLiteHelper.COLUMN_ORIENTATION, "C");
        if (questionsTestOnline.get(i-1).getOrientation()==Orientation.Libertaire )
          values.put(MySQLiteHelper.COLUMN_ORIENTATION, "L");
        long insertId = database.insert(MySQLiteHelper.TABLE_TEST, null,
          values);
      }
    }

    if (nbQuestionsQuizzOnline > nbQuestionsQuizz){
      for (int i =nbQuestionsQuizz + 1 ; i<=nbQuestionsQuizzOnline; i++){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TITLE, questionsQuizzOnline.get(i-1).getTitle());
        if (questionsQuizzOnline.get(i-1).getAnswer() )
          values.put(MySQLiteHelper.COLUMN_ANSWER, 1);
        else
          values.put(MySQLiteHelper.COLUMN_ANSWER, 0);

        long insertId = database.insert(MySQLiteHelper.TABLE_QUIZZ, null,
          values);
      }
    }

    //this.close();
  }

}
