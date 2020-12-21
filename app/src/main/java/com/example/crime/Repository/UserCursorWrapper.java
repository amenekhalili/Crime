package com.example.crime.Repository;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.crime.Model.User;
import com.example.crime.dataBase.CrimeDBSchema.UserTable.columns;

import java.util.UUID;

public class UserCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getUser(){
        UUID uuid = UUID.fromString(getString(getColumnIndex(columns.UUID)));
        String UserName = getString(getColumnIndex(columns.User_Name));
        String PassWord = getString(getColumnIndex(columns.Pass_Word));
        return new User(uuid , UserName , PassWord);
    }
}
