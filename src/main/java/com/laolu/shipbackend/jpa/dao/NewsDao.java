package com.laolu.shipbackend.jpa.dao;

import com.laolu.shipbackend.jpa.entity.NewsEntity;

import java.util.Optional;

/**
 * @author wanyi.lu
 * @date Created in 2023/4/20 10:13
 */
public interface NewsDao extends BaseDao<NewsEntity, Integer> {
    Optional<NewsEntity> findByStarId(Integer starId);
}
