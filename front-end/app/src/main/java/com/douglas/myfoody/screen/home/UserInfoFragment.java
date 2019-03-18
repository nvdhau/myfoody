package com.douglas.myfoody.screen.home;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

import com.douglas.myfoody.R;
import com.douglas.myfoody.core.models.User;
import com.douglas.myfoody.screen.viewmodel.UserViewModel;


public class UserInfoFragment extends Fragment implements OnClickListener {
    private static View view;

    private static EditText email, password;
    private static FragmentManager fragmentManager;
    private UserViewModel mUserViewModel;

    public UserInfoFragment() {

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
            }
        });
        setListeners();
        return view;
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        email = view.findViewById(R.id.userEmailId);
        // add more field here ???
    }

    // Set Listeners
    private void setListeners() {
    }

    @Override
    public void onClick(View v) {

    }

}
