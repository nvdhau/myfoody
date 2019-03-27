package com.douglas.myfoody.screen.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.douglas.myfoody.core.models.Promotion;
import com.douglas.myfoody.core.repository.PromotionRepository;

import java.util.List;

public class PromotionViewModel extends AndroidViewModel {

    private PromotionRepository promotionRepository;

    public PromotionViewModel(@NonNull Application application) {
        super(application);

        promotionRepository = new PromotionRepository(application);
    }

    public List<Promotion> getSpecificUserDiscounts(String promotionCode, String email) {
        return promotionRepository.getSpecificUserDiscounts(promotionCode, email);
    }

    public boolean addDiscount(Promotion promotion, String email, String expiry) {
        return promotionRepository.insertDiscount(promotion, email, expiry);
    }

    public List<Promotion> getUserDiscounts(String email) {
        return promotionRepository.getUserDiscounts(email);
    }

    public boolean expireDiscount(Promotion data, String email) {
        return promotionRepository.expireDiscount(data, email);
    }
}
