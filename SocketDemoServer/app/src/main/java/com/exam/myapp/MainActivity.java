package com.exam.myapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.exam.myapp.base.BaseActivity;
import com.exam.myapp.entity.People;
import com.exam.myapp.entity.Person;
import com.exam.myapp.utils.ToastMe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import butterknife.OnClick;

/**
 * I/O Stream Demo
 */
public class MainActivity extends BaseActivity {

    public static String mTag = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileTest();
        spTest();

//        serialized();
    }

    private void serialized() {
        People p = new People();
        p.setAge(10);
        p.setName("Lily");

        String path = getExternalCacheDir().getAbsolutePath();
        File file = new File(path + File.separator + "people.txt");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(p);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void spTest() {
        SharedPreferences sp = getSharedPreferences("sp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("key", "MainActivity");
    }

    private void fileTest() {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput("test.txt", Context.MODE_PRIVATE);
            fileOutputStream.write("test file text!".getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ToastMe.show(intent.getStringExtra("key"));
    }

    @OnClick({R.id.btn_intent, R.id.btn_secondact})
    void f(View v) {
        switch (v.getId()) {
            case R.id.btn_intent: {
                Intent intent = new Intent(this, IntentAct.class);
                intent.putExtra("key", "Name");
                startActivity(intent);
            }
            break;
            case R.id.btn_secondact: {
                Person person = new Person();
                person.setAge(10);
                person.setName("Tom");
                person.setSex("Male");
                Intent intent = new Intent(this, SecondActivity.class);
                intent.putExtra("key", "from " + MainActivity.class.getSimpleName());
                intent.putExtra("parcle", person);
                startActivity(intent);
            }
            break;
        }
    }
}
