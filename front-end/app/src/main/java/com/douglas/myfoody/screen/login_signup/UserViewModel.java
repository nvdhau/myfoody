package com.douglas.myfoody.screen.login_signup;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.douglas.myfoody.core.models.User;
import com.douglas.myfoody.core.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    public UserViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public void insert(User user) {
        userRepository.insert(user);
    }

    public LiveData<User> getUserByEmail(String email){
        return userRepository.getUserByEmail(email);
    }
}