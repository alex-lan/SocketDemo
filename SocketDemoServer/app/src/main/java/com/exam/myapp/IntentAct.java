package com.exam.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.exam.myapp.base.BaseActivity;
import com.exam.myapp.utils.ToastMe;

import butterknife.OnClick;

public class IntentAct extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_intent);
        ToastMe.show(getIntent().getStringExtra("key"));
    }

    @OnClick(R.id.btn_sent)
    void v() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("key","发送信息回MainActivity");
        startActivity(intent);
        finish();
    }
}
