package com.karthik.chat;

import jakarta.websocket.*;

import jakarta.websocket.Session;

import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.lang.annotation.*;

@ServerEndpoint("/chat")
public class ChatServer {

    private static final Set<Session> sessions = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("User connected");
    }

    @OnMessage
    public void onMessage(String message, Session sender) throws IOException {
        for (Session session : session) {
            if (session.isOpen()) {
                session.getBasicRemote().sendText(message);
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        System.out.println("User disconnected");
    }
}
