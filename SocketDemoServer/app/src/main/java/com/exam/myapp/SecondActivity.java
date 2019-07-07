package com.exam.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.exam.myapp.base.BaseActivity;
import com.exam.myapp.entity.People;
import com.exam.myapp.entity.Person;
import com.wuxiaolong.androidutils.library.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import butterknife.BindView;
import butterknife.OnClick;

public class SecondActivity extends BaseActivity {
    @BindView(R.id.tv_1)
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_second);

//        textView.setText(getIntent().getStringExtra("key"));

        SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
        textView.setText(sp.getString("key", ""));

//        fileRead();

//        LogUtil.i(MainActivity.mTag);

//        textView.setText(MainActivity.mTag);
//        deserialized();
        Person person = getIntent().getParcelableExtra("parcle");
        LogUtil.i("name:" + person.getName() + "  age:" + person.getAge());
    }

    private void deserialized() {
        String path = getExternalCacheDir().getAbsolutePath();
        File file = new File(path + File.separator + "people.txt");
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            People p = (People) ois.readObject();
            LogUtil.i(p.getName() + " " + p.getAge());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
            LogUtil.i(str);
            textView.setText(str);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_thirdact)
    void f() {
        Intent intent = new Intent(this, ThirdActivity.class);
        intent.putExtra("key", "from " + SecondActivity.class.getSimpleName());
        startActivity(intent);
    }
}
