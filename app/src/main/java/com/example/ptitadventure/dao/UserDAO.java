package com.example.ptitadventure.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ptitadventure.model.User;

public class UserDAO implements IUserDAO {
    private DBHelper dbHelper;

    public UserDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void changePassword(String username, String newPassword) {

    }

    @Override
    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = { "id" };
        String selection = "email = ? AND password = ?";
        String[] selectionArgs = { username, password };

        Cursor cursor = db.query(
                DBHelper.TABLE_USER,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean loginSuccessful = cursor.getCount() > 0;

        cursor.close();
        return loginSuccessful;
    }

    @Override
    public int getIDStudent(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = { "id" };
        String selection = "email = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(
                DBHelper.TABLE_USER,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int id = -1;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        }
        return id;
    }


}
