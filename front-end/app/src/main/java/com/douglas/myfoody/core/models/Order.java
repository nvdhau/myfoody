package com.douglas.myfoody.core.models;

import android.arch.lifecycle.LiveData;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

public class Order extends LiveData<Order> implements Parcelable {
    private int ID;
    private String userEmail;
    private int restaurantId;
    private String items;
    private String deliveryAddress;
    private String specialInstruction;
    private double subTotal;
    private double deliveryFee;
    private double discount;
    private double tax;
    private double total;
    private String createdAt;

    private String restaurantName;// runtime attribute (not persist in database)

    public Order() {

    }
    // for passing object to another Activity
    public Order(Parcel in) {
        ID = in.readInt();
        userEmail = in.readString();
        restaurantId = in.readInt();
        items = in.readString();
        deliveryAddress = in.readString();
        specialInstruction = in.readString();
        subTotal = in.readDouble();
        deliveryFee = in.readDouble();
        discount = in.readDouble();
        tax = in.readDouble();
        total = in.readDouble();
        createdAt = in.readString();
    }

    // DEFINE TABLE ORDER
    public static final class ORDER_TABLE {
        public static final String TB_NAME = "orderdetail";

        public static final class TB_COL implements BaseColumns {
            public static final String ID = "id";
            public static final String USER_EMAIL = "email";
            public static final String RESTAURANT_ID = "restaurantId";
            public static final String ITEMS_JSON = "items";
            public static final String DELIVERY_ADDRESS = "deliveryAddress";
            public static final String SPECIAL_INSTRUCTION = "specialInstruction";
            public static final String SUB_TOTAL = "subTotal";
            public static final String DELIVERY_FEE = "deliveryFee";
            public static final String DISCOUNT = "discount";
            public static final String TAX = "tax";
            public static final String TOTAL = "total";
            public static final String CREATED_AT = "createdAt";
        }
    }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getSpecialInstruction() {
        return specialInstruction;
    }

    public void setSpecialInstruction(String specialInstruction) {
        this.specialInstruction = specialInstruction;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(userEmail);
        dest.writeInt(restaurantId);
        dest.writeString(items);
        dest.writeString(deliveryAddress);
        dest.writeString(specialInstruction);
        dest.writeDouble(subTotal);
        dest.writeDouble(deliveryFee);
        dest.writeDouble(discount);
        dest.writeDouble(tax);
        dest.writeDouble(total);
        dest.writeString(createdAt);
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
