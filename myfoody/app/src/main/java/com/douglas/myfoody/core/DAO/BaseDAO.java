package com.douglas.myfoody.core.DAO;

import android.arch.lifecycle.LiveData;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public interface BaseDAO<T> {

    SQLiteDatabase getWriteDB();
    SQLiteDatabase getReadDB();

    List<T> findAll();
    T findById(int id);
    boolean insert(T data);
    boolean update(T data);
    boolean delete(int id);
}
