package com.appyhome.appyproduct.mvvm.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.appyhome.appyproduct.mvvm.data.model.db.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users")
    List<User> loadAll();

    @Query("SELECT * FROM users WHERE username IN (:userNames)")
    List<User> loadAllByIds(List<String> userNames);

    @Query("SELECT * FROM users WHERE username LIKE :name LIMIT 1")
    User findByUsername(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<User> users);

    @Delete
    void delete(User user);

}
