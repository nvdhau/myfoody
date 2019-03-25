package com.douglas.myfoody.screen.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.widget.Toast;

import com.douglas.myfoody.core.DAO.RestaurantDAO;
import com.douglas.myfoody.core.models.Restaurant;
import com.douglas.myfoody.core.models.User;
import com.douglas.myfoody.core.repository.RestaurantRepository;
import com.douglas.myfoody.core.repository.UserRepository;

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
        if (location.isEmpty()) {
            mAllRestaurants.setValue(restaurantRepository.findAll());
        } else {
            mAllRestaurants.setValue(restaurantRepository.findAllByLocation(location));
        }

        return mAllRestaurants;
    }

    public LiveData<List<Restaurant>> getAllRestaurants() {
        return mAllRestaurants;
    }

    public LiveData<Restaurant> findRestaurantByID(int id) {
        mRestaurant.setValue(restaurantRepository.findById(id));
        return mRestaurant;
    }
}