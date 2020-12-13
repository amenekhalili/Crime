package com.example.crime.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crime.Model.User;
import com.example.crime.dataBase.CrimeDBHelper;
import com.example.crime.dataBase.CrimeDBSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDBRepository implements UserIRepository{

    private static UserDBRepository sIsInstance;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public static UserDBRepository getIsInstance(Context context) {

        if (sIsInstance == null)
            sIsInstance = new UserDBRepository(context);

        return sIsInstance;
    }

    private UserDBRepository(Context context) {
        mContext = context.getApplicationContext();
        CrimeDBHelper crimeDBHelper = new CrimeDBHelper(mContext);

        mDatabase = crimeDBHelper.getWritableDatabase();
    }





    //this method are for User.

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();

        Cursor cursor = mDatabase.query(
                CrimeDBSchema.UserTable.Name,
                null,
                null,
                null,
                null,
                null,
                null);
        if (cursor == null || cursor.getCount() == 0)
            return users;
        try {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                User user = ExtractUserFromCursor(cursor);

                users.add(user);
                cursor.moveToNext();

            }

        } finally {
            cursor.close();

        }

        return users;
    }

    @Override
    public User getUser(UUID uuid) {
        String where = CrimeDBSchema.UserTable.columns.UUID + " = ?";
        String[] whereArgs = new String[]{uuid.toString()};

        Cursor cursor = mDatabase.query(
                CrimeDBSchema.UserTable.Name,
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
            User user = ExtractUserFromCursor(cursor);
            return user;

        } finally {
            cursor.close();
        }

    }

    @Override
    public void insertUser(User user) {
        ContentValues values = getContentValues(user);
        mDatabase.insert(CrimeDBSchema.UserTable.Name ,null , values);
    }

    @Override
    public void deleteUser(User user) {
        String whereClause = CrimeDBSchema.UserTable.columns.UUID + " = ? ";
        String[] whereArgs =  new String[]{user.getUUID().toString()};
        mDatabase.delete(CrimeDBSchema.UserTable.Name , whereClause , whereArgs);
    }

    @Override
    public void UpdateUser(User user) {
        ContentValues values = getContentValues(user);
        String whereClause = CrimeDBSchema.UserTable.columns.UUID + " = ? ";
        String[] whereArgs =  new String[]{user.getUUID().toString()};
        mDatabase.update(CrimeDBSchema.UserTable.Name , values,whereClause , whereArgs);
    }


    private User ExtractUserFromCursor(Cursor cursor) {
        UUID uuid = UUID.fromString(cursor.getString(cursor.getColumnIndex(CrimeDBSchema.UserTable.columns.UUID)));
        String UserName = cursor.getString(cursor.getColumnIndex(CrimeDBSchema.UserTable.columns.User_Name));
        String PassWord = cursor.getString(cursor.getColumnIndex(CrimeDBSchema.UserTable.columns.Pass_Word));
        return new User(uuid , UserName , PassWord);
    }

    private ContentValues getContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(CrimeDBSchema.UserTable.columns.UUID , user.getUUID().toString());
        values.put(CrimeDBSchema.UserTable.columns.User_Name , user.getUserName());
        values.put(CrimeDBSchema.UserTable.columns.Pass_Word , user.getPassWord());
        return values;
    }



    @Override
    public int sizeList() {
      List<User> users = getUsers();
      return users.size();
    }

    // the end of user method.
}
