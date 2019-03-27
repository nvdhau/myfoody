package com.douglas.myfoody.screen.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.douglas.myfoody.R;
import com.douglas.myfoody.screen.restaurant.RestaurantListActivity;
import com.douglas.myfoody.screen.viewmodel.UserViewModel;

public class ExploreRestaurantFragment extends Fragment implements OnClickListener {
    private static View view;
    private static Button exploreBtn;
    private static FragmentManager fragmentManager;
    private UserViewModel mUserViewModel;

    public ExploreRestaurantFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.explore_restaurant_layout, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        exploreBtn = view.findViewById(R.id.exploreBtn);
    }

    // Set Listeners
    private void setListeners() {
        exploreBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exploreBtn:
                TextView searchLocation = view.findViewById(R.id.search_restaurant);
                Intent intent = new Intent(getActivity(), RestaurantListActivity.class);
                intent.putExtra("search_location", searchLocation.getText().toString());
                startActivity(intent);
                break;
        }

    }

}
