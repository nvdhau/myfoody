package com.douglas.myfoody.screen.restaurant;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.douglas.myfoody.R;
import com.douglas.myfoody.core.models.MenuItem;

import java.util.List;

public class RestaurantMenuAdapter extends RecyclerView.Adapter<RestaurantMenuAdapter.RestaurantMenuViewHolder> {


    class RestaurantMenuViewHolder extends RecyclerView.ViewHolder {
        private final TextView menuName;
        private final TextView menuDesc;
        private final TextView itemQuantity;
        private final Button addItem;
        private final Button minusItem;

        private RestaurantMenuViewHolder(View itemView) {
            super(itemView);
            menuName = itemView.findViewById(R.id.menu_name);
            menuDesc = itemView.findViewById(R.id.menu_description);
            itemQuantity = itemView.findViewById(R.id.textViewItemQuantity);
            addItem = itemView.findViewById(R.id.addItem);
            minusItem = itemView.findViewById(R.id.minusItem);
        }
    }

    private final LayoutInflater mInflater;
    private List<MenuItem> mMenuRestaurants;
    private Context context;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MenuItem item = mMenuRestaurants.get((int) view.getTag());
            if(view.getId() == R.id.addItem) {
                item.setQuantity(item.getQuantity() + 1);
            } else if(view.getId() == R.id.minusItem) {
                if(item.getQuantity() > 0)
                    item.setQuantity(item.getQuantity() - 1);
            }

            notifyDataSetChanged();
        }
    };

    public RestaurantMenuAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }


    @Override
    public RestaurantMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.restaurant_list_menu_content, parent, false);
        return new RestaurantMenuViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RestaurantMenuViewHolder holder, int position) {
        if (mMenuRestaurants != null) {
            MenuItem current = mMenuRestaurants.get(position);
            holder.menuName.setText(current.getItemName() + " ($" +current.getPrice() + ")");
            holder.menuDesc.setText(current.getDescription());
            holder.itemQuantity.setText(""+current.getQuantity());
            holder.addItem.setOnClickListener(mOnClickListener);
            holder.minusItem.setOnClickListener(mOnClickListener);
            holder.addItem.setTag(position);
            holder.minusItem.setTag(position);
        } else {
            // Covers the case of data not being ready yet.
            holder.menuName.setText("No Item");
            holder.menuDesc.setText("No Item");
        }
    }

    public void setMenuRestaurant(List<MenuItem> menuItems) {
        mMenuRestaurants = menuItems;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mMenuRestaurants != null)
            return mMenuRestaurants.size();
        else return 0;
    }

}