package com.laolu.shipbackend.model;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author wanyi.lu
 * @date Created in 2022/9/11 11:16
 */

@Getter
@Setter
public class SocketClient {
    private WebSocketSession webSocketSession;
    private User user;
    private int timestamp;
}
