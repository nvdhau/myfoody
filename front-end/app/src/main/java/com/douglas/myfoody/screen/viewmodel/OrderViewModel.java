package com.douglas.myfoody.screen.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.douglas.myfoody.core.models.Order;
import com.douglas.myfoody.core.repository.OrderRepository;

public class OrderViewModel extends AndroidViewModel {

    private OrderRepository orderRepository;
    private MutableLiveData<Order> mOrder;

    public OrderViewModel(@NonNull Application application) {
        super(application);

        orderRepository = new OrderRepository(application);

        if (mOrder == null) {
            mOrder = new MutableLiveData<>();
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

    public void insert(Order order) {
        if (!orderRepository.add(order)) {
            Toast.makeText(getApplication(), "Cannot create order!", Toast.LENGTH_SHORT).show();
        } else {
            setOrder(order);
        }
    }

    public void update(Order order) {
        if (!orderRepository.update(order)) {
            Toast.makeText(getApplication(), "Cannot update order!", Toast.LENGTH_SHORT).show();
        } else {
            setOrder(order);
        }
    }
}
