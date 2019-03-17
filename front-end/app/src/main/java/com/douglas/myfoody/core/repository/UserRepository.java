package com.douglas.myfoody.core.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.douglas.myfoody.core.DAO.UserDAO;
import com.douglas.myfoody.core.models.User;
import java.util.List;

public class UserRepository implements BaseRepository<User> {

    private UserDAO myUserDAO;


    public UserRepository(Application application) {
        myUserDAO = new UserDAO(application);
    }

    @Override
    public LiveData<List<User>> findAll() {
        return null;
    }

    @Override
    public LiveData<User> findById(int id) {
        return null;
    }

    @Override
    public boolean add(User data) {
        return myUserDAO.insert(data);
    }

    @Override
    public boolean update(User data) {
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

    public User getUserByEmail(String email) {
        //mUser.postValue(myUserDAO.findByEmail(email));
        return myUserDAO.findByEmail(email);
    }
}
