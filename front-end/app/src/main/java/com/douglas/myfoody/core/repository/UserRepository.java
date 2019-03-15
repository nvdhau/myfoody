package com.douglas.myfoody.core.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.douglas.myfoody.core.DAO.UserDAO;
import com.douglas.myfoody.core.data.AppDatabase;
import com.douglas.myfoody.core.models.User;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserRepository {
    private static UserRepository instance;

    private UserDAO userDAO;
    private LiveData<List<User>> allUsers;
    private Executor executor = Executors.newSingleThreadExecutor();

    public static UserRepository getInstance(Application application) {
        if (instance == null) {
            instance = new UserRepository(application);
        }
        return instance;
    }

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDAO = db.userDAO();
        allUsers = userDAO.findAll();//get list of all users
    }

    public void insert(User user) {
        new InsertAsyncTask(userDAO).execute(user);
    }

    private static class InsertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDAO mAsyncTaskDao;

        InsertAsyncTask(UserDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.add(params[0]);
            return null;
        }
    }

    public LiveData<User> getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    public User getUser(String email) {
        return userDAO.getUser(email);
    }

}
