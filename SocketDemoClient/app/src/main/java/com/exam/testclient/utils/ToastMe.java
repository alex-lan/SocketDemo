package com.exam.testclient.utils;

import android.widget.Toast;

import com.exam.testclient.App;


public class ToastMe {
    // 私有构造，不能new
    private ToastMe() {

    }

    private static Toast toast;

    public static void show(int strRes) {
        show(App.getAppContext().getString(strRes));
    }

    public static void show(String msg) {
        if (App.getAppContext() != null) {
            if (toast == null) {
                toast = Toast.makeText(App.getAppContext(), msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            toast.show();
        }
    }
}
