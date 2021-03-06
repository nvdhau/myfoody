package com.douglas.myfoody.screen.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.douglas.myfoody.R;
import com.douglas.myfoody.screen.main.login_signup.MyToast;
import com.douglas.myfoody.screen.place_order.OrderActivity;

/**
 * An activity representing a single Restaurant detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RestaurantListActivity}.
 */
public class RestaurantDetailActivity extends AppCompatActivity {

    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        Intent intent = getIntent();
        location = intent.getStringExtra("search_location");

        final RestaurantDetailFragment fragment = new RestaurantDetailFragment();

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(RestaurantDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(RestaurantDetailFragment.ARG_ITEM_ID));

            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.restaurant_detail_container, fragment)
                    .commit();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fragment.itemAddedToOrder()) {
                    Intent intent = new Intent(RestaurantDetailActivity.this, OrderActivity.class);
                    fragment.addExtraToIntent(intent);
                    startActivity(intent);
                } else {
                    // display Toast message
                    new MyToast().showToast(RestaurantDetailActivity.this, view,
                            "There are no items in your cart!");
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, RestaurantListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void backToHome() {
        // pass Order Items into next Activity
        startActivity(new Intent(this, OrderActivity.class));
    }
}
