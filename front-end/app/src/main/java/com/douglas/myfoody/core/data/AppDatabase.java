package com.douglas.myfoody.core.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.douglas.myfoody.core.DAO.RestaurantDAO;
import com.douglas.myfoody.core.DAO.UserDAO;
import com.douglas.myfoody.core.models.Restaurant;
import com.douglas.myfoody.core.models.User;

@Database(entities = {User.class, Restaurant.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "mobiledb";

    public abstract RestaurantDAO restaurantDAO();
    public abstract UserDAO userDAO();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
//                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final UserDAO mDao;
        private final RestaurantDAO mRestaurantDao;

        PopulateDbAsync(AppDatabase db) {
            mDao = db.userDAO();
            mRestaurantDao = db.restaurantDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            mDao.deleteAll();
            mRestaurantDao.deleteAll();

            // Initial data here
            initUserData(mDao);
            initRestaurantData(mRestaurantDao);

            return null;
        }
    }

    private static void initUserData(UserDAO dao) {
        User admin = new User("admin@gmail.com");
        dao.add(admin);
    }

    private static void initRestaurantData(RestaurantDAO dao) {

        Restaurant rest1 = new Restaurant();
        rest1.setName("Big Star Sandwich Co");
        rest1.setAddress("664 Columbia St");
        rest1.setCategory("restaurant");
        rest1.setRating("5");

        Restaurant rest2 = new Restaurant();
        rest2.setName("Taqueria Playa Tropical");
        rest2.setAddress("334 6th Street");
        rest2.setCategory("restaurant");
        rest2.setRating("5");

        Restaurant rest3 = new Restaurant();
        rest3.setName("The Old Bavaria Haus Restaurant");
        rest3.setAddress("233 Sixth St");
        rest3.setCategory("restaurant");
        rest3.setRating("4");

        Restaurant rest4 = new Restaurant();
        rest4.setName("The Old Spaghetti Factory");
        rest4.setAddress("50 Eighth St");
        rest4.setCategory("restaurant");
        rest4.setRating("4.5");

        dao.add(rest1);
        dao.add(rest2);
        dao.add(rest3);
        dao.add(rest4);
    }
}
