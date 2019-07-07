package com.exam.myapp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.exam.myapp.base.BaseActivity;
import com.exam.myapp.utils.DBManager;

public class MainActivity3 extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DBManager manager = new DBManager(this);
//        for (int i = 1; i <= 10; i++) {
//            manager.insertBook("三体：黑暗森林 " + i);
//        }

//        manager.insertPerson("Andy", 14, "Male");
//        manager.insertPerson("Lily", 15, "Female");
//        manager.insertPerson("LiLei", 16, "Male");
//        manager.insertPerson("HanMeuMei", 15, "Female");
//
//        manager.queryBook();
//        manager.queryPerson();
    }
}
