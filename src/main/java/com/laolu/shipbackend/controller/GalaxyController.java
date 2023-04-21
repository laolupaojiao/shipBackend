package com.laolu.shipbackend.controller;

import com.google.gson.JsonObject;
import com.laolu.shipbackend.model.SocketClient;
import com.laolu.shipbackend.model.User;
import com.laolu.shipbackend.model.response.GalaxyResponse;
import com.laolu.shipbackend.model.response.UserResponse;
import com.laolu.shipbackend.service.GalaxyService;
import com.laolu.shipbackend.socket.SocketHandle;
import com.laolu.shipbackend.utils.CommonResponse;
import com.laolu.shipbackend.utils.ContactType;
import com.laolu.shipbackend.utils.JsonResponse;
import com.laolu.shipbackend.utils.UserPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wanyi.lu
 * @date Created in 2023/4/20 9:41
 */

@RestController
@CrossOrigin
@RequestMapping("/api/galaxy")
public class GalaxyController {

    @Autowired
    GalaxyService galaxyService;

    @GetMapping("")
    public CommonResponse<List<GalaxyResponse>> getStoreListByStarId(){
        return galaxyService.getGalaxyList();
    }

    @PutMapping("/move/{galaxyId}")
    public CommonResponse<List<UserResponse>> move(@PathVariable Integer galaxyId){
        List<UserResponse> players = new ArrayList<>();
        if (SocketHandle.CLIENT_POOL.containsKey(UserPool.getUser().getToken())) {

            User user = SocketHandle.CLIENT_POOL.get(UserPool.getUser().getToken()).getUser();

            for (Map.Entry<String, SocketClient> player : SocketHandle.CLIENT_POOL.entrySet()) {
                if (player.getValue().getWebSocketSession().isOpen()) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("nickName", user.getNickName());
                    if (player.getValue().getUser().getId().intValue() != user.getId().intValue()) {
                        if (player.getValue().getUser().getGalaxyId().intValue() == user.getGalaxyId().intValue()) {
                            try {
                                player.getValue().getWebSocketSession().sendMessage(new TextMessage(JsonResponse.success(jsonObject, ContactType.LEFT_GAME, player.getValue().getUser().getAuthKey())));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        if (player.getValue().getUser().getGalaxyId().intValue() == galaxyId) {
                            UserResponse userResponse = new UserResponse();
                            userResponse.setPosX(player.getValue().getUser().getPosX());
                            userResponse.setPosY(player.getValue().getUser().getPosY());
                            userResponse.setNickName(player.getValue().getUser().getNickName());
                            players.add(userResponse);

                            UserResponse in = new UserResponse();
                            in.setPosX(user.getPosX());
                            in.setPosY(user.getPosY());
                            in.setNickName(user.getNickName());

                            try {
                                player.getValue().getWebSocketSession().sendMessage(new TextMessage(JsonResponse.success(in, ContactType.NEW_PLAYERS, user.getAuthKey())));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            user.setGalaxyId(galaxyId);
            galaxyService.move(user.getId(), galaxyId);
            return CommonResponse.success(players);
        }
        return CommonResponse.error("前往失败！");
    }
}
