package com.douglas.myfoody.core.models;


import android.arch.lifecycle.LiveData;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

public class Restaurant extends LiveData<User> implements Parcelable {

    private int ID;
    private String name;
    private String address;
    private String category;
    private String rating;
    private String menu;

    public Restaurant() {

    }

    public Restaurant(Parcel in) {
        ID = in.readInt();
        name = in.readString();
        address = in.readString();
        category = in.readString();
        rating = in.readString();
        menu = in.readString();
    }


    public static final class RESTAURANT_TABLE {
        public static final String TB_NAME = "restaurant";

        public static final class TB_COL implements BaseColumns {
            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String ADDRESS = "address";
            public static final String CATEGORY = "category";
            public static final String RATING = "rating";
            public static final String MENU = "menu";
        }
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(category);
        dest.writeString(rating);
        dest.writeString(menu);
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };
}
