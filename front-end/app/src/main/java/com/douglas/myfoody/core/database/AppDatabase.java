package com.douglas.myfoody.core.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.douglas.myfoody.core.models.Order;
import com.douglas.myfoody.core.models.Promotion;
import com.douglas.myfoody.core.models.Restaurant;
import com.douglas.myfoody.core.models.User;
import com.douglas.myfoody.core.utilities.JSONHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
        db.execSQL("DROP TABLE IF EXISTS " + Order.ORDER_TABLE.TB_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Promotion.PROMOTION_TABLE.TB_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Promotion.DISCOUNT_TABLE.TB_NAME);
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
                User.USER_TABLE.TB_COL.CREATED_AT +
                ")"
        );

        database.execSQL("CREATE TABLE " + Restaurant.RESTAURANT_TABLE.TB_NAME + "(" +
                Restaurant.RESTAURANT_TABLE.TB_COL.ID + " integer primary key autoincrement, " +
                Restaurant.RESTAURANT_TABLE.TB_COL.NAME + ", " +
                Restaurant.RESTAURANT_TABLE.TB_COL.ADDRESS + ", " +
                Restaurant.RESTAURANT_TABLE.TB_COL.CATEGORY + ", " +
                Restaurant.RESTAURANT_TABLE.TB_COL.RATING + ", " +
                Restaurant.RESTAURANT_TABLE.TB_COL.IMAGE + ", " +
                Restaurant.RESTAURANT_TABLE.TB_COL.MENU +
                ")"
        );

        // RUN INSERT QUERY TO INITIALIZE DATA

        // Tables for User - Start
        try {
            ContentValues values = new ContentValues();
            values.put(User.USER_TABLE.TB_COL.EMAIL, "user@gmail.com");
            values.put(User.USER_TABLE.TB_COL.PASSWORD, "123");
            values.put(User.USER_TABLE.TB_COL.FULL_NAME, "User");
            values.put(User.USER_TABLE.TB_COL.PHONE, "123456789");
            values.put(User.USER_TABLE.TB_COL.ADDRESS, "Douglas College");

            // Inserting Row
            if (database.insert(User.USER_TABLE.TB_NAME, null, values) <= 0)
                throw new Exception("Unable to create the record");

        } catch (Exception ex) {
            // throw error message
            System.out.println(ex.getMessage());
        }
        // Tables for User - End

        // Tables for Restaurant - Start
        String file = "res/raw/restaurants.json";
        String json = null;
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "utf-8");

            List<Restaurant> restaurants = JSONHelper.getRestaurantListsFromJSON(json);


            for(int i=0; i<restaurants.size(); i++){
                ContentValues values = new ContentValues();
                values.put(Restaurant.RESTAURANT_TABLE.TB_COL.NAME, restaurants.get(i).getName());
                values.put(Restaurant.RESTAURANT_TABLE.TB_COL.ADDRESS, restaurants.get(i).getAddress());
                values.put(Restaurant.RESTAURANT_TABLE.TB_COL.CATEGORY, restaurants.get(i).getCategory());
                values.put(Restaurant.RESTAURANT_TABLE.TB_COL.RATING, restaurants.get(i).getRating());
                values.put(Restaurant.RESTAURANT_TABLE.TB_COL.IMAGE, restaurants.get(i).getImage());
                values.put(Restaurant.RESTAURANT_TABLE.TB_COL.MENU, restaurants.get(i).getMenu());

                // Inserting Row
                if(database.insert(Restaurant.RESTAURANT_TABLE.TB_NAME, null, values) <= 0){
                    System.out.println("AppDatabase: fail to insert restaurant data!");
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // Tables for Restaurant - End

        // Table for Order - Start
        database.execSQL("CREATE TABLE " + Order.ORDER_TABLE.TB_NAME + "(" +
                Order.ORDER_TABLE.TB_COL.ID + " integer primary key, " +
                Order.ORDER_TABLE.TB_COL.USER_EMAIL + ", " +
                Order.ORDER_TABLE.TB_COL.RESTAURANT_ID + " integer, " +
                Order.ORDER_TABLE.TB_COL.ITEMS_JSON + ", " +
                Order.ORDER_TABLE.TB_COL.DELIVERY_ADDRESS + ", " +
                Order.ORDER_TABLE.TB_COL.SPECIAL_INSTRUCTION + ", " +
                Order.ORDER_TABLE.TB_COL.SUB_TOTAL + " real, " +
                Order.ORDER_TABLE.TB_COL.DELIVERY_FEE + " real, " +
                Order.ORDER_TABLE.TB_COL.DISCOUNT + " real, " +
                Order.ORDER_TABLE.TB_COL.TAX + " real, " +
                Order.ORDER_TABLE.TB_COL.TOTAL + " real, " +
                Order.ORDER_TABLE.TB_COL.CREATED_AT +
                ")"
        );
        // Table for Order - End

        // Tables for Promotions - Start
        database.execSQL("CREATE TABLE " + Promotion.PROMOTION_TABLE.TB_NAME + "(" +
                Promotion.PROMOTION_TABLE.TB_COL.PROMOTION_CODE + " primary key, " +
                Promotion.PROMOTION_TABLE.TB_COL.DISCOUNT_AMOUNT + " real, " +
                Promotion.PROMOTION_TABLE.TB_COL.DISCOUNT_TYPE +
                ")"
        );
        database.execSQL("CREATE TABLE " + Promotion.DISCOUNT_TABLE.TB_NAME + "(" +
                Promotion.DISCOUNT_TABLE.TB_COL.ID + " integer primary key, " +
                Promotion.DISCOUNT_TABLE.TB_COL.USER_EMAIL + ", " +
                Promotion.DISCOUNT_TABLE.TB_COL.PROMOTION_CODE + ", " +
                Promotion.DISCOUNT_TABLE.TB_COL.EXPIRY_DATE +
                ")"
        );
        database.execSQL("INSERT INTO " + Promotion.PROMOTION_TABLE.TB_NAME + " VALUES " +
                "('INVITE10'," + "'10', " + "'Percent')"
        );
        // Tables for Promotions - End
    }
}
