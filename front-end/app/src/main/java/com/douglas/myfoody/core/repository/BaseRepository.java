package com.douglas.myfoody.core.repository;

import java.util.List;

public interface BaseRepository<T> {

    List<T> findAll();
    T findById(int id);
    boolean add(T data);
    boolean update(T data);
    boolean delete(int id);
    boolean deleteAll();
}
