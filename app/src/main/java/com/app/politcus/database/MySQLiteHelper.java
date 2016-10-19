package com.app.politcus.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;


public class MySQLiteHelper extends SQLiteOpenHelper {
  private SQLiteDatabase db;

  public static final String TABLE_QUIZZ = "q_quizz";
  public static final String TABLE_TEST = "q_politicus_test";

  public static final String COLUMN_ID = "ID";
  public static final String COLUMN_TITLE = "TITLE";
  public static final String COLUMN_ANSWER = "ANSWER";
  public static final String COLUMN_ORIENTATION = "ORIENTATION";

  private static final String DATABASE_NAME = "politicus.db";
  private static final int DATABASE_VERSION = 1;

  // Commande sql pour la création de la table
  private static final String QUIZZ_TABLE_CREATE = "create table if not exists "
    + TABLE_QUIZZ + "(" + COLUMN_ID
    + " integer primary key autoincrement, " + COLUMN_TITLE
    + " text not null, " + COLUMN_ANSWER
    + " integer not null);";

  // Commande sql pour la création de la table
  private static final String TEST_TABLE_CREATE = "create table if not exists "
    + TABLE_TEST + "(" + COLUMN_ID
    + " integer primary key autoincrement, " + COLUMN_TITLE
    + " text not null, " + COLUMN_ORIENTATION
    + " text not null);";

  private static final String QUIZZ_FILL = "insert or ignore into " + TABLE_QUIZZ + " (ID, TITLE, ANSWER) values (1,'Le Prince de Monaco s''appelle Robert','0');";
  private static final String TEST_FILL = "insert or ignore into " + TABLE_TEST + " (ID, TITLE, ORIENTATION) values (1,'Entre l''intérêt des entreprises et l''intérêt de la société il y a un conflit important.','G');";

  public MySQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    db = getWritableDatabase();
    db.execSQL(QUIZZ_TABLE_CREATE);
    db.execSQL(TEST_TABLE_CREATE);
    db.execSQL(QUIZZ_FILL);
    db.execSQL(TEST_FILL);
    }

  @Override
  public void onCreate(SQLiteDatabase database) {
    Log.i("testanto","Oncreate");
    }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZZ);
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEST);
    onCreate(db);
  }

}
