package com.douglas.myfoody.screen.place_order;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.douglas.myfoody.R;
import com.douglas.myfoody.screen.home.ExploreRestaurantFragment;

public class OrderActivity extends AppCompatActivity {
    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        fragmentManager = getSupportFragmentManager();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(R.string.place_order);
        fragmentManager.beginTransaction()
                .replace(R.id.placeOrderFrameContainer, new PlaceOrderFragment(),
                        "Place_Order_Fragment").commit();
    }
}
