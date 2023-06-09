package com.laolu.shipbackend.socket.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.laolu.shipbackend.model.Heartbeat;
import com.laolu.shipbackend.model.SocketClient;
import com.laolu.shipbackend.model.response.StarResponse;
import com.laolu.shipbackend.model.User;
import com.laolu.shipbackend.model.response.HeartBeatResponse;
import com.laolu.shipbackend.model.response.JoinGameResponse;
import com.laolu.shipbackend.model.response.UserResponse;
import com.laolu.shipbackend.service.CacheService;
import com.laolu.shipbackend.service.StarService;
import com.laolu.shipbackend.socket.SocketHandle;
import com.laolu.shipbackend.utils.AESTools;
import com.laolu.shipbackend.utils.JsonResponse;
import com.laolu.shipbackend.utils.ContactType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

import static com.laolu.shipbackend.utils.JsonResponse.GSON;

/**
 * @author wanyi.lu
 * @date Created in 2022/9/11 18:50
 */
@Component
public class MainHandler {

    static StarService starService;

    @Autowired
    public void setStarService(StarService starService) {
        MainHandler.starService = starService;
    }
    @Autowired
    CacheService cacheService;

    public void joinGame(String req, WebSocketSession session) throws IOException {
        String reqdata = AESTools.decrypt(req,"0D7FB71E8EC31E97");
        User user = GSON.fromJson(reqdata, User.class);
        if(user.getAuthKey() != null) {
            if (!SocketHandle.USER_IDS.contains(user.getId())) {
                List<StarResponse> starResponses = starService.getStarList(user.getGalaxyId());
                List<UserResponse> players = new ArrayList<>();
                SocketClient socketClient = new SocketClient();
                socketClient.setWebSocketSession(session);
                user.setToken(session.getId());
                user.setLastBeatTime(System.currentTimeMillis());
                socketClient.setUser(user);
                JoinGameResponse joinGameResponse = new JoinGameResponse();
                for (Map.Entry<String, SocketClient> player : SocketHandle.CLIENT_POOL.entrySet()) {
                    if (player.getValue().getWebSocketSession().isOpen()) {
                        if (player.getValue().getUser().getGalaxyId().intValue() == user.getGalaxyId().intValue()) {
                            UserResponse userResponse = new UserResponse();
                            userResponse.setPosX(player.getValue().getUser().getPosX());
                            userResponse.setPosY(player.getValue().getUser().getPosY());
                            userResponse.setNickName(player.getValue().getUser().getNickName());
                            players.add(userResponse);
                        }
                    }
                }
                joinGameResponse.setStars(starResponses);
                joinGameResponse.setPlayers(players);
                joinGameResponse.setToken(session.getId());
                session.sendMessage(new TextMessage(JsonResponse.success(joinGameResponse, ContactType.JOIN_GAME, user.getAuthKey())));
                SocketHandle.USER_IDS.add(user.getId());
                SocketHandle.CLIENT_POOL.put(session.getId(),socketClient);
                UserResponse userResponse = new UserResponse();
                userResponse.setPosX(user.getPosX());
                userResponse.setPosY(user.getPosY());
                userResponse.setNickName(user.getNickName());
                for (Map.Entry<String, SocketClient> player : SocketHandle.CLIENT_POOL.entrySet()) {
                    if (player.getValue().getWebSocketSession().isOpen()) {
                        if (player.getValue().getUser().getGalaxyId().intValue() == user.getGalaxyId().intValue()  && player.getValue().getUser().getId().intValue() != user.getId().intValue()) {
                            try {
                                player.getValue().getWebSocketSession().sendMessage(new TextMessage(JsonResponse.success(userResponse, ContactType.NEW_PLAYERS, player.getValue().getUser().getAuthKey())));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            SocketHandle.ONLINE_COUNT = SocketHandle.ONLINE_COUNT+1;
        }
    }

    public void heartBeat(String req, WebSocketSession session) {
        String reqdata = AESTools.decrypt(req,SocketHandle.CLIENT_POOL.get(session.getId()).getUser().getAuthKey());
        SocketHandle.CLIENT_POOL.get(session.getId()).getUser().setLastBeatTime(System.currentTimeMillis());
        Heartbeat heartbeat = new Gson().fromJson(reqdata, Heartbeat.class);
        SocketHandle.CLIENT_POOL.get(session.getId()).getUser().setPosX(heartbeat.getPosX());
        SocketHandle.CLIENT_POOL.get(session.getId()).getUser().setPosY(heartbeat.getPosY());
        List<UserResponse> players = new ArrayList<>();
        User user = SocketHandle.CLIENT_POOL.get(session.getId()).getUser();
        for (Map.Entry<String, SocketClient> player : SocketHandle.CLIENT_POOL.entrySet()) {
            if (player.getValue().getWebSocketSession().isOpen()) {
                if (player.getValue().getUser().getGalaxyId().intValue() == user.getGalaxyId().intValue()
                        && player.getValue().getUser().getId().intValue() != user.getId().intValue()) {
                    UserResponse userResponse = new UserResponse();
                    userResponse.setPosX(player.getValue().getUser().getPosX());
                    userResponse.setPosY(player.getValue().getUser().getPosY());
                    userResponse.setNickName(player.getValue().getUser().getNickName());
                    players.add(userResponse);
                }
            }
        }
        HeartBeatResponse heartBeatResponse = new HeartBeatResponse();
        heartBeatResponse.setPlayers(players);
        try {
            session.sendMessage(new TextMessage(JsonResponse.success(heartBeatResponse, ContactType.HEART_BEAT, user.getAuthKey())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void leftGame(String req, WebSocketSession session) {
        User leftUser = SocketHandle.CLIENT_POOL.get(session.getId()).getUser();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("nickName", leftUser.getNickName());
        for (Map.Entry<String, SocketClient> player : SocketHandle.CLIENT_POOL.entrySet()) {
            if (player.getValue().getWebSocketSession().isOpen()) {
                if (player.getValue().getUser().getGalaxyId().intValue() == leftUser.getGalaxyId().intValue()) {
                    try {
                        player.getValue().getWebSocketSession().sendMessage(
                                new TextMessage(
                                        JsonResponse.success(jsonObject, ContactType.LEFT_GAME, player.getValue().getUser().getAuthKey())
                                )
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        SocketHandle.USER_IDS.remove(leftUser.getId());
        SocketHandle.CLIENT_POOL.remove(session.getId());
    }

    @Scheduled(cron = "0/3 * * * * ?")
    public void execute() {
        for (Map.Entry<String, SocketClient> player : SocketHandle.CLIENT_POOL.entrySet()) {
            boolean left = !player.getValue().getWebSocketSession().isOpen();
            User user = player.getValue().getUser();
            if (System.currentTimeMillis() - user.getLastBeatTime() > 5000) {
                left = true;
            }
            if (left) {
                leftGame(null, player.getValue().getWebSocketSession());
            }
        }
    }
}
