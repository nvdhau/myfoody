package com.douglas.myfoody.core.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.douglas.myfoody.core.models.Restaurant;
import com.douglas.myfoody.core.models.User;

public class AppDatabase extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "myfoody.db";
    private static AppDatabase sInstance;

    private AppDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.getWritableDatabase(); // it must be here in order to call the onCreate, onUpgrade
    }

    public static synchronized AppDatabase getDBInstance(Context context) {
        if (sInstance == null) {
            sInstance = new AppDatabase(context.getApplicationContext());
        }

        return sInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        initAppDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + User.USER_TABLE.TB_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Restaurant.RESTAURANT_TABLE.TB_NAME);
        onCreate(db);
    }

    private void initAppDB(SQLiteDatabase database) {
        // create user table
        database.execSQL("CREATE TABLE " + User.USER_TABLE.TB_NAME + "(" +
                User.USER_TABLE.TB_COL.ID + " integer primary key autoincrement, " +
                User.USER_TABLE.TB_COL.EMAIL + ", " +
                User.USER_TABLE.TB_COL.PASSWORD + ", " +
                User.USER_TABLE.TB_COL.FULL_NAME + ", " +
                User.USER_TABLE.TB_COL.PHONE + ", " +
                User.USER_TABLE.TB_COL.ADDRESS + ", " +
                User.USER_TABLE.TB_COL.ORDER_COUNT + ", " +
                User.USER_TABLE.TB_COL.CREATED_AT + ", " +
                User.USER_TABLE.TB_COL.IS_LOGGED_IN +
                ")"
        );

        database.execSQL("CREATE TABLE " + Restaurant.RESTAURANT_TABLE.TB_NAME + "(" +
                Restaurant.RESTAURANT_TABLE.TB_COL.ID + " integer primary key autoincrement, " +
                Restaurant.RESTAURANT_TABLE.TB_COL.NAME + ", " +
                Restaurant.RESTAURANT_TABLE.TB_COL.ADDRESS + ", " +
                Restaurant.RESTAURANT_TABLE.TB_COL.CATEGORY + ", " +
                Restaurant.RESTAURANT_TABLE.TB_COL.RATING + ", " +
                Restaurant.RESTAURANT_TABLE.TB_COL.MENU +
                ")"
        );

        // RUN INSERT QUERY TO INITIALIZE DATA

    }
}
