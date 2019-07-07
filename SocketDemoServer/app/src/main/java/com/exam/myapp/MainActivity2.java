package com.exam.myapp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.exam.myapp.base.BaseActivity;

import butterknife.OnClick;

// AIDL Demo
public class MainActivity2 extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    }

    @OnClick(R.id.btn_startser)void v() {

    }
}
