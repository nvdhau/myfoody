package com.douglas.myfoody.core.DAO;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.douglas.myfoody.core.database.AppDatabase;
import com.douglas.myfoody.core.models.Promotion;
import com.douglas.myfoody.core.utilities.Utils;

import java.util.ArrayList;
import java.util.List;

public class PromotionDAO implements BaseDAO<Promotion> {
    private AppDatabase db;

    public PromotionDAO(Application application) {
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
    public List<Promotion> findAll() {
        return null;
    }

    @Override
    public Promotion findById(int id) {
        return null;
    }

    @Override
    public boolean insert(Promotion data) {
        try {
            ContentValues values = new ContentValues();

            values.put(Promotion.PROMOTION_TABLE.TB_COL.PROMOTION_CODE, data.getPromotionCode());
            values.put(Promotion.PROMOTION_TABLE.TB_COL.DISCOUNT_AMOUNT, data.getDiscountAmount());
            values.put(Promotion.PROMOTION_TABLE.TB_COL.DISCOUNT_TYPE, data.getDiscountType());

            // Inserting Row
            long result = getWriteDB().insert(Promotion.PROMOTION_TABLE.TB_NAME, null, values);

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
    public boolean update(Promotion data) {
        try {
            ContentValues values = new ContentValues();

            values.put(Promotion.PROMOTION_TABLE.TB_COL.PROMOTION_CODE, data.getPromotionCode());
            values.put(Promotion.PROMOTION_TABLE.TB_COL.DISCOUNT_AMOUNT, data.getDiscountAmount());
            values.put(Promotion.PROMOTION_TABLE.TB_COL.DISCOUNT_TYPE, data.getDiscountType());

            // Updating Row
            long result = getWriteDB().update(Promotion.PROMOTION_TABLE.TB_NAME, values,
                    Promotion.PROMOTION_TABLE.TB_COL.PROMOTION_CODE + " = '" + data.getPromotionCode() + "'",
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
        return false;
    }

    public boolean insertDiscount(Promotion data, String userEmail, String expiry) {
        try {
            ContentValues values = new ContentValues();

            values.put(Promotion.DISCOUNT_TABLE.TB_COL.USER_EMAIL, userEmail);
            values.put(Promotion.DISCOUNT_TABLE.TB_COL.PROMOTION_CODE, data.getPromotionCode());
            values.put(Promotion.DISCOUNT_TABLE.TB_COL.EXPIRY_DATE, expiry);

            // Inserting Row
            long result = getWriteDB().insert(Promotion.DISCOUNT_TABLE.TB_NAME, null, values);

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

    public List<Promotion> getSpecificUserDiscounts(String promotionCode, String email) {
        List<Promotion> promotions = new ArrayList<>();

        try {
            String query = "SELECT p." + Promotion.PROMOTION_TABLE.TB_COL.PROMOTION_CODE + ", "
                    + Promotion.PROMOTION_TABLE.TB_COL.DISCOUNT_AMOUNT + ", "
                    + Promotion.PROMOTION_TABLE.TB_COL.DISCOUNT_TYPE + " "
                    + "FROM " + Promotion.PROMOTION_TABLE.TB_NAME + " p "
                    + "JOIN " + Promotion.DISCOUNT_TABLE.TB_NAME + " d "
                    + "ON p." + Promotion.PROMOTION_TABLE.TB_COL.PROMOTION_CODE + " = d." + Promotion.DISCOUNT_TABLE.TB_COL.PROMOTION_CODE
                    + " WHERE d." + Promotion.DISCOUNT_TABLE.TB_COL.USER_EMAIL + "=?"
                    + " AND d." + Promotion.DISCOUNT_TABLE.TB_COL.PROMOTION_CODE + "=?";

            Cursor cursor = getReadDB().rawQuery(query, new String[] { email, promotionCode });
            if (cursor != null) {
                while(cursor.moveToNext()) {
                    Promotion promotion = new Promotion();
                    promotion.setPromotionCode(cursor.getString(0));
                    promotion.setDiscountAmount(cursor.getFloat(1));
                    promotion.setDiscountType(cursor.getString(2));

                    promotions.add(promotion);
                }
            }
        } catch (Exception ex) {
            // throw error message
            System.out.println(ex.getMessage());
        } finally {
            // Closing database connection
            getWriteDB().close();
        }

        return promotions;
    }

    public List<Promotion> getAllUserDiscounts(String email) {
        List<Promotion> promotions = new ArrayList<>();

        try {
            String query = "SELECT p." + Promotion.PROMOTION_TABLE.TB_COL.PROMOTION_CODE + ", "
                                    + Promotion.PROMOTION_TABLE.TB_COL.DISCOUNT_AMOUNT + ", "
                                    + Promotion.PROMOTION_TABLE.TB_COL.DISCOUNT_TYPE + ", "
                                    + Promotion.DISCOUNT_TABLE.TB_COL.EXPIRY_DATE + " "
                            + "FROM " + Promotion.PROMOTION_TABLE.TB_NAME + " p "
                            + "JOIN " + Promotion.DISCOUNT_TABLE.TB_NAME + " d "
                            + "ON p." + Promotion.PROMOTION_TABLE.TB_COL.PROMOTION_CODE + " = d." + Promotion.DISCOUNT_TABLE.TB_COL.PROMOTION_CODE
                            + " WHERE d." + Promotion.DISCOUNT_TABLE.TB_COL.USER_EMAIL + "=?";

            Cursor cursor = getReadDB().rawQuery(query, new String[] {email});
            if (cursor != null) {
                while(cursor.moveToNext()) {
                    Promotion promotion = new Promotion();
                    promotion.setPromotionCode(cursor.getString(0));
                    promotion.setDiscountAmount(cursor.getFloat(1));
                    promotion.setDiscountType(cursor.getString(2));

                    if(!Utils.checkExpired(cursor.getString(3)))
                        promotions.add(promotion);
                }
            }
        } catch (Exception ex) {
            // throw error message
            System.out.println(ex.getMessage());
        } finally {
            // Closing database connection
            getWriteDB().close();
        }

        return promotions;
    }

    public boolean expireDiscount(Promotion data, String email) {
        try {
            ContentValues values = new ContentValues();

            values.put(Promotion.DISCOUNT_TABLE.TB_COL.EXPIRY_DATE, Utils.formatDate(Utils.getToday()));

            // Updating Row
            long result = getWriteDB().update(Promotion.DISCOUNT_TABLE.TB_NAME, values,
                    Promotion.DISCOUNT_TABLE.TB_COL.USER_EMAIL + " = '" + email + "' AND " + Promotion.DISCOUNT_TABLE.TB_COL.PROMOTION_CODE + " = '" + data.getPromotionCode() + "'",
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
}
