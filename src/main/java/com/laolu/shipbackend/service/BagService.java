package com.laolu.shipbackend.service;

import com.laolu.shipbackend.model.response.BagItemResponse;
import com.laolu.shipbackend.utils.CommonResponse;

import java.util.List;

/**
 * @author wanyi.lu
 * @date Created in 2023/4/18 17:47
 */
public interface BagService {
    /**
     * 给玩家添加物品
     * @param userId 用户ID
     * @param storeId 商品ID
     * @param amount 添加数量
     * @return CommonResponse<String>
     */
    CommonResponse<String> addItem(Integer userId, Integer storeId, Integer amount);

    /**
     * 给玩家删除物品
     * @param userId 用户ID
     * @param storeId 商品ID
     * @param amount 添加数量
     * @return CommonResponse<String>
     */
    CommonResponse<String> deleteItem(Integer userId, Integer storeId, Integer amount);

    /**
     * 获取玩家物品列表
     * @param userId 用户ID
     * @return CommonResponse<String>
     */
    CommonResponse<List<BagItemResponse>> getItems(Integer userId);
}
