package com.douglas.myfoody.screen.main.login_signup;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.douglas.myfoody.R;
import com.douglas.myfoody.core.models.User;
import com.douglas.myfoody.screen.main.MainActivity;
import com.douglas.myfoody.screen.viewmodel.UserViewModel;


public class SignUpFragment extends Fragment implements OnClickListener {
    private static View view;
    private static EditText fullName, email,
            mobileNumber, location,
            password, confirmPassword;
    private static TextView login;
    private static Button signUpBtn;

    public SignUpFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup_layout, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initialize all views
    private void initViews() {
        fullName = view.findViewById(R.id.fullName);
        email = view.findViewById(R.id.userEmailId);
        mobileNumber = view.findViewById(R.id.mobileNumber);
        location =  view.findViewById(R.id.location);
        password = view.findViewById(R.id.password);
        confirmPassword = view.findViewById(R.id.confirmPassword);
        signUpBtn = view.findViewById(R.id.signUpBtn);
        login = view.findViewById(R.id.already_user);
    }

    // Set Listeners
    private void setListeners() {
        signUpBtn.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpBtn:
                //Hide the soft keyboard
                InputMethodManager imm = (InputMethodManager)getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                // Call checkValidation method
                if (checkValidation()) {
                    UserViewModel mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
                    User user = new User();
                    user.setEmail(email.getText().toString());
                    user.setPassword(password.getText().toString());
                    user.setFullName(fullName.getText().toString());
                    user.setPhone(mobileNumber.getText().toString());
                    user.setAddress(location.getText().toString());
                    if(mUserViewModel.insert(user))//insert success
                        MainActivity.loginFragment();// navigate back to login screen
                }
                break;
            case R.id.already_user:
                // Replace login fragment
                MainActivity.loginFragment();
                break;
        }
    }

    private boolean checkValidation() {
        String getFullName = fullName.getText().toString();
        String getEmailId = email.getText().toString();
        String getMobileNumber = mobileNumber.getText().toString();
        String getLocation = location.getText().toString();
        String getPassword = password.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();

        // check not empty
        if (getFullName.equals("") || getEmailId.equals("")
                || getMobileNumber.equals("") || getLocation.equals("")
                || getPassword.equals("") || getConfirmPassword.equals("")) {

            new MyToast().showToast(getActivity(), view,
                    "Please fill all required fields.");
            return false;
        } else if (getPassword.equals(getConfirmPassword) == false) {
            new MyToast().showToast(getActivity(), view,
                    "Password and Confirm Password are not the same.");
            return false;
        }

        return true;
    }
}
