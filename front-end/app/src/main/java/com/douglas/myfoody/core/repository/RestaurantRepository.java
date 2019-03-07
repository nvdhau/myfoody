package com.douglas.myfoody.core.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.douglas.myfoody.core.DAO.BaseDAO;
import com.douglas.myfoody.core.DAO.RestaurantDAO;
import com.douglas.myfoody.core.data.AppDatabase;
import com.douglas.myfoody.core.models.Restaurant;

import java.util.List;

public class RestaurantRepository {

    private RestaurantDAO restaurantDAO;
    private LiveData<List<Restaurant>> mRestaurants;

    public RestaurantRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        restaurantDAO = db.restaurantDAO();
        mRestaurants = restaurantDAO.findAll();
    }

    public LiveData<List<Restaurant>> getAllRestaurants() {
        return mRestaurants;
    }


}
