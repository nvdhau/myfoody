package com.douglas.myfoody.core.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;
import com.douglas.myfoody.core.models.Restaurant;
import java.util.List;

@Dao
public interface RestaurantDAO extends BaseDAO<Restaurant> {
    @Override
    @Query("SELECT * FROM restaurant")
    LiveData<List<Restaurant>> findAll();

    @Override
    @Query("SELECT * FROM restaurant WHERE id = :id")
    LiveData<Restaurant> findById(int id);

    @Override
    @Insert(onConflict = REPLACE)
    void add(Restaurant data);

    @Override
    @Update(onConflict = REPLACE)
    void update(Restaurant data);

    @Override
    @Query("DELETE FROM restaurant WHERE id = :id")
    void delete(int id);

    @Override
    @Query("DELETE FROM restaurant")
    void deleteAll();
}
