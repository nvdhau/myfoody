package com.douglas.myfoody.core.DAO;


import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.douglas.myfoody.core.database.AppDatabase;
import com.douglas.myfoody.core.models.Restaurant;
import com.douglas.myfoody.core.models.User;


import java.util.ArrayList;
import java.util.List;

public class RestaurantDAO implements BaseDAO<Restaurant> {

    private AppDatabase db;

    public RestaurantDAO(Application application) {
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
    public List<Restaurant> findAll() {
        List<Restaurant> restaurants = new ArrayList<>();
        try {
            String[] columns = {
                    Restaurant.RESTAURANT_TABLE.TB_COL.ID, Restaurant.RESTAURANT_TABLE.TB_COL.NAME,
                    Restaurant.RESTAURANT_TABLE.TB_COL.ADDRESS, Restaurant.RESTAURANT_TABLE.TB_COL.CATEGORY,
                    Restaurant.RESTAURANT_TABLE.TB_COL.MENU, Restaurant.RESTAURANT_TABLE.TB_COL.RATING,
                    Restaurant.RESTAURANT_TABLE.TB_COL.IMAGE
            };

            Cursor cursor = getReadDB().query(Restaurant.RESTAURANT_TABLE.TB_NAME, columns,
                    null, null, null, null,
                    Restaurant.RESTAURANT_TABLE.TB_COL.ID + " ASC");

            if (cursor.moveToFirst()) {
                do {
                    Restaurant restaurant = new Restaurant();
                    restaurant.setID(cursor.getInt(0));
                    restaurant.setName(cursor.getString(1));
                    restaurant.setAddress(cursor.getString(2));
                    restaurant.setCategory(cursor.getString(3));
                    restaurant.setMenu(cursor.getString(4));
                    restaurant.setRating(cursor.getString(5));
                    restaurant.setImage(cursor.getString(6));
                    restaurants.add(restaurant);
                } while (cursor.moveToNext());
            }

            return restaurants;

        } catch (Exception ex) {
            // throw error message
            System.out.println(ex.getMessage());
        } finally {
            // Closing database connection
            getWriteDB().close();
        }

        return restaurants;
    }

    @Override
    public Restaurant findById(int id) {
        Restaurant restaurant = null;
        try {
            String[] columns = {
                    Restaurant.RESTAURANT_TABLE.TB_COL.ID, Restaurant.RESTAURANT_TABLE.TB_COL.NAME,
                    Restaurant.RESTAURANT_TABLE.TB_COL.ADDRESS, Restaurant.RESTAURANT_TABLE.TB_COL.CATEGORY,
                    Restaurant.RESTAURANT_TABLE.TB_COL.MENU, Restaurant.RESTAURANT_TABLE.TB_COL.RATING,
                    Restaurant.RESTAURANT_TABLE.TB_COL.IMAGE
            };

            Cursor cursor = getReadDB().query(Restaurant.RESTAURANT_TABLE.TB_NAME, columns,
                    Restaurant.RESTAURANT_TABLE.TB_COL.ID + " = '" + id + "'", null,
                    null, null, null);
            if (cursor != null && cursor.moveToFirst()) {

                restaurant = new Restaurant();
                restaurant.setID(Integer.parseInt(cursor.getString(0)));
                restaurant.setName(cursor.getString(1));
                restaurant.setAddress(cursor.getString(2));
                restaurant.setCategory(cursor.getString(3));
                restaurant.setMenu(cursor.getString(4));
                restaurant.setRating(cursor.getString(5));
                restaurant.setImage(cursor.getString(6));

                return restaurant;
            }

        } catch (Exception ex) {
            // throw error message
            System.out.println(ex.getMessage());
        } finally {
            // Closing database connection
            getWriteDB().close();
        }

        return restaurant;
    }

    public Restaurant findByName(String email) {
        Restaurant restaurant = null;
        try {
            // TODO Prepare your query. Refer to UserDAO for more information
            return restaurant;

        } catch (Exception ex) {
            // throw error message
            System.out.println(ex.getMessage());
        } finally {
            // Closing database connection
            getWriteDB().close();
        }

        return restaurant;
    }

    @Override
    public boolean insert(Restaurant restaurant) {
        return true;
    }

    @Override
    public boolean update(Restaurant restaurant) {
        return true;
    }

    @Override
    public boolean delete(int id) {
        return true;
    }

}