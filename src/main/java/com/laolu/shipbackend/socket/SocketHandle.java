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

@Component
public class SocketHandle extends TextWebSocketHandler {
    public static final List<Integer> USER_IDS = new LinkedList<>();
    public static Map<String, SocketClient> CLIENT_POOL = new HashMap<>();
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

//    @Override
//    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        JsonObject json = new Gson().fromJson(message.getPayload(),JsonObject.class);
//        String reqContent = json.getAsJsonObject().get("data").getAsString();
//        int type = json.getAsJsonObject().get("type").getAsInt();
//
//        try {
//            Class<?> aClass = Class.forName(reqClass);
//            Method method = aClass.getDeclaredMethod(reqMethod,String.class, WebSocketSession.class);
//            Object o = aClass.getDeclaredConstructor().newInstance();
//            method.invoke(o, reqContent, session);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (type == ContactType.JOIN_GAME) {
//            String reqdata = AESTools.decrypt(reqContent,"0D7FB71E8EC31E97");
//            User user = GSON.fromJson(reqdata, User.class);
//            Class<?> aClass = Class.forName("com.");
//            Method method = aClass.getDeclaredMethod("abc",String.class, WebSocketSession.class);
//            Object o = aClass.getDeclaredConstructor().newInstance();
//            method.invoke(o, reqdata, session);
//
//            wsService.joinGame(user, session);
//        }
//
//        if(type == ContactType.SEND_MESSAGE) {
//            wsService.sendMessage(reqContent, session);
//        }
//
//        if(type == ContactType.HEART_BEAT) {
//            wsService.heartbeat(reqContent, session);
//        }
//    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println(session.getId() + "加入");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        ONLINE_COUNT = ONLINE_COUNT-1;
    }
}
