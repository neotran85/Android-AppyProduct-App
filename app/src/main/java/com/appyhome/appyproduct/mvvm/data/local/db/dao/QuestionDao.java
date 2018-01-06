package com.appyhome.appyproduct.mvvm.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.appyhome.appyproduct.mvvm.data.model.db.Question;

import java.util.List;

@Dao
public interface QuestionDao {

    @Query("SELECT * FROM questions")
    List<Question> loadAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Question question);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Question> questions);

}
