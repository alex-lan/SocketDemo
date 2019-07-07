package com.exam.myapp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.exam.myapp.socket.ServerListener;
import com.wuxiaolong.androidutils.library.LogUtil;

public class SocketService extends Service {
    private static final String TAG = SocketService.class.getSimpleName();
    ServerListener serverListener;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.e(TAG, "onBind()");
        return new Binder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.e(TAG, "onCreate()");
        serverListener = new ServerListener();
        serverListener.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e(TAG, "onDestroy()");
        serverListener.closeAllSockets();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.e(TAG, "onUnbind()");
        return super.onUnbind(intent);
    }
}
