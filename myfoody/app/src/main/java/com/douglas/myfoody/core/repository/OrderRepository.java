package com.douglas.myfoody.core.repository;

import android.app.Application;

import com.douglas.myfoody.core.DAO.OrderDAO;
import com.douglas.myfoody.core.models.Order;

import java.util.List;

public class OrderRepository implements BaseRepository<Order> {

    private OrderDAO orderDAO;

    public OrderRepository(Application application) {
        orderDAO = new OrderDAO(application);
    }

    @Override
    public List<Order> findAll() {
        return orderDAO.findAll();
    }

    @Override
    public Order findById(int id) {
        return orderDAO.findById(id);
    }

    @Override
    public boolean add(Order data) {
        return orderDAO.insert(data);
    }

    @Override
    public boolean update(Order data) {
        return orderDAO.update(data);
    }

    @Override
    public boolean delete(int id) {
        return orderDAO.delete(id);
    }

    @Override
    public boolean deleteAll() {
        // TODO
        return false;
    }

    public List<Order> findByUserEmail(String email) {
        return orderDAO.findByUserEmail(email);
    }
}
