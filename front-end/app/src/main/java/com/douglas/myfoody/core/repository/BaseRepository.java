package com.douglas.myfoody.core.repository;

import android.arch.lifecycle.LiveData;

import java.util.List;

public interface BaseRepository<T> {

    LiveData<List<T>> findAll();
    LiveData<T> findById(int id);
    boolean add(T data);
    boolean update(T data);
    boolean delete(int id);
    boolean deleteAll();
}
