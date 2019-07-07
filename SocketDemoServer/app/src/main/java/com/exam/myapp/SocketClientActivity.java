package com.exam.myapp;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.exam.myapp.base.BaseActivity;
import com.exam.myapp.utils.NetWorkUtils;
import com.exam.myapp.utils.ToastMe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Socket 客户端
 */
public class SocketClientActivity extends BaseActivity {

    @BindView(R.id.et_ip)
    EditText etIp;
    @BindView(R.id.btn_connect)
    Button btnConnect;
    @BindView(R.id.sv_content)
    ScrollView scrollView;
    @BindView(R.id.et_msg)
    EditText etMsg;
    @BindView(R.id.btn_send)
    Button btnSend;
    String hostStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_socketclient);

        getHostStr();
    }

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
                if (hostStr != null) {
                    connect();
                } else {
                    getHostStr();
                    if (hostStr != null) {
                        connect();
                    }
                }
                break;
        }

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
                    socket = new Socket(etIp.getText().toString(), 12345);
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    publishProgress("@success...");
                } catch (UnknownHostException e) {
                    ToastMe.show("连接失败！");
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                    ToastMe.show("连接失败！");
                }
                try {
                    String line;
                    while ((line = reader.readLine()) != null) {
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
                if (values[0].equals("@success...")) {
                    ToastMe.show("连接成功！");
                }
                TextView textView = new TextView(SocketClientActivity.this);
                ScrollView.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                        , ViewGroup.LayoutParams.WRAP_CONTENT);
                textView.setLayoutParams(params);
                textView.setText(values[0]);
                scrollView.addView(textView);
            }
        };

    }

    private void send() {
        try {
            writer.write(etMsg.getText().toString());
            etMsg.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
