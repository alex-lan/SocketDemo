package com.exam.myapp.socket;

import com.wuxiaolong.androidutils.library.LogUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener extends Thread {
    ServerSocket serverSocket;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(12345);
            while (true) {
                Socket socket = serverSocket.accept();
                LogUtil.e("有客户端连接到 12345 端口");
                ChatSocket chatSocket = new ChatSocket(socket);
                chatSocket.start();
                SocketChatManager.getChatManager().add(chatSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeAllSockets() {
        try {
            for (ChatSocket socket : SocketChatManager.getChatManager().vector) {
                socket.socket.close();
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
