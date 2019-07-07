package com.exam.myapp.socket;

import com.wuxiaolong.androidutils.library.LogUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ChatSocket extends Thread {
    Socket socket;
    BufferedWriter bw;
    public ChatSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                LogUtil.e(line);
                // 多人聊天的实现方法
//                SocketChatManager.getChatManager().publish(this, line);
                out(line);
            }
            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void out(String out) {
        try {
            bw.write(out + "\n");
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
