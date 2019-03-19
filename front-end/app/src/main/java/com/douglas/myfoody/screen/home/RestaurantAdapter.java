package com.douglas.myfoody.screen.home;

import com.douglas.myfoody.R;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.douglas.myfoody.core.models.Restaurant;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {


    class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private final TextView restaurantTitleView;
        private final TextView restaurantAddressView;

        private RestaurantViewHolder(View itemView) {
            super(itemView);
            restaurantTitleView = itemView.findViewById(R.id.title_restaurant);
            restaurantAddressView = itemView.findViewById(R.id.address_restaurant);

        }
    }

    private final LayoutInflater mInflater;
    private List<Restaurant> mRestaurants;

    public RestaurantAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.restaurant_list_row, parent, false);
        return new RestaurantViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder holder, int position) {
        if (mRestaurants != null) {
            Restaurant current = mRestaurants.get(position);
            holder.restaurantTitleView.setText(current.getName());
            holder.restaurantAddressView.setText(current.getAddress());
        } else {
            // Covers the case of data not being ready yet.
            holder.restaurantTitleView.setText("No Item");
            holder.restaurantAddressView.setText("No Address");
        }
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        mRestaurants = restaurants;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mRestaurants != null)
            return mRestaurants.size();
        else return 0;
    }

}