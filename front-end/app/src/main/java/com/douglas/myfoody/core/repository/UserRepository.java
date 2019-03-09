package com.douglas.myfoody.core.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.douglas.myfoody.core.DAO.UserDAO;
import com.douglas.myfoody.core.data.AppDatabase;
import com.douglas.myfoody.core.models.User;

public class UserRepository {

    private UserDAO userDAO;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDAO = db.userDAO();
        userDAO.findAll();
    }

    public void insert(User user) {
        new insertAsyncTask(userDAO).execute(user);
    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDAO mAsyncTaskDao;

        insertAsyncTask(UserDAO dao) {
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
}
