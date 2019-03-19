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

    public RestaurantViewModel(Application application) {
        super(application);
        restaurantRepository = new RestaurantRepository(application);

        if (mAllRestaurants == null) {
            mAllRestaurants = new MutableLiveData<>();
        }
    }

    public LiveData<List<Restaurant>> findAllRestaurants() {
        mAllRestaurants.setValue(restaurantRepository.findAll());
        return mAllRestaurants;
    }

    public LiveData<List<Restaurant>> getAllRestaurants() {
        return mAllRestaurants;
    }
//
//    public void setUser(User user) {
//        mUser.setValue(user);
//    }
//
//    public boolean insert(User user) {
//        if (!userRepository.add(user)) {
//            Toast.makeText(getApplication(), "Cannot insert user!", Toast.LENGTH_SHORT).show();
//            return false;
//        } else {
//            setUser(user);
//            return true;
//        }
//    }
//
//    public void deleteAll(){
//        userRepository.deleteAll();
//    }
}