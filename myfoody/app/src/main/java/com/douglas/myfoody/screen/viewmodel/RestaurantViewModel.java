package com.douglas.myfoody.screen.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.douglas.myfoody.core.models.Restaurant;
import com.douglas.myfoody.core.repository.RestaurantRepository;

import java.util.Collections;
import java.util.List;

public class RestaurantViewModel extends AndroidViewModel {

    private RestaurantRepository restaurantRepository;
    private MutableLiveData<List<Restaurant>> mAllRestaurants;
    private MutableLiveData<Restaurant> mRestaurant;

    public RestaurantViewModel(Application application) {
        super(application);
        restaurantRepository = new RestaurantRepository(application);

        if (mAllRestaurants == null) {
            mAllRestaurants = new MutableLiveData<>();
        }

        if (mRestaurant == null) {
            mRestaurant = new MutableLiveData<>();
        }
    }

    public LiveData<List<Restaurant>> findAllRestaurants(String location) {
        List<Restaurant> restaurantList = null;
        if (!location.isEmpty()) {
            restaurantList = restaurantRepository.findAllByLocation(location);
        }

        if(location.isEmpty() || restaurantList.isEmpty()) {
            restaurantList = restaurantRepository.findAll();
        }

        mAllRestaurants.setValue(restaurantList);

        return mAllRestaurants;
    }

    public LiveData<List<Restaurant>> getAllRestaurants() {
        return mAllRestaurants;
    }

    public LiveData<Restaurant> findRestaurantByID(int id) {
        mRestaurant.setValue(restaurantRepository.findById(id));
        return mRestaurant;
    }

    public Restaurant getRestaurantByID(int id) {
        return restaurantRepository.findById(id);

    }
}