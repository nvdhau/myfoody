package com.douglas.myfoody.core.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

public class Promotion implements Parcelable {
    private String promotionCode;
    private double discountAmount;
    private String discountType;

    public static final String DISCOUNT_TYPE_PERCENT = "Percent";
    public static final String DISCOUNT_TYPE_FLAT = "Flat";

    public Promotion() {

    }

    protected Promotion(Parcel in) {
        promotionCode = in.readString();
        discountAmount = in.readDouble();
        discountType = in.readString();
    }

    public static final Creator<Promotion> CREATOR = new Creator<Promotion>() {
        @Override
        public Promotion createFromParcel(Parcel in) {
            return new Promotion(in);
        }

        @Override
        public Promotion[] newArray(int size) {
            return new Promotion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(promotionCode);
        dest.writeDouble(discountAmount);
        dest.writeString(discountType);
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

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public double getDiscountAmount(double subtotal) {
        double discount = 0.0;
        if(DISCOUNT_TYPE_PERCENT.equals(discountType)) {
            discount = subtotal * discountAmount / 100.0;
        } else if(DISCOUNT_TYPE_FLAT.equals(discountType)) {
            discount = Math.min(subtotal, discountAmount);
        }
        return discount;
    }
}
