package com.exam.myapp;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.wuxiaolong.androidutils.library.LogUtil;

public class MService extends Service {
    private static final int MSG_FROM_CLIENT = 10;
    private static final String TAG = MService.class.getSimpleName();
    private MyBinder binder = new MyBinder();
    private static final int MSG_FROM_SERVICE = 20;

    @SuppressLint("HandlerLeak")
    private final Messenger messenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_FROM_CLIENT:
                    LogUtil.i("msg from client: " + msg.getData().getString("msg"));
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Messenger messenger = msg.replyTo;
                    Message message = Message.obtain(null, MSG_FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString("msg", "this is Service send Msg");
                    message.setData(bundle);
                    try {
                        messenger.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    });

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.e(TAG, "onBind()");
        return messenger.getBinder();
    }

    public class MyBinder extends Binder {
        MService getService() {
            return MService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.e(MService.TAG, "onCreate()");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.e(TAG, "onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e(TAG, "onDestroy()");
    }
}
