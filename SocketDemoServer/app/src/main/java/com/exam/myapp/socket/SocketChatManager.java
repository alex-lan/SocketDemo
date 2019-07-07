package com.exam.myapp.socket;

import java.util.Vector;

public class SocketChatManager {

    private SocketChatManager() {
    }


    private static SocketChatManager manager = new SocketChatManager();

    public static SocketChatManager getChatManager() {
        return manager;
    }

    Vector<ChatSocket> vector = new Vector<>();

    public void add(ChatSocket socket) {
        vector.add(socket);
    }

    // 实现多人聊天的方法
    public void publish(ChatSocket socket, String out) {
        for (int i = 0; i < vector.size(); i++) {
            ChatSocket chatSocket = vector.get(i);
            chatSocket.out(out);
        }
    }

}
