package com.laolu.shipbackend.socket.handler;

import com.google.gson.Gson;
import com.laolu.shipbackend.model.SocketClient;
import com.laolu.shipbackend.model.response.ChatMessageResponse;
import com.laolu.shipbackend.socket.SocketHandle;
import com.laolu.shipbackend.utils.AESTools;
import com.laolu.shipbackend.utils.ContactType;
import com.laolu.shipbackend.utils.JsonResponse;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;

/**
 * @author wanyi.lu
 * @date Created in 2023/1/4 10:29
 */
public class ChatHandler {
    public void sendMessage(String req, WebSocketSession session) {
        String reqdata = AESTools.decrypt(req, SocketHandle.CLIENT_POOL.get(session.getId()).getUser().getAuthKey());
        ChatMessageResponse chatMessageResponse = new Gson().fromJson(reqdata, ChatMessageResponse.class);
        for (Map.Entry<String, SocketClient> player : SocketHandle.CLIENT_POOL.entrySet()) {
            WebSocketMessage<String> webSocketMessage = new TextMessage(JsonResponse.success(chatMessageResponse, ContactType.SEND_MESSAGE,player.getValue().getUser().getAuthKey()));
            if (player.getValue().getWebSocketSession().isOpen()) {
                try {
                    player.getValue().getWebSocketSession().sendMessage(webSocketMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
