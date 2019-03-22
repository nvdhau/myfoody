package com.douglas.myfoody.core.DAO;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.douglas.myfoody.core.database.AppDatabase;
import com.douglas.myfoody.core.models.Order;
import com.douglas.myfoody.core.models.Promotion;

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
            ContentValues values = parsePromotionObjectToContentValues(data);

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
            ContentValues values = parsePromotionObjectToContentValues(data);

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

    private Promotion parseCursorToPromotionObject(Cursor cursor) {
        Promotion promotion = new Promotion();
        promotion.setPromotionCode(cursor.getString(0));
        promotion.setDiscountAmount(cursor.getFloat(1));
        promotion.setDiscountType(cursor.getString(2));

        return promotion;
    }

    private ContentValues parsePromotionObjectToContentValues(Promotion promotion) {
        ContentValues values = new ContentValues();

        values.put(Promotion.PROMOTION_TABLE.TB_COL.PROMOTION_CODE, promotion.getPromotionCode());
        values.put(Promotion.PROMOTION_TABLE.TB_COL.DISCOUNT_AMOUNT, promotion.getDiscountAmount());
        values.put(Promotion.PROMOTION_TABLE.TB_COL.DISCOUNT_TYPE, promotion.getDiscountType());

        return values;
    }
}
