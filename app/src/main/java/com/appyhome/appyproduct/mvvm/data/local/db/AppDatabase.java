package com.appyhome.appyproduct.mvvm.data.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.appyhome.appyproduct.mvvm.data.local.db.dao.OptionDao;
import com.appyhome.appyproduct.mvvm.data.local.db.dao.QuestionDao;
import com.appyhome.appyproduct.mvvm.data.local.db.dao.UserDao;
import com.appyhome.appyproduct.mvvm.data.model.db.Option;
import com.appyhome.appyproduct.mvvm.data.model.db.Question;
import com.appyhome.appyproduct.mvvm.data.model.db.User;


@Database(entities = {User.class, Question.class, Option.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract QuestionDao questionDao();

    public abstract OptionDao optionDao();

}
