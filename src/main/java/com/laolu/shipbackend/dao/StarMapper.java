package com.laolu.shipbackend.dao;

import com.laolu.shipbackend.model.response.StarResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wanyi.lu
 * @date Created in 2022/9/11 13:52
 */
@Mapper
public interface StarMapper {
    @Select("select * from ship_star where galaxy_id=#{galaxyId}")
    List<StarResponse> getStarListByGalaxyId(int galaxyId);
}
