package com.douglas.myfoody.core.repository;

import android.app.Application;
import com.douglas.myfoody.core.DAO.RestaurantDAO;
import com.douglas.myfoody.core.models.Restaurant;
import java.util.List;

public class RestaurantRepository implements BaseRepository<Restaurant> {

    private RestaurantDAO restaurantDAO;

    public RestaurantRepository(Application application) {
        restaurantDAO = new RestaurantDAO(application);
    }

    @Override
    public List<Restaurant> findAll() {
        return restaurantDAO.findAll();
    }

    public List<Restaurant> findAllByLocation(String location) {
        return restaurantDAO.findAllByLocation(location);
    }

    @Override
    public Restaurant findById(int id) {
        return restaurantDAO.findById(id);
    }

    @Override
    public boolean add(Restaurant data) {
        return true;
    }

    @Override
    public boolean update(Restaurant data) {
        return true;
    }

    @Override
    public boolean delete(int id) {
        return true;
    }

    @Override
    public boolean deleteAll() {
        return true;
    }
}
