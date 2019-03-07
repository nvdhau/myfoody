package com.douglas.myfoody.core.DAO;

import android.arch.lifecycle.LiveData;
import java.util.List;

public interface BaseDAO<T> {

    LiveData<List<T>> findAll();
    LiveData<T> findById(int id);
    void add(T data);
    void update(T data);
    void delete(int id);
    void deleteAll();
}
