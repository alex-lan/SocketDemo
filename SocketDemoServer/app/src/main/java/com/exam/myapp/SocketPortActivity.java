package com.exam.myapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.exam.myapp.base.BaseActivity;
import com.exam.myapp.utils.NetWorkUtils;
import com.exam.myapp.utils.ToastMe;
import com.wuxiaolong.androidutils.library.LogUtil;
import com.wuxiaolong.androidutils.library.NetConnectUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import butterknife.BindView;
import butterknife.OnClick;

public class SocketPortActivity extends BaseActivity {
    @BindView(R.id.tv_1)
    TextView textView;
    public static final int minPort = 10;
    public static final int maxPort = 800;
    private ScanPortThread scanPortThread;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_socketport);

//        if (NetWorkUtils.checkEnable(this)) {
//            LogUtil.e(NetWorkUtils.getLocalIpAddress(this));
//            ToastMe.show(NetWorkUtils.getLocalIpAddress(this));
//        }
    }

    @OnClick(R.id.btn_startscan)
    void f() {
        scanPortThread = new ScanPortThread(minPort, maxPort);
        scanPortThread.start();
        textView.setText("");
        progressBar.setVisibility(View.VISIBLE);

    }

    private class ScanPortThread extends Thread {
        private int minPort;
        private int maxPort;

        public ScanPortThread(int minPort, int maxPort) {
            this.maxPort = maxPort;
            this.minPort = minPort;
        }

        @Override
        public void run() {
            super.run();
            for (int i = minPort; i <= maxPort; i++) {
                Socket socket = new Socket();
                SocketAddress socketAddress = new InetSocketAddress("192.168.31.228", i);
                try {
                    socket.connect(socketAddress, 50);
                    handler.sendEmptyMessage(i);
                    LogUtil.e(i + "");
                } catch (IOException e) {
//                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            textView.append(msg.what + "  OK\n");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
