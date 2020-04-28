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

            for (int i = 0; i < restaurantsJSON.length(); i++) {
                JSONObject restaurantJSON = restaurantsJSON.getJSONObject(i);

                Restaurant restaurant = new Restaurant();
                restaurant.setName(restaurantJSON.getString("name"));
                restaurant.setAddress(restaurantJSON.getString("address"));
                restaurant.setRating(restaurantJSON.getString("rating") + "");
                restaurant.setCategory(restaurantJSON.getString("category"));
                restaurant.setImage(restaurantJSON.getString("image"));
                restaurant.setMenu(restaurantJSON.getString("menus"));

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

            for (int i = 0; i < menusJSON.length(); i++) {
                JSONObject menuItemJSON = menusJSON.getJSONObject(i);

                MenuItem menuItem = new MenuItem(menuItemJSON.getString("item_name"),
                        menuItemJSON.getString("description"), menuItemJSON.getDouble("price"));

                menus.add(menuItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return menus;
    }

    public static String parseMenuItemsListToJSON(List<MenuItem> menuItems) {
        JSONArray items = new JSONArray();
        for(int i=0; i<menuItems.size(); i++) {
            MenuItem item = menuItems.get(i);
            JSONObject itemJSON = new JSONObject();
            try {
                itemJSON.put("item_name", item.getItemName());
                itemJSON.put("description", item.getDescription());
                itemJSON.put("price", item.getPrice());
                itemJSON.put("quantity", item.getQuantity());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            items.put(itemJSON);
        }
        return items.toString();
    }

    public static List<MenuItem> getItemListFromOrderDetail(String itemJSON) {
        List<MenuItem> items = new ArrayList<>();

        try {
            JSONArray array = new JSONArray(itemJSON);
            for (int i = 0; i < array.length(); i++) {
                JSONObject menuItemJSON = array.getJSONObject(i);

                MenuItem menuItem = new MenuItem(menuItemJSON.getString("item_name"),
                        menuItemJSON.getString("description"), menuItemJSON.getDouble("price"));
                menuItem.setQuantity(menuItemJSON.getInt("quantity"));
                items.add(menuItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return items;
    }
}
