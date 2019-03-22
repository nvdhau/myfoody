package com.douglas.myfoody.core.repository;

import android.app.Application;

import com.douglas.myfoody.core.DAO.OrderDAO;
import com.douglas.myfoody.core.DAO.PromotionDAO;
import com.douglas.myfoody.core.models.Promotion;

import java.util.List;

public class PromotionRepository implements BaseRepository<Promotion> {
    private PromotionDAO promotionDAO;

    public PromotionRepository(Application application) {
        promotionDAO = new PromotionDAO(application);
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
    public boolean add(Promotion data) {
        return promotionDAO.insert(data);
    }

    @Override
    public boolean update(Promotion data) {
        return promotionDAO.update(data);
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean deleteAll() {
        return false;
    }
}
