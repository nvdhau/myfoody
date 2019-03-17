package com.douglas.myfoody.core.database;

import android.provider.BaseColumns;

public class DBSchema {
    //Table user schema
    public static final class UserTable {
        public static final String NAME = "user";

        public static final class Cols implements BaseColumns {
            public static final String EMAIL = "email";
            public static final String PASSWORD = "password";
            public static final String LAST_NAME = "last_name";
            public static final String FIRST_NAME = "first_name";
            public static final String DOB = "dob";
            public static final String PHONE = "phone";
            public static final String ORDER_COUNT = "order_count";
            public static final String CREATE_AT = "create_at";
            public static final String LOGGED_IN = "logged_in";
        }
    }

    //Table order_history schema
    public static final class OrderHistoryTable {
        public static final String NAME = "order_history";

        public static final class Cols implements BaseColumns {
            public static final String ID = "id";
            public static final String RESTAURANT_NAME = "restaurant_name";
            public static final String RESTAURANT_ADDRESS = "restaurant_address";
            public static final String ITEMS = "items";
            public static final String SUB_TOTAL = "sub_total";
            public static final String TAX = "tax";
            public static final String DELIVERY_FEE = "delivery_fee";
            public static final String DELIVERY_ADDRESS= "delivery_address";
            public static final String TOTAL = "total";
            public static final String SPECIAL_INSTRUCTION = "special_instruction";
            public static final String CREATE_AT = "create_at";
        }
    }

    //Remote databases
    //Table restaurant
    public static final class ReataurantTable {
        public static final String NAME = "restaurant";

        public static final class Cols implements BaseColumns {
            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String ADDRESS = "address";
            public static final String CATEGORY = "category";
            public static final String RATING = "rating";
            public static final String MENU = "menu";
            public static final String MENU_HASH = "menu_hash";
        }
    }
}