package com.douglas.myfoody.core.models;


import android.arch.lifecycle.LiveData;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

public class User extends LiveData<User> implements Parcelable {

    private int ID;
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private String address;
    private String orderCount;
    private String createAt;
    private String isLoggedIn;

    public User() {

    }

    // for passing object to another Activity
    public User(Parcel in) {
        ID = in.readInt();
        email = in.readString();
        password = in.readString();
        fullName = in.readString();
        phone = in.readString();
        address = in.readString();
        orderCount = in.readString();
        createAt = in.readString();
        isLoggedIn = in.readString();
    }

    // DEFINE TABLE USER
    public static final class USER_TABLE {
        public static final String TB_NAME = "user";

        public static final class TB_COL implements BaseColumns {
            public static final String ID = "id";
            public static final String EMAIL = "email";
            public static final String PASSWORD = "password";
            public static final String FULL_NAME = "full_name";
            public static final String PHONE = "phone";
            public static final String ADDRESS = "address";
            public static final String ORDER_COUNT= "order_count";
            public static final String CREATED_AT = "create_at";
            public static final String IS_LOGGED_IN = "is_logged_in";
        }
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(String loggedIn) {
        isLoggedIn = loggedIn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(fullName);
        dest.writeString(phone);
        dest.writeString(address);
        dest.writeString(orderCount);
        dest.writeString(createAt);
        dest.writeString(isLoggedIn);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}