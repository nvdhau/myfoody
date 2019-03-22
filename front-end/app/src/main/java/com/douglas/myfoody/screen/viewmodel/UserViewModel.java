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

    public boolean insert(User user) {
        if (!userRepository.add(user)) {
            Toast.makeText(getApplication(), "Cannot insert user!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            setUser(user);
            return true;
        }
    }

    public void deleteAll(){
        userRepository.deleteAll();
    }

    public boolean updateUser(User user){
        //get user by input email
        User userExist = userRepository.getUserByEmail(user.getEmail());

        //if the same user or new email is valid
        if(userExist == null || user.getID() == userExist.getID()){
            //update user
            if(!userRepository.update(user)){
                Toast.makeText(getApplication(), "Cannot update user!", Toast.LENGTH_SHORT).show();
                return false;
            }else{
                setUser(user);
                Toast.makeText(getApplication(), "Update user successfully!", Toast.LENGTH_SHORT).show();
                return true;
            }
        }else{
            Toast.makeText(getApplication(), "Cannot update user. Email is used!", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public boolean updatePassword(User user){
        return userRepository.updatePassword(user);
    }

}