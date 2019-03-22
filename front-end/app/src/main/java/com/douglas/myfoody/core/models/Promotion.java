package com.douglas.myfoody.core.models;

import android.provider.BaseColumns;

public class Promotion {
    private String promotionCode;
    private float discountAmount;
    private String discountType;

    public Promotion() {

    }

    // DEFINE TABLE PROMOTION
    public static final class PROMOTION_TABLE {
        public static final String TB_NAME = "promotion";

        public static final class TB_COL implements BaseColumns {
            public static final String PROMOTION_CODE = "promotionCode";
            public static final String DISCOUNT_AMOUNT = "discountAmount";
            public static final String DISCOUNT_TYPE = "discountType";
        }
    }

    // DEFINE TABLE DISCOUNT
    public static final class DISCOUNT_TABLE {
        public static final String TB_NAME = "discount";

        public static final class TB_COL implements BaseColumns {
            public static final String ID = "id";
            public static final String USER_EMAIL = "email";
            public static final String PROMOTION_CODE = "promotionCode";
            public static final String EXPIRY_DATE = "expiry";
        }
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public float getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(float discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }
}
