package com.laolu.shipbackend.controller;

import com.laolu.shipbackend.model.request.store.BuyRequest;
import com.laolu.shipbackend.model.response.StarStoreListResponse;
import com.laolu.shipbackend.service.StarStoreService;
import com.laolu.shipbackend.utils.CommonResponse;
import com.laolu.shipbackend.utils.UserPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @author wanyi.lu
 * @date Created in 2023/4/6 23:12
 */

@RestController
@CrossOrigin
@RequestMapping("/api/store")
public class StoreController {
    @Autowired
    StarStoreService starStoreService;

    @GetMapping("/{starId}")
    public CommonResponse<List<StarStoreListResponse>> getStoreListByStarId(@NotNull(message = "星球ID不能位空") @PathVariable Integer starId){
        return CommonResponse.success(starStoreService.getList(starId));
    }

    @PostMapping("/buy/{storeId}")
    public CommonResponse<String> buyStoreById(@PathVariable Integer storeId, @RequestBody BuyRequest buyRequest){
        buyRequest.setTargetId(storeId);
        buyRequest.setUserId(UserPool.getUser().getId());
        return starStoreService.buy(buyRequest);
    }
}
