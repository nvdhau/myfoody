package com.douglas.myfoody.screen.restaurant;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.douglas.myfoody.R;
import com.douglas.myfoody.core.models.MenuItem;
import com.douglas.myfoody.core.models.Restaurant;
import com.douglas.myfoody.core.utilities.JSONHelper;
import com.douglas.myfoody.screen.restaurant.dummy.DummyContent;
import com.douglas.myfoody.screen.viewmodel.RestaurantViewModel;
import com.douglas.myfoody.screen.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a single Restaurant detail screen.
 * This fragment is either contained in a {@link RestaurantListActivity}
 * in two-pane mode (on tablets) or a {@link RestaurantDetailActivity}
 * on handsets.
 */
public class RestaurantDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    private RestaurantViewModel mRestaurantViewModel;

    private Restaurant mRestaurant = new Restaurant();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RestaurantDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
//            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            int restaurantID =  Integer.parseInt(getArguments().getString(ARG_ITEM_ID));
            mRestaurantViewModel = ViewModelProviders.of(this).get(RestaurantViewModel.class);
            mRestaurantViewModel.findRestaurantByID(restaurantID).observe(this, new Observer<Restaurant>() {
                @Override
                public void onChanged(@Nullable Restaurant restaurant) {
                    mRestaurant = restaurant;
                    Activity activity = getActivity();
                    CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
                    if (appBarLayout != null) {
                        appBarLayout.setTitle(mRestaurant.getName());
                        int imageResource = getActivity().getResources().getIdentifier(mRestaurant.getImage(),
                                "drawable", getActivity().getPackageName());
                        ImageView image = appBarLayout.findViewById(R.id.imageView);
                        image.setImageResource(imageResource);
                    }

                    // get a list of menu items
                    List<MenuItem> menuItems = new ArrayList<>();
                    menuItems = JSONHelper.getMenuItemListsFromJSON(restaurant.getMenu());
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.restaurant_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
//            ((TextView) rootView.findViewById(R.id.restaurant_detail)).setText(mItem.details);
        }

        return rootView;
    }
}
