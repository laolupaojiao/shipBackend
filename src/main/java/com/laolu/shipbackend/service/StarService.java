package com.laolu.shipbackend.service;

import com.laolu.shipbackend.model.response.StarResponse;

import java.util.List;

/**
 * @author wanyi.lu
 * @date Created in 2022/9/11 13:57
 */
public interface StarService {
    List<StarResponse> getStarList(int galaxyId);
}
