package com.app.politcus.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Antoine on 11/10/2016.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {
  public static final String TABLE_QUIZZ = "q_quizz";
  public static final String COLUMN_ID = "ID";
  public static final String COLUMN_TITLE = "TITLE";
  public static final String COLUMN_ANSWER = "ANSWER";

  private static final String DATABASE_NAME = "politicus.db";
  private static final int DATABASE_VERSION = 1;

  // Commande sql pour la création de la base de données
  private static final String DATABASE_CREATE = "create table "
    + TABLE_QUIZZ + "(" + COLUMN_ID
    + " integer primary key autoincrement, " + COLUMN_TITLE
    + " text not null, " + COLUMN_ANSWER
    + " integer not null);";

  private static final String DATABASE_FILL = "insert into q_quizz values ('1','Quizz question 1','0');" +
    "  insert into q_quizz values ('2','Quizz question 2','1');" +
    "  insert into q_quizz values ('3','Quizz question 3','1');" +
    "  insert into q_quizz values ('4','Quizz question 4','0');" +
    "  insert into q_quizz values ('5','Quizz question 5','0');" +
    "  insert into q_quizz values ('6','Quizz question 6','1');";


  public MySQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
    database.execSQL(DATABASE_FILL);
    }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZZ);
    onCreate(db);
  }

}