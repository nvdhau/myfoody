package com.douglas.myfoody.screen.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.widget.Toast;
import com.douglas.myfoody.core.models.User;
import com.douglas.myfoody.core.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private MutableLiveData<User> mUser;

    public UserViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);

        if (mUser == null) {
            mUser = new MutableLiveData<>();
        }
    }

    public LiveData<User> getUserByEmail(String email) {
        mUser.setValue(userRepository.getUserByEmail(email));
        return mUser;
    }

    public LiveData<User> getUser() {
        System.out.println(mUser.getValue().getEmail());

        return mUser;
    }

    public void setUser(User user) {
        mUser.setValue(user);
    }

    public void insert(User user) {
        if (!userRepository.add(user)) {
            Toast.makeText(getApplication(), "Cannot insert user!", Toast.LENGTH_SHORT).show();
        } else {
            setUser(user);
        }
    }
}