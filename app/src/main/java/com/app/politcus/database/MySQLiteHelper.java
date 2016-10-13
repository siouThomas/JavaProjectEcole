package com.app.politcus.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Antoine on 11/10/2016.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {
  public static final String TABLE_QUIZZ = "q_quizz";
  public static final String TABLE_TEST = "q_politicus_test";

  public static final String COLUMN_ID = "ID";
  public static final String COLUMN_TITLE = "TITLE";
  public static final String COLUMN_ANSWER = "ANSWER";
  public static final String COLUMN_ORIENTATION = "ORIENTATION";

  private static final String DATABASE_NAME = "politicus.db";
  private static final int DATABASE_VERSION = 1;

  // Commande sql pour la création de la table
  private static final String QUIZZ_TABLE_CREATE = "create table "
    + TABLE_QUIZZ + "(" + COLUMN_ID
    + " integer primary key autoincrement, " + COLUMN_TITLE
    + " text not null, " + COLUMN_ANSWER
    + " integer not null);";

  // Commande sql pour la création de la table
  private static final String TEST_TABLE_CREATE = "create table "
    + TABLE_TEST + "(" + COLUMN_ID
    + " integer primary key autoincrement, " + COLUMN_TITLE
    + " text not null, " + COLUMN_ORIENTATION
    + " text not null);";

  private static final String QUIZZ_FILL = "insert into " + TABLE_QUIZZ + " values ('1','Quizz question 1','0');" +
    "  insert into q_quizz values ('2','Quizz question 2','1');" +
    "  insert into q_quizz values ('3','Quizz question 3','1');" +
    "  insert into q_quizz values ('4','Quizz question 4','0');" +
    "  insert into q_quizz values ('5','Quizz question 5','0');" +
    "  insert into q_quizz values ('6','Quizz question 6','1');";

  private static final String TEST_FILL = "insert into " + TABLE_TEST + " values ('1','Test question 1','G');";

  public MySQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(QUIZZ_TABLE_CREATE);
    database.execSQL(TEST_TABLE_CREATE);
    database.execSQL(QUIZZ_FILL);
    database.execSQL(TEST_FILL);
    }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZZ);
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEST);
    onCreate(db);
  }

}
