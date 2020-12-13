package com.example.crime.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crime.Model.Crime;
import com.example.crime.Model.User;
import com.example.crime.dataBase.CrimeDBHelper;
import com.example.crime.dataBase.CrimeDBSchema;
import static com.example.crime.dataBase.CrimeDBSchema.CrimeTable.cols;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CrimeDBRepository implements IRepository {


    private static CrimeDBRepository sIsInstance;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public static CrimeDBRepository getIsInstance(Context context) {

        if (sIsInstance == null)
            sIsInstance = new CrimeDBRepository(context);

        return sIsInstance;
    }

    private CrimeDBRepository(Context context) {
        mContext = context.getApplicationContext();
        CrimeDBHelper crimeDBHelper = new CrimeDBHelper(mContext);

        mDatabase = crimeDBHelper.getWritableDatabase();
    }

    @Override
    public List<Crime> getCrimes() {
        List<Crime> crimes = new ArrayList<>();

        Cursor cursor = mDatabase.query(
                CrimeDBSchema.CrimeTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor == null || cursor.getCount() == 0)
            return crimes;
        try {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Crime crime = ExtractCrimeFromCursor(cursor);
                crimes.add(crime);
                cursor.moveToNext();

            }

        } finally {
            cursor.close();

        }

        return crimes;
    }

    private Crime ExtractCrimeFromCursor(Cursor cursor) {
        UUID uuid = UUID.fromString(cursor.getString(cursor.getColumnIndex(cols.UUID)));
        String title = cursor.getString(cursor.getColumnIndex(cols.TITLE));
        Date date = new Date(cursor.getLong(cursor.getColumnIndex(cols.DATE)));
        boolean solvedInt = cursor.getInt(cursor.getColumnIndex(cols.SOLVED)) == 0 ? false : true;
        boolean checkedInt = cursor.getInt(cursor.getColumnIndex(cols.CHECKED)) == 0 ? false : true;

        return new Crime(uuid, title, date, solvedInt, checkedInt);
    }

    @Override
    public Crime getCrime(UUID uuid) {
        String where = cols.UUID + " = ?";
        String[] whereArgs = new String[]{uuid.toString()};

        Cursor cursor = mDatabase.query(
                CrimeDBSchema.CrimeTable.NAME,
                null,
                where,
                whereArgs,
                null,
                null,
                null);

        if (cursor == null || cursor.getCount() == 0)
            return null;
        try {
            cursor.moveToFirst();
            Crime crime = ExtractCrimeFromCursor(cursor);
            return crime;

        } finally {
            cursor.close();
        }


    }

    @Override
    public void insertCrime(Crime crime) {
        ContentValues values = getContentValues(crime);

        mDatabase.insert(CrimeDBSchema.CrimeTable.NAME, null, values);
    }

    private ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(cols.UUID, crime.getId().toString());
        values.put(cols.TITLE, crime.getTitle());
        values.put(cols.DATE, crime.getDate().getTime());
        values.put(cols.SOLVED, crime.isSolved() ? 1 : 0);
        values.put(cols.CHECKED, crime.isChecked() ? 1 : 0);
        return values;
    }

    @Override
    public void updateCrime(Crime crime) {
        ContentValues values = getContentValues(crime);
        String whereClause = cols.UUID + " = ?";
        String[] whereArgs = new String[]{crime.getId().toString()};
        mDatabase.update(CrimeDBSchema.CrimeTable.NAME, values, whereClause, whereArgs);
    }

    @Override
    public void deleteCrime(Crime crime) {

        String whereClause = cols.UUID + " = ?";
        String[] whereArgs = new String[]{crime.getId().toString()};
        mDatabase.delete(CrimeDBSchema.CrimeTable.NAME, whereClause, whereArgs);

    }

    @Override
    public int getPosition(UUID id) {
        List<Crime> crimes = getCrimes();
        for (int i = 0; i < crimes.size(); i++) {
            if (crimes.get(i).getId().equals(id))
                return i;

        }
        return -1;
    }


    @Override
    public int sizeList() {
        List<Crime> crimes = getCrimes();
        return crimes.size();
    }



}
