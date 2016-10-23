package com.app.politcus.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.UUID;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;


public class MySQLiteHelper extends SQLiteOpenHelper {
  private SQLiteDatabase db;

  public static final String TABLE_QUIZZ = "q_quizz";
  public static final String TABLE_TEST = "q_politicus_test";
  public static final String TABLE_RESULTS_TEST = "results_test";
  public static final String TABLE_BEST_SCORE_QUIZZ = "best_score_quizz";
  public static final String TABLE_UUID = "uuid_tbl";

  public static final String COLUMN_ID = "ID";
  public static final String COLUMN_TITLE = "TITLE";
  public static final String COLUMN_ANSWER = "ANSWER";
  public static final String COLUMN_ORIENTATION = "ORIENTATION";
  public static final String COLUMN_HORIZONTAL_RESULT = "HORIZONTAL_RESULT";
  public static final String COLUMN_VERTICAL_RESULT = "VERTICAL_RESULT";
  public static final String COLUMN_SCORE = "SCORE";
  public static final String COLUMN_UUID = "UUID";

    private static final String DATABASE_NAME = "politicus.db";
  private static final int DATABASE_VERSION = 1;

  private static final String QUIZZ_TABLE_CREATE = "create table if not exists "
    + TABLE_QUIZZ + "(" + COLUMN_ID
    + " integer primary key autoincrement, " + COLUMN_TITLE
    + " text not null, " + COLUMN_ANSWER
    + " integer not null);";

  private static final String TEST_TABLE_CREATE = "create table if not exists "
    + TABLE_TEST + "(" + COLUMN_ID
    + " integer primary key autoincrement, " + COLUMN_TITLE
    + " text not null, " + COLUMN_ORIENTATION
    + " text not null);";

  private static final String RESULTS_TEST_TABLE_CREATE = "create table if not exists "
            + TABLE_RESULTS_TEST + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_HORIZONTAL_RESULT
            + " real not null, " + COLUMN_VERTICAL_RESULT
            + " real not null);";

  private static final String BEST_SCORE_QUIZZ_TABLE_CREATE = "create table if not exists "
          + TABLE_BEST_SCORE_QUIZZ + "(" + COLUMN_ID
          + " integer primary key autoincrement, " + COLUMN_SCORE
          + " integer not null);";

  private static final String UUID_TABLE_CREATE = "create table if not exists "
          + TABLE_UUID + "(" + COLUMN_ID
          + " integer primary key autoincrement, " + COLUMN_UUID
          + " text not null);";

  private static final String QUIZZ_FILL = "insert or ignore into " + TABLE_QUIZZ + " (ID, TITLE, ANSWER) values (1,'Le Prince de Monaco s''appelle Robert','0');";
  private static final String TEST_FILL = "insert or ignore into " + TABLE_TEST + " (ID, TITLE, ORIENTATION) values (1,'Entre l''intérêt des entreprises et l''intérêt de la société il y a un conflit important.','G');";
  private static final String BEST_SCORE_QUIZZ_FILL = "insert or ignore into " + TABLE_BEST_SCORE_QUIZZ + " (ID, SCORE) values (1, 0);";
  private static final String UUID_FILL = "insert or ignore into " + TABLE_UUID + " (ID, UUID) values (1,'" + UUID.randomUUID().toString() + "');";

  public MySQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    db = getWritableDatabase();
    db.execSQL(QUIZZ_TABLE_CREATE);
    db.execSQL(TEST_TABLE_CREATE);
    db.execSQL(RESULTS_TEST_TABLE_CREATE);
    db.execSQL(BEST_SCORE_QUIZZ_TABLE_CREATE);
    db.execSQL(UUID_TABLE_CREATE);
    db.execSQL(QUIZZ_FILL);
    db.execSQL(TEST_FILL);
    db.execSQL(BEST_SCORE_QUIZZ_FILL);
    db.execSQL(UUID_FILL);
    }

  @Override
  public void onCreate(SQLiteDatabase database) {}

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZZ);
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEST);
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTS_TEST);
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_BEST_SCORE_QUIZZ);
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_UUID);
    onCreate(db);
  }

}
