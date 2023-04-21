package com.laolu.shipbackend.service;

import com.laolu.shipbackend.model.response.GalaxyResponse;
import com.laolu.shipbackend.utils.CommonResponse;

import java.util.List;

/**
 * @author wanyi.lu
 * @date Created in 2023/4/20 10:10
 */
public interface GalaxyService {
    CommonResponse<List<GalaxyResponse>> getGalaxyList();

    void move(Integer userId, Integer galaxyId);
}
