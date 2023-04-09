package com.laolu.shipbackend.socket.handler;

import com.laolu.shipbackend.model.User;
import com.laolu.shipbackend.model.request.store.BuyRequest;
import com.laolu.shipbackend.service.StarStoreService;
import com.laolu.shipbackend.socket.SocketHandle;
import com.laolu.shipbackend.utils.AESTools;
import com.laolu.shipbackend.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import static com.laolu.shipbackend.utils.JsonResponse.GSON;

/**
 * @author wanyi.lu
 * @date Created in 2023/1/4 10:30
 */

@Component
public class StoreHandler {
    @Autowired
    StarStoreService starStore;


    public void starBuy(String req, WebSocketSession session) {
        try {
            String reqdata = AESTools.decrypt(req,SocketHandle.CLIENT_POOL.get(session.getId()).getUser().getAuthKey());
            BuyRequest buyRequest = GSON.fromJson(reqdata, BuyRequest.class);
            User user = SocketHandle.CLIENT_POOL.get(session.getId()).getUser();
            if (!ObjectUtils.isEmpty(buyRequest) && !ObjectUtils.isEmpty(user)) {
                synchronized (buyRequest.getTargetId()) {
                    if (starStore.buy(buyRequest)) {
                        session.sendMessage(new TextMessage(JsonResponse.message(200, "购买成功", user.getAuthKey())));
                    } else {
                        session.sendMessage(new TextMessage(JsonResponse.message(500, "购买失败", user.getAuthKey())));
                    }
                }
            } else {
                session.sendMessage(new TextMessage(JsonResponse.message(403, "请重新登录", user.getAuthKey())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
