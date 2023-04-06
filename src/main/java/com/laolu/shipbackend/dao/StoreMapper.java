package com.laolu.shipbackend.dao;

import com.laolu.shipbackend.model.Goods;
import com.laolu.shipbackend.model.User;
import com.laolu.shipbackend.model.request.store.BuyRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wanyi.lu
 * @date Created in 2023/1/4 11:06
 */

@Mapper
public interface StoreMapper {
    @Select("select * from old_ship_store where star_id=#{starId} and status=1")
    List<Goods> getStarGoodsList(Integer starId);

    @Select("select * from old_ship_store where id=#{targetId} and left_amount > #{amount} and status=1")
    Goods getStarGoodsById(BuyRequest buyRequest);
}
