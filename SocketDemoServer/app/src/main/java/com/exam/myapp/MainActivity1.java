package com.exam.myapp;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.exam.myapp.base.BaseActivity;
import com.wuxiaolong.androidutils.library.LogUtil;
// Messager Demo
public class MainActivity1 extends BaseActivity {
    TextView textView;
    Button btnSend;
    //    private IBinder binder;
    private static final int MSG_FROM_CLIENT = 10;
    private static final int MSG_FROM_SERVICE = 20;
    Messenger messenger;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        textView = findViewById(R.id.tv_1);
        btnSend = findViewById(R.id.btn_send);

        Intent intent = new Intent();
        intent.setClassName("com.exam.myapp","com.exam.myapp.MService");
        bindService(intent, connection, BIND_AUTO_CREATE);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("msg", "this is client send Msg");
                Message message = Message.obtain(null, MSG_FROM_CLIENT);
                message.replyTo = msger;// 将客户端的信使对象装入
                message.setData(bundle);
                try {
                    messenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private final Messenger msger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_FROM_SERVICE:
                    LogUtil.i("msg from Service: " + msg.getData().getString("msg"));
                    break;
            }
        }
    });

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            textView.setText("Service Connected!");
//            MainActivity1.this.binder = binder;
            messenger = new Messenger(binder);// 获得服务端的信使对象
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
