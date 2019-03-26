package com.douglas.myfoody.core.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MenuItem implements Parcelable {
    private String itemName, description;
    private double price;
    private int quantity;

    public MenuItem(String itemName, String description, double price) {
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.quantity = 0;
    }

    protected MenuItem(Parcel in) {
        itemName = in.readString();
        description = in.readString();
        price = in.readDouble();
        quantity = in.readInt();
    }

    public static final Creator<MenuItem> CREATOR = new Creator<MenuItem>() {
        @Override
        public MenuItem createFromParcel(Parcel in) {
            return new MenuItem(in);
        }

        @Override
        public MenuItem[] newArray(int size) {
            return new MenuItem[size];
        }
    };

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemName);
        dest.writeString(description);
        dest.writeDouble(price);
        dest.writeInt(quantity);
    }
}
