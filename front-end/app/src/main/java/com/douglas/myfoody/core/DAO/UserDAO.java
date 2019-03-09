package com.douglas.myfoody.core.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.douglas.myfoody.core.models.User;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDAO extends BaseDAO<User> {
    @Override
    @Query("SELECT * FROM user")
    LiveData<List<User>> findAll();

    @Override
    @Query("SELECT * FROM user WHERE id = :id")
    LiveData<User> findById(int id);

    @Override
    @Insert(onConflict = REPLACE)
    void add(User data);

    @Override
    @Update(onConflict = REPLACE)
    void update(User data);

    @Override
    @Query("DELETE FROM user WHERE id = :id")
    void delete(int id);

    @Override
    @Query("DELETE FROM user")
    void deleteAll();

    @Query("SELECT * FROM user")
    boolean checkDB();

    @Query("SELECT * FROM user WHERE email = :email")
    LiveData<User> getUserByEmail(String email);
}
