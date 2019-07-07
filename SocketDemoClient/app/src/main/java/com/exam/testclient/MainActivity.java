package com.exam.testclient;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exam.testclient.utils.NetWorkUtils;
import com.exam.testclient.utils.ToastMe;
import com.wuxiaolong.androidutils.library.LogUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.et_ip)
    EditText etIp;
    @BindView(R.id.btn_connect)
    Button btnConnect;
    @BindView(R.id.lay_content)
    LinearLayout lay_content;
    @BindView(R.id.et_msg)
    EditText etMsg;
    @BindView(R.id.btn_send)
    Button btnSend;
    String hostStr;
    private boolean isSerConnected = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getHostStr();

        startService();
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            LogUtil.e("Service Connected!");
            ToastMe.show("启动服务成功!");

            isSerConnected = true;

            TextView textView = new TextView(MainActivity.this);
            textView.setText("@ Service Connected!");
            lay_content.addView(textView);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private String getHostStr() {
        if (NetWorkUtils.checkEnable(this)) {
            if (!NetWorkUtils.getLocalIpAddress(this).equals("0.0.0.0")) {
                hostStr = NetWorkUtils.getLocalIpAddress(this);
                etIp.setText(hostStr);
            } else {
                ToastMe.show("获取本机地址失败！");
            }
        } else {
            ToastMe.show("网络不可用！");
        }
        return hostStr;
    }

    @OnClick({R.id.btn_connect, R.id.btn_send})
    void f(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                send();
                break;
            case R.id.btn_connect:
                if (isSerConnected) {
                    if (hostStr != null) {
                        connect();
                    } else {
                        getHostStr();
                        if (hostStr != null) {
                            connect();
                        }
                    }
                } else {
                    ToastMe.show("服务尚未启动！");
                }
                break;
        }

    }

    private void startService() {
        Intent intent = new Intent();
        intent.setClassName("com.exam.myapp", "com.exam.myapp.SocketService");
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    //---------------------
    Socket socket = null;
    BufferedReader reader;
    BufferedWriter writer;

    private void connect() {
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, String, Void> read = new AsyncTask<Void, String, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    publishProgress("@ Socket Connecting...");
                    socket = new Socket(hostStr, 12345);
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    publishProgress("@ Success!");
                } catch (UnknownHostException e) {
                    ToastMe.show("连接失败！");
                    publishProgress("@ connect failed！");
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                    ToastMe.show("连接失败！");
                    publishProgress("@ connect failed！");
                }
                try {
                    String line;
                    while (reader != null && (line = reader.readLine()) != null) {
                        LogUtil.e(line);
                        publishProgress(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(String... values) { //
                super.onProgressUpdate(values);
                if (values[0].equals("@ Success!")) {
                    ToastMe.show("连接成功！");
                }
                TextView textView = new TextView(MainActivity.this);
                textView.setText(values[0]);
                lay_content.addView(textView);
            }
        };
        read.execute();
    }

    private void send() {
        if (socket == null) {
            ToastMe.show("Socket尚未连接！");
            return;
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    writer.write(etMsg.getText().toString() + "\n");
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        etMsg.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        unbindService(connection);
    }
}
