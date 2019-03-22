package com.douglas.myfoody.screen.home;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.douglas.myfoody.R;
import com.douglas.myfoody.core.models.User;
import com.douglas.myfoody.core.utilities.UIHelper;
import com.douglas.myfoody.screen.viewmodel.UserViewModel;


public class ChangePasswordFragment extends Fragment {
    private static View view;

    private EditText currentPassword, newPassword, confirmPassword;

    private UserViewModel mUserViewModel;

    public ChangePasswordFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        //hide edit menu
        MenuItem item=menu.findItem(R.id.action_edit);
        item.setVisible(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.change_password_layout, container, false);
        initViews();
        mUserViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);


        return view;
    }

    // Initiate Views
    private void initViews() {
        currentPassword = view.findViewById(R.id.currentPassword);
        newPassword = view.findViewById(R.id.newPassword);
        confirmPassword = view.findViewById(R.id.confirmNewPassword);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_done:
                User currentUser = mUserViewModel.getUser().getValue();
                //check current password is match
                if(currentUser.getPassword().equals(currentPassword.getText().toString())){
                    //check New Password and Confirm Password are equal and not empty
                    if(newPassword.getText().toString().equals(confirmPassword.getText().toString())
                            && !(newPassword.getText().toString().isEmpty())){
                        currentUser.setPassword(newPassword.getText().toString());
                        if(mUserViewModel.updatePassword(currentUser))
                            Toast.makeText(getActivity(), "Password has successfully changed!", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getActivity(), "Password has not been changed.\nPlease try again!", Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(getActivity(), "New Password and Confirm Password are not matched!",
                                Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getActivity(), "Current Password is not matched!", Toast.LENGTH_SHORT).show();
                
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
