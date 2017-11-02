package com.demo.db.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StudModelOpt extends SQLiteOpenHelper {

  public static final String TAG = StudModelOpt.class.getSimpleName();
  private static final String DATABASE = "studentDetail";
  private static final int DATABASE_VERSION = 1;

  public StudModelOpt(Context context) {
    super(context, DATABASE, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    String cr_query = "CREATE TABLE ";
    cr_query += StudModel.TABLE_NAME;
    cr_query += "( " + StudModel.COL_ID;
    cr_query += " integer primary key autoincrement," + StudModel.COL_NAME;
    cr_query += " text," + StudModel.COL_ADD + " text," + StudModel.COL_GENDER + " text,"
        + StudModel.COL_PHONE + " text)";
    db.execSQL(cr_query);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("Drop table if exists " + StudModel.TABLE_NAME);
  }
}
