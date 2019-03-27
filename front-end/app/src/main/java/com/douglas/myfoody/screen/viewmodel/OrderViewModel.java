package com.douglas.myfoody.screen.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.douglas.myfoody.core.models.Order;
import com.douglas.myfoody.core.repository.OrderRepository;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {

    private OrderRepository orderRepository;
    private MutableLiveData<Order> mOrder;
    private MutableLiveData<List<Order>> mAllOrders;

    public OrderViewModel(@NonNull Application application) {
        super(application);

        orderRepository = new OrderRepository(application);

        if (mOrder == null) {
            mOrder = new MutableLiveData<>();
        }

        if (mAllOrders == null) {
            mAllOrders = new MutableLiveData<>();
        }
    }

    public MutableLiveData<Order> getOrder() {
        return mOrder;
    }

    public void setOrder(Order order) {
        mOrder.setValue(order);
    }

    public LiveData<Order> getOrderById(int id) {
        mOrder.setValue(orderRepository.findById(id));
        return mOrder;
    }

    public boolean insert(Order order) {
        if (!orderRepository.add(order)) {
            Toast.makeText(getApplication(), "Cannot create order!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            setOrder(order);
            return true;
        }
    }

    public boolean update(Order order) {
        if (!orderRepository.update(order)) {
            Toast.makeText(getApplication(), "Cannot update order!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            setOrder(order);
            return true;
        }
    }

    public List<Order> findByUserEmail(String email) {
        return orderRepository.findByUserEmail(email);
    }
}
