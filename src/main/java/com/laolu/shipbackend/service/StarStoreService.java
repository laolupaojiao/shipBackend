package com.laolu.shipbackend.service;

import com.laolu.shipbackend.model.request.store.BuyRequest;
import com.laolu.shipbackend.model.request.store.SellRequest;
import com.laolu.shipbackend.model.response.StarStoreListResponse;

import java.util.List;

/**
 * @author wanyi.lu
 * @date Created in 2022/12/21 15:54
 */
public interface StarStoreService {
    /**
     * 购买
     *
     * @param buyRequest 实体类
     * @return boolean
     */
    boolean buy(BuyRequest buyRequest);

    /**
     * 出售
     *
     * @param sellRequest 实体类
     * @return boolean
     */
    boolean sell(SellRequest sellRequest);

    /**
     * 获取商品列表
     *
     * @param starId 实体类
     * @return boolean
     */
    List<StarStoreListResponse> getList(Integer starId);
}
