package com.laolu.shipbackend.socket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.laolu.shipbackend.model.SocketClient;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SocketHandle extends TextWebSocketHandler {
    public static final List<Integer> USER_IDS = new LinkedList<>();
    public static Map<String, SocketClient> CLIENT_POOL = new ConcurrentHashMap<>();
    public static int ONLINE_COUNT = 0;


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            JsonObject json = new Gson().fromJson(message.getPayload(), JsonObject.class);
            String reqContent = json.getAsJsonObject().get("data").getAsString();
            String reqClass = json.getAsJsonObject().get("class").getAsString();
            String reqMethod = json.getAsJsonObject().get("method").getAsString();
            Class<?> aClass = null;
            aClass = Class.forName(reqClass);
            Method method = aClass.getDeclaredMethod(reqMethod, String.class, WebSocketSession.class);
            Object o = aClass.getDeclaredConstructor().newInstance();
            method.invoke(o, reqContent, session);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println(session.getId() + "加入");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        ONLINE_COUNT = ONLINE_COUNT-1;
    }
}
