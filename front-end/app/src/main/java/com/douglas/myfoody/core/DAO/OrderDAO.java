package com.douglas.myfoody.core.DAO;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.douglas.myfoody.core.database.AppDatabase;
import com.douglas.myfoody.core.models.Order;
import com.douglas.myfoody.core.models.User;

import java.util.ArrayList;
import java.util.List;

public class OrderDAO implements BaseDAO<Order> {

    private AppDatabase db;

    private String[] db_columns = {
            Order.ORDER_TABLE.TB_COL.ID, Order.ORDER_TABLE.TB_COL.USER_EMAIL,
            Order.ORDER_TABLE.TB_COL.RESTAURANT_ID, Order.ORDER_TABLE.TB_COL.ITEMS_JSON,
            Order.ORDER_TABLE.TB_COL.DELIVERY_ADDRESS, Order.ORDER_TABLE.TB_COL.SPECIAL_INSTRUCTION,
            Order.ORDER_TABLE.TB_COL.SUB_TOTAL, Order.ORDER_TABLE.TB_COL.DELIVERY_FEE, Order.ORDER_TABLE.TB_COL.DISCOUNT,
            Order.ORDER_TABLE.TB_COL.TAX, Order.ORDER_TABLE.TB_COL.TOTAL,
            Order.ORDER_TABLE.TB_COL.CREATED_AT
    };

    public OrderDAO(Application application) {
        db = AppDatabase.getDBInstance(application);
    }

    @Override
    public SQLiteDatabase getWriteDB() {
        return db.getWritableDatabase();
    }

    @Override
    public SQLiteDatabase getReadDB() {
        return db.getReadableDatabase();
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        try {
            Cursor cursor = getReadDB().query(Order.ORDER_TABLE.TB_NAME, db_columns,
                    null, null,
                    null, null, null);
            if (cursor != null) {
                while(cursor.moveToNext()) {
                    Order order = parseCursorToOrderObject(cursor);
                    orders.add(order);
                }
            }
        } catch (Exception ex) {
            // throw error message
            System.out.println(ex.getMessage());
        } finally {
            // Closing database connection
            getWriteDB().close();
        }

        return orders;
    }

    @Override
    public Order findById(int id) {
        Order order = null;
        try {
            Cursor cursor = getReadDB().query(Order.ORDER_TABLE.TB_NAME, db_columns,
                    Order.ORDER_TABLE.TB_COL.ID + " = '" + id + "'", null,
                    null, null, null);
            if (cursor != null) {
                while(cursor.moveToNext()) {
                    order = parseCursorToOrderObject(cursor);
                    break;
                }
            }
        } catch (Exception ex) {
            // throw error message
            System.out.println(ex.getMessage());
        } finally {
            // Closing database connection
            getWriteDB().close();
        }

        return order;
    }

    public List<Order> findByUserEmail(String email) {
        List<Order> orders = new ArrayList<>();
        try {
            Cursor cursor = getReadDB().query(Order.ORDER_TABLE.TB_NAME, db_columns,
                    Order.ORDER_TABLE.TB_COL.USER_EMAIL + " = '" + email + "'", null,
                    null, null, null);
            if (cursor != null) {
                while(cursor.moveToNext()) {
                    Order order = parseCursorToOrderObject(cursor);
                    orders.add(order);
                }
            }
        } catch (Exception ex) {
            // throw error message
            System.out.println(ex.getMessage());
        } finally {
            // Closing database connection
            getWriteDB().close();
        }

        return orders;
    }

    @Override
    public boolean insert(Order data) {
        try {
            ContentValues values = parseOrderObjectToContentValues(data, false);

            // Inserting Row
            long result = getWriteDB().insert(Order.ORDER_TABLE.TB_NAME, null, values);

            if (result > 0)
                return true;
            else
                throw new Exception("Unable to create the record");

        } catch (Exception ex) {
            // throw error message
            System.out.println(ex.getMessage());

            return false;
        } finally {
            // Closing database connection
            getWriteDB().close();
        }
    }

    @Override
    public boolean update(Order data) {
        try {
            ContentValues values = parseOrderObjectToContentValues(data, true);

            // Updating Row
            long result = getWriteDB().update(Order.ORDER_TABLE.TB_NAME, values,
                    Order.ORDER_TABLE.TB_COL.ID + " = '" + data.getID() + "'",
                    null);

            if (result > 0)
                return true;
            else
                throw new Exception("Unable to update the record");

        } catch (Exception ex) {
            // throw error message
            System.out.println(ex.getMessage());

            return false;
        } finally {
            // Closing database connection
            getWriteDB().close();
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            // Deleting Row
            long result = getWriteDB().delete(Order.ORDER_TABLE.TB_NAME,
                    Order.ORDER_TABLE.TB_COL.ID + " = '" + id + "'",
                    null);

            if (result > 0)
                return true;
            else
                throw new Exception("Unable to delete the record");

        } catch (Exception ex) {
            // throw error message
            System.out.println(ex.getMessage());

            return false;
        } finally {
            // Closing database connection
            getWriteDB().close();
        }
    }

    private Order parseCursorToOrderObject(Cursor cursor) {
        Order order = new Order();
        order.setID(Integer.parseInt(cursor.getString(0)));
        order.setUserEmail(cursor.getString(1));
        order.setRestaurantId(Integer.parseInt(cursor.getString(2)));
        order.setItems(cursor.getString(3));
        order.setDeliveryAddress(cursor.getString(4));
        order.setSpecialInstruction(cursor.getString(5));
        order.setSubTotal(Double.parseDouble(cursor.getString(6)));
        order.setDeliveryFee(Double.parseDouble(cursor.getString(7)));
        order.setDiscount(Double.parseDouble(cursor.getString(8)));
        order.setTax(Double.parseDouble(cursor.getString(9)));
        order.setTotal(Double.parseDouble(cursor.getString(10)));
        order.setCreatedAt(cursor.getString(11));

        return order;
    }

    private ContentValues parseOrderObjectToContentValues(Order order, boolean includeId) {
        ContentValues values = new ContentValues();
        if(includeId)
            values.put(Order.ORDER_TABLE.TB_COL.ID, order.getID());
        values.put(Order.ORDER_TABLE.TB_COL.USER_EMAIL, order.getUserEmail());
        values.put(Order.ORDER_TABLE.TB_COL.RESTAURANT_ID, order.getRestaurantId());
        values.put(Order.ORDER_TABLE.TB_COL.ITEMS_JSON, order.getItems());
        values.put(Order.ORDER_TABLE.TB_COL.DELIVERY_ADDRESS, order.getDeliveryAddress());
        values.put(Order.ORDER_TABLE.TB_COL.SPECIAL_INSTRUCTION, order.getSpecialInstruction());
        values.put(Order.ORDER_TABLE.TB_COL.SUB_TOTAL, order.getSubTotal());
        values.put(Order.ORDER_TABLE.TB_COL.DELIVERY_FEE, order.getDeliveryFee());
        values.put(Order.ORDER_TABLE.TB_COL.DISCOUNT, order.getDiscount());
        values.put(Order.ORDER_TABLE.TB_COL.TAX, order.getTax());
        values.put(Order.ORDER_TABLE.TB_COL.TOTAL, order.getTotal());
        values.put(Order.ORDER_TABLE.TB_COL.CREATED_AT, order.getCreatedAt());

        return values;
    }

}
