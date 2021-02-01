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


import java.io.File;
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

        CrimeCursorWrapper crimeCursorWrapper = queryCrimeCursor(null, null);

        if (crimeCursorWrapper == null || crimeCursorWrapper.getCount() == 0)
            return crimes;
        try {
            crimeCursorWrapper.moveToFirst();

            while (!crimeCursorWrapper.isAfterLast()) {
                Crime crime = crimeCursorWrapper.getCrime();
                crimes.add(crime);
                crimeCursorWrapper.moveToNext();

            }

        } finally {
            crimeCursorWrapper.close();

        }

        return crimes;
    }



    @Override
    public Crime getCrime(UUID uuid) {
        String where = cols.UUID + " = ?";
        String[] whereArgs = new String[]{uuid.toString()};

       CrimeCursorWrapper crimeCursorWrapper = queryCrimeCursor(where, whereArgs);

        if (crimeCursorWrapper == null || crimeCursorWrapper.getCount() == 0)
            return null;
        try {
            crimeCursorWrapper.moveToFirst();
            Crime crime = crimeCursorWrapper.getCrime();
            return crime;

        } finally {
            crimeCursorWrapper.close();
        }


    }

    private CrimeCursorWrapper queryCrimeCursor(String where, String[] whereArgs) {

       Cursor cursor =  mDatabase.query(
                CrimeDBSchema.CrimeTable.NAME,
                null,
                where,
                whereArgs,
                null,
                null,
                null);

       CrimeCursorWrapper crimeCursorWrapper = new CrimeCursorWrapper(cursor);

       return crimeCursorWrapper;
    }

    @Override
    public void insertCrime(Crime crime) {
        ContentValues values = getContentValues(crime);

         mDatabase.insert(CrimeDBSchema.CrimeTable.NAME, null, values);
    }

    public File getPhotoFile(Crime crime){
        File fileDir = mContext.getFilesDir();
        File photoFile = new File(fileDir , crime.getPhotoFileName());
        return photoFile;
    }


    private ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(cols.UUID, crime.getId().toString());
        values.put(cols.TITLE, crime.getTitle());
        values.put(cols.DATE, crime.getDate().getTime());
        values.put(cols.SOLVED, crime.isSolved() ? 1 : 0);
        values.put(cols.CHECKED, crime.isChecked() ? 1 : 0);
        values.put(cols.SUSPECT , crime.getSuspect());
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
