package com.example.crime.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.crime.dataBase.CrimeDBSchema.CrimeTable.cols;

import androidx.annotation.Nullable;

public class CrimeDBHelper extends SQLiteOpenHelper {

    public CrimeDBHelper(@Nullable Context context) {
        super(context,CrimeDBSchema.NAME, null, CrimeDBSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sbQuery = new StringBuilder();
        sbQuery.append("CREATE TABLE " + CrimeDBSchema.CrimeTable.NAME + " (");
        sbQuery.append(cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,");
        sbQuery.append(cols.UUID + " TEXT NOT NULL,");
        sbQuery.append(cols.TITLE + " TEXT, ");
        sbQuery.append(cols.DATE + " TEXT, ");
        sbQuery.append(cols.SOLVED + " INTEGER,");
        sbQuery.append(cols.CHECKED + " INTEGER");
        sbQuery.append(");");
        db.execSQL(sbQuery.toString());




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
