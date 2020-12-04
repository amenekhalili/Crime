package com.example.crime.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.crime.dataBase.CrimeDBSchema.CrimeTable.cols;
import com.example.crime.dataBase.CrimeDBSchema.UserTable.columns;

import androidx.annotation.Nullable;

public class CrimeDBHelper extends SQLiteOpenHelper {

    public CrimeDBHelper(@Nullable Context context) {
        super(context,CrimeDBSchema.NAME, null, CrimeDBSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder sbQuery = CrimeTable();
        db.execSQL(sbQuery.toString());

        StringBuilder stringBuilder = UserTable();
        db.execSQL(stringBuilder.toString());

    }

    private StringBuilder UserTable() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE " + CrimeDBSchema.UserTable.Name + " (");
        stringBuilder.append(columns.ID + "INTEGER PRIMARY KEY AUTOINCREMENT,");
        stringBuilder.append(columns.UUID + " TEXT NOT NULL,");
        stringBuilder.append(columns.User_Name + "TEXT NOT NULL , ");
        stringBuilder.append(columns.Pass_Word + "TEXT NOT NULL");
        stringBuilder.append(");");
        return stringBuilder;
    }

    private StringBuilder CrimeTable() {
        StringBuilder sbQuery = new StringBuilder();
        sbQuery.append("CREATE TABLE " + CrimeDBSchema.CrimeTable.NAME + " (");
        sbQuery.append(cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,");
        sbQuery.append(cols.UUID + " TEXT NOT NULL,");
        sbQuery.append(cols.TITLE + " TEXT, ");
        sbQuery.append(cols.DATE + " TEXT, ");
        sbQuery.append(cols.SOLVED + " INTEGER,");
        sbQuery.append(cols.CHECKED + " INTEGER");
        sbQuery.append(");");
        return sbQuery;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
