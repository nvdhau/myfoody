package com.douglas.myfoody.screen.home;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.douglas.myfoody.R;
import com.douglas.myfoody.core.view_model.UserViewModel;
import com.douglas.myfoody.utilities.UIHelper;

import es.dmoral.toasty.Toasty;


public class UserInfoFragment extends Fragment {
    private View view;

    private EditText fullName, email, mobileNumber, location;
    private EditText[] editTextInputs;

    private UserViewModel mUserViewModel;

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
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        initViews();
//        mUserViewModel.mUser.setValue(HomeActivity.currentUser);
//        mUserViewModel.mUser.observe(this, new Observer<User>() {
//            @Override
//            public void onChanged(@Nullable User user) {
//                if(user != null){
//                    fullName.setText(user.getFullName());
//                    email.setText(user.getEmail());
//                    mobileNumber.setText(user.getMobileNumber());
//                    location.setText(user.getAddress());
//                }
//            }
//        });
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

        //fill data
        fullName.setText(HomeActivity.currentUser.getFullName());
        email.setText(HomeActivity.currentUser.getEmail());
        mobileNumber.setText(HomeActivity.currentUser.getMobileNumber());
        location.setText(HomeActivity.currentUser.getAddress());

//        Toast.makeText(getActivity(), "onCreateUserInFo", Toast.LENGTH_SHORT).show();
    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit:
                Toasty.success(getActivity(), "Edit!", Toast.LENGTH_SHORT, true).show();
                for (EditText editText : editTextInputs)
                    UIHelper.enableEditText(editText);

                fullName.requestFocus();
                return true;
            case R.id.action_done:
                Toasty.success(getContext(), "Done!", Toast.LENGTH_SHORT, true).show();
                HomeActivity.currentUser.setFullName(fullName.getText().toString());
                HomeActivity.currentUser.setEmail(email.getText().toString());
                HomeActivity.currentUser.setMobileNumber(mobileNumber.getText().toString());
                HomeActivity.currentUser.setAddress(location.getText().toString());
                mUserViewModel.updateUser(HomeActivity.currentUser);
                for (EditText editText : editTextInputs)
                    UIHelper.disableEditText(editText);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
