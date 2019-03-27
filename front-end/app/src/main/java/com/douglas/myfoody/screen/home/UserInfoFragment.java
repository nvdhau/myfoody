package com.douglas.myfoody.screen.home;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.douglas.myfoody.R;
import com.douglas.myfoody.core.models.User;
import com.douglas.myfoody.core.utilities.UIHelper;
import com.douglas.myfoody.screen.viewmodel.UserViewModel;


public class UserInfoFragment extends Fragment {
    private static View view;

    private EditText fullName, email, mobileNumber, location;
    private EditText[] editTextInputs;

    private UserViewModel mUserViewModel;

    private boolean isEdited =  false;

    public UserInfoFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_info_layout, container, false);
        initViews();
        mUserViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);

        mUserViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                System.out.println(user.getEmail());
                email.setText(user.getEmail());
                fullName.setText(user.getFullName());
                mobileNumber.setText(user.getPhone());
                location.setText(user.getAddress());
            }
        });

        return view;
    }

    // Initiate Views
    private void initViews() {
        fullName = view.findViewById(R.id.fullName);
        email = view.findViewById(R.id.userEmailId);
        mobileNumber = view.findViewById(R.id.mobileNumber);
        location = view.findViewById(R.id.location);

        editTextInputs = new EditText[] {fullName, email, mobileNumber, location};

        //disable edit texts
        for (EditText editText : editTextInputs)
            UIHelper.disableEditText(editText);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit:
                for (EditText editText : editTextInputs)
                    UIHelper.enableEditText(editText);

                fullName.requestFocus();
                isEdited = true;
                return true;
            case R.id.action_done:
                if(isEdited){
                    User currentUser = mUserViewModel.getUser().getValue();

                    currentUser.setFullName(fullName.getText().toString());
                    currentUser.setEmail(email.getText().toString());
                    currentUser.setPhone(mobileNumber.getText().toString());
                    currentUser.setAddress(location.getText().toString());

                    if(mUserViewModel.updateUser(currentUser)){//update success
                        mUserViewModel.setUser(currentUser);
                    }

                    for (EditText editText : editTextInputs)
                        UIHelper.disableEditText(editText);

                    isEdited = false;//turn of edit mode
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
