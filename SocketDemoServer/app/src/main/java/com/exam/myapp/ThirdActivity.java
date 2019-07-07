package com.exam.myapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.exam.myapp.base.BaseActivity;
import com.wuxiaolong.androidutils.library.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;

public class ThirdActivity extends BaseActivity {
    @BindView(R.id.tv_1)
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_third);

//        textView.setText(getIntent().getStringExtra("key"));

//        SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
//        textView.setText(sp.getString("key",""));

//        fileRead();
        LogUtil.i(MainActivity.mTag);
        textView.setText(MainActivity.mTag);

    }

    private void fileRead() {
        FileInputStream fileInputStream = null;
        ByteArrayOutputStream baos = null;
        try {
            fileInputStream = openFileInput("test.txt");
            int length = fileInputStream.available();
            byte[] bytes = new byte[length];
            baos = new ByteArrayOutputStream();
            while (fileInputStream.read(bytes) != -1) {
                baos.write(bytes, 0, bytes.length);
            }
            String str = new String(baos.toByteArray());
            textView.setText(str);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
