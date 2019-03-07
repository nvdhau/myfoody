package com.douglas.myfoody.screen.login_signup;


import com.douglas.myfoody.R;
import com.douglas.myfoody.screen.main.MainActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;

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
                // Call checkValidation method
                if (checkValidation()) {
                    // TODO: save data and redirect to login
                    new MainActivity().loginFragment();
                }
                break;
            case R.id.already_user:
                // Replace login fragment
                new MainActivity().loginFragment();
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
        if (getFullName.equals("") || getFullName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getMobileNumber.equals("") || getMobileNumber.length() == 0
                || getLocation.equals("") || getLocation.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0) {

            new MyToast().showToast(getActivity(), view,
                    "Please fill all required fields.");
            return false;
        }

        return true;
    }
}
