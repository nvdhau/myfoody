package com.douglas.myfoody.core.utilities;

import com.douglas.myfoody.core.models.MenuItem;
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

    //parse jsonString to list of menu items
    public static List<MenuItem> getMenuItemListsFromJSON(String jsonString){
        List<MenuItem> menus = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(jsonString);
            JSONArray menusJSON = obj.getJSONArray("menus");

            System.out.println("JSON: Number of menu items - " + menusJSON.length());

            for (int i = 0; i < menusJSON.length(); i++) {
                JSONObject menuItemJSON = menusJSON.getJSONObject(i);

                MenuItem menuItem = new MenuItem(menuItemJSON.getString("item_name"),
                        menuItemJSON.getString("description"), menuItemJSON.getDouble("price"));

                System.out.println("JSON: " + menuItem.getItemName());
                System.out.println("JSON: " + menuItem.getDescription());
                System.out.println("JSON: " + menuItem.getPrice());

                menus.add(menuItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return menus;
    }

}
