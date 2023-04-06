package com.laolu.shipbackend.service;

import com.laolu.shipbackend.model.Star;

import java.util.List;

/**
 * @author wanyi.lu
 * @date Created in 2022/9/11 13:57
 */
public interface StarService {
    List<Star> getStarList(int galaxyId);
}
