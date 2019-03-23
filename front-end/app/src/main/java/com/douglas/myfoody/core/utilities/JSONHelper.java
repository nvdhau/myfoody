package com.douglas.myfoody.core.utilities;

import com.douglas.myfoody.core.models.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONHelper {

    //parse jsonString to list of restaurants
    public static List<Restaurant> getRestaurantListsFromJSON(String jsonString){
        List<Restaurant> restaurants = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(jsonString);
            JSONArray restaurantsJSON = obj.getJSONArray("restaurants");

//            System.out.println("JSON: Number of restaurants - " + restaurantsJSON.length());

            for (int i = 0; i < restaurantsJSON.length(); i++) {
                JSONObject restaurantJSON = restaurantsJSON.getJSONObject(i);

                Restaurant restaurant = new Restaurant();
                restaurant.setName(restaurantJSON.getString("name"));
                restaurant.setAddress(restaurantJSON.getString("address"));
                restaurant.setRating(restaurantJSON.getString("rating") + "");
                restaurant.setCategory(restaurantJSON.getString("category"));
                restaurant.setMenu(restaurantJSON.getString("menus"));

//                System.out.println("JSON: " + restaurant.getName());

                restaurants.add(restaurant);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return restaurants;
    }
}
