package com.douglas.myfoody.core.DAO;


import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.douglas.myfoody.core.database.AppDatabase;
import com.douglas.myfoody.core.models.User;

import java.util.List;

public class UserDAO implements BaseDAO<User> {

    private AppDatabase db;

    public UserDAO(Application application) {
        db = AppDatabase.getDBInstance(application);
    }

    @Override
    public SQLiteDatabase getWriteDB() {
        return db.getWritableDatabase();
    }

    @Override
    public SQLiteDatabase getReadDB() {
        return db.getReadableDatabase();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(int id) {
        return null;
    }

    public User findByEmail(String email) {
        User user = null;
        try {
            String[] columns = {
                    User.USER_TABLE.TB_COL.ID, User.USER_TABLE.TB_COL.EMAIL,
                    User.USER_TABLE.TB_COL.PASSWORD, User.USER_TABLE.TB_COL.FULL_NAME,
                    User.USER_TABLE.TB_COL.PHONE, User.USER_TABLE.TB_COL.ADDRESS,
                    User.USER_TABLE.TB_COL.ORDER_COUNT, User.USER_TABLE.TB_COL.IS_LOGGED_IN,
            };

            Cursor cursor = getReadDB().query(User.USER_TABLE.TB_NAME, columns,
                    User.USER_TABLE.TB_COL.EMAIL + " = '" + email + "'", null,
                    null, null, null);
            if (cursor != null && cursor.moveToFirst()) {

                user = new User();
                user.setID(Integer.parseInt(cursor.getString(0)));
                user.setEmail(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                user.setFullName(cursor.getString(3));
                user.setPhone(cursor.getString(4));
                user.setAddress(cursor.getString(5));
                user.setOrderCount(cursor.getString(6));
                user.setLoggedIn(cursor.getString(7));
                return user;
            }

        } catch (Exception ex) {
            // throw error message
            System.out.println(ex.getMessage());
        } finally {
            // Closing database connection
            getWriteDB().close();
        }

        return user;
    }

    @Override
    public boolean insert(User user) {

        try {
            ContentValues values = new ContentValues();
            values.put(User.USER_TABLE.TB_COL.EMAIL, user.getEmail());
            values.put(User.USER_TABLE.TB_COL.PASSWORD, user.getPassword());
            values.put(User.USER_TABLE.TB_COL.FULL_NAME, user.getFullName());
            values.put(User.USER_TABLE.TB_COL.PHONE, user.getPhone());
            values.put(User.USER_TABLE.TB_COL.ADDRESS, user.getAddress());
            values.put(User.USER_TABLE.TB_COL.ORDER_COUNT, user.getOrderCount());
            values.put(User.USER_TABLE.TB_COL.IS_LOGGED_IN, "false"); // default

            // Inserting Row
            long result = getWriteDB().insert(User.USER_TABLE.TB_NAME, null, values);

            if (result > 0)
                return true;
            else
                throw new Exception("Unable to create the record");

        } catch (Exception ex) {
            // throw error message
            System.out.println(ex.getMessage());
        } finally {
            // Closing database connection
            getWriteDB().close();
        }

        return false;
    }

    @Override
    public boolean update(User data) {
        return true;
    }

    @Override
    public boolean delete(int id) {
        return true;
    }

    public void deleteAll(){
        getWriteDB().execSQL("DELETE FROM " + User.USER_TABLE.TB_NAME);//delete all users
    }
}