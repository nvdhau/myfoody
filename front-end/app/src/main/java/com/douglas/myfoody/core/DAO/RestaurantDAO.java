package com.douglas.myfoody.core.DAO;


import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.douglas.myfoody.core.database.AppDatabase;
import com.douglas.myfoody.core.models.Restaurant;


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
        return null;
    }

    @Override
    public Restaurant findById(int id) {
        return null;
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