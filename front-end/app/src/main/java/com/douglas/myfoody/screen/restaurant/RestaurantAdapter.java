package com.douglas.myfoody.screen.restaurant;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.douglas.myfoody.R;
import com.douglas.myfoody.core.models.Restaurant;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {


    class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private final TextView restaurantTitleView;
        private final TextView restaurantAddressView;
        private final RatingBar ratingBar;
        private final ImageView imageView;

        private RestaurantViewHolder(View itemView) {
            super(itemView);
            restaurantTitleView = itemView.findViewById(R.id.title_restaurant);
            restaurantAddressView = itemView.findViewById(R.id.address_restaurant);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }

    private final LayoutInflater mInflater;
    private List<Restaurant> mRestaurants;
    private Context context;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Restaurant restaurant = (Restaurant) view.getTag();
        Context context = view.getContext();
        Intent intent = new Intent(context, RestaurantDetailActivity.class);
        intent.putExtra(RestaurantDetailFragment.ARG_ITEM_ID, String.valueOf(restaurant.getID()));
        context.startActivity(intent);
    }
};

    public RestaurantAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }


    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.restaurant_list_content, parent, false);
        return new RestaurantViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder holder, int position) {
        if (mRestaurants != null) {
            Restaurant current = mRestaurants.get(position);
            holder.restaurantTitleView.setText(current.getName());
            holder.restaurantAddressView.setText(current.getAddress());
            holder.ratingBar.setRating(Float.parseFloat(current.getRating()));
            String imageName = current.getImage();
            int imageResource = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
            holder.imageView.setImageResource(imageResource);
            holder.itemView.setTag(mRestaurants.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);


        } else {
            // Covers the case of data not being ready yet.
            holder.restaurantTitleView.setText("No Item");
            holder.restaurantAddressView.setText("No Address");
            holder.ratingBar.setRating(0.0f);
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