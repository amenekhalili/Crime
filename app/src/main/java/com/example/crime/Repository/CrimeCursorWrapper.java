package com.example.crime.Repository;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.crime.Model.Crime;
import com.example.crime.dataBase.CrimeDBSchema.CrimeTable.cols;


import java.util.Date;
import java.util.UUID;


public class CrimeCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime(){
        UUID uuid = UUID.fromString(getString(getColumnIndex(cols.UUID)));
        String title = getString(getColumnIndex(cols.TITLE));
        Date date = new Date(getLong(getColumnIndex(cols.DATE)));
        boolean solvedInt = getInt(getColumnIndex(cols.SOLVED)) == 0 ? false : true;
        boolean checkedInt = getInt(getColumnIndex(cols.CHECKED)) == 0 ? false : true;
        String suspect = getString(getColumnIndex(cols.SUSPECT));

        return new Crime(uuid, title, date, solvedInt, checkedInt , suspect);
    }


}
