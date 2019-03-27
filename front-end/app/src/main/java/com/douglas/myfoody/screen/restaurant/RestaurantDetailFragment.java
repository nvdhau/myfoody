package com.douglas.myfoody.screen.restaurant;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.douglas.myfoody.R;
import com.douglas.myfoody.core.models.MenuItem;
import com.douglas.myfoody.core.models.Restaurant;
import com.douglas.myfoody.core.utilities.JSONHelper;
import com.douglas.myfoody.screen.restaurant.dummy.DummyContent;
import com.douglas.myfoody.screen.viewmodel.RestaurantViewModel;

import java.util.ArrayList;

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

    private Restaurant mRestaurant;
    private ArrayList<MenuItem> mItems;

    private TextView mResCategory;
    private TextView mResAddress;
    private RatingBar mRating;
    private RecyclerView mItemsRecylerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RestaurantDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.restaurant_detail, container, false);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
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

                    mResAddress = rootView.findViewById(R.id.address);
                    mResAddress.setText(mRestaurant.getAddress());

                    mResCategory = rootView.findViewById(R.id.category);
                    mResCategory.setText(mRestaurant.getCategory());

                    mRating = rootView.findViewById(R.id.ratingRestaurant);
                    mRating.setRating(Float.parseFloat(mRestaurant.getRating()));

                    // get a list of menu items
                    mItemsRecylerView = rootView.findViewById(R.id.restaurant_list_menu);
                    final RestaurantMenuAdapter adapter = new RestaurantMenuAdapter(getContext());
                    mItemsRecylerView.setAdapter(adapter);

                    mItems = new ArrayList<>();
                    mItems = new ArrayList<>(JSONHelper.getMenuItemListsFromJSON(restaurant.getMenu()));
                    adapter.setMenuRestaurant(mItems);
                }
            });
        }

        return rootView;
    }

    public boolean itemAddedToOrder() {
        for(int i=0; i<mItems.size(); i++) {
            if(mItems.get(i).getQuantity() > 0) {
                return true;
            }
        }
        return false;
    }

    public void addExtraToIntent(Intent intent) {
        intent.putExtra("restaurant", mRestaurant);
        intent.putExtra("items", mItems);
    }
}
