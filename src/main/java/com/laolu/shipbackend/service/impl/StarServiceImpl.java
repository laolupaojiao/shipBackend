package com.laolu.shipbackend.service.impl;

import com.laolu.shipbackend.dao.StarMapper;
import com.laolu.shipbackend.model.response.StarResponse;
import com.laolu.shipbackend.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wanyi.lu
 * @date Created in 2022/9/11 13:57
 */
@Service
public class StarServiceImpl implements StarService {

    @Autowired
    StarMapper starMapper;

    public List<StarResponse> getStarList(int galaxyId) {
        return starMapper.getStarListByGalaxyId(galaxyId);
    }
}
