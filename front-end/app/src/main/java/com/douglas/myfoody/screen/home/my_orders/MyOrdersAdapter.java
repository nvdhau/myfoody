package com.douglas.myfoody.screen.home.my_orders;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.douglas.myfoody.R;
import com.douglas.myfoody.core.models.Order;

import java.util.List;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.MyOrdersViewHolder> {

    class MyOrdersViewHolder extends RecyclerView.ViewHolder {
        private final TextView restaurantNameView, orderDateView, totalView;

        private MyOrdersViewHolder(View itemView) {
            super(itemView);
            // binds all view order_date
            restaurantNameView = itemView.findViewById(R.id.restaurant_name);
            orderDateView = itemView.findViewById(R.id.order_date);
            totalView = itemView.findViewById(R.id.total);
        }
    }

    private final LayoutInflater mInflater;
    private List<Order> mOrders;
    private Context context;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Order order = (Order) view.getTag();

            Bundle i = new Bundle();
            i.putParcelable("order", order);
            MyOrderDetailFragment fragment = new MyOrderDetailFragment();
            fragment.setArguments(i);

            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.homeFrameContainer, fragment,
                            "My_Order_Detail_Fragment").commit();
        }
    };

    public MyOrdersAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }


    @Override
    public MyOrdersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.my_order_list_content, parent, false);
        return new MyOrdersAdapter.MyOrdersViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyOrdersAdapter.MyOrdersViewHolder holder, int position) {
        if (mOrders != null) {
            Order current = mOrders.get(position);

            holder.restaurantNameView.setText(current.getRestaurantName());
            holder.orderDateView.setText("Date: " + current.getCreatedAt());
            holder.totalView.setText("Total: $" + current.getTotal());

            holder.itemView.setTag(mOrders.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);

        } else {
            // Covers the case of data not being ready yet.
            holder.restaurantNameView.setText("No orders yet!");
        }
    }

    public void setOrders(List<Order> orders) {
        mOrders = orders;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mOrders != null)
            return mOrders.size();
        else return 0;
    }

}
